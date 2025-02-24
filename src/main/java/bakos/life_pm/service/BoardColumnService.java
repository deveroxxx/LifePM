package bakos.life_pm.service;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.exception.BusinessLogicRtException;
import bakos.life_pm.exception.PositionOverflowException;
import bakos.life_pm.repository.BoardColumnRepository;
import bakos.life_pm.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@Service
public class BoardColumnService {

    private final BoardColumnRepository columnRepo;
    private final BoardRepository boardRepo;

    public BoardColumnService(BoardColumnRepository columnRepo, BoardRepository boardRepo) {
        this.columnRepo = columnRepo;
        this.boardRepo = boardRepo;
    }

    public BoardColumn getColumn(UUID id) {
        return columnRepo.findByIdOrThrow(id);
    }

    public List<BoardColumn> listColumns() {
        return columnRepo.findAll();
    }

    public BoardColumn createColumn(String name, UUID boardId) {
        BoardColumn column = new BoardColumn();
        column.setName(name);
        column.setPosition(columnRepo.findMaxPositionInBoard(boardId) + SPARSE_POSITION_GAP);
        column.setBoard(boardRepo.findByIdAndUserNameOrThrow(boardId, Utils.getUserFromSecurityContext()));
        column = this.columnRepo.save(column);
        return column;
    }

    @Transactional
    public void updateColumnPosition(UUID movedColumnId, UUID prevColId, UUID nextColId) {
        BoardColumn movedColumn = getColumn(movedColumnId);
        try {
            int newPosition = Utils.getSparseOrder(
                    prevColId == null ? null : getColumn(prevColId).getPosition(),
                    nextColId == null ? null : getColumn(nextColId).getPosition());
            movedColumn.setPosition(newPosition);
        } catch (PositionOverflowException e) {
            recreateAllColumnPositionInBoard(movedColumn);
        }
    }

    private static void recreateAllColumnPositionInBoard(BoardColumn movedColumn) {
        int position = SPARSE_POSITION_GAP;
        for (BoardColumn column : movedColumn.getBoard().getColumns()) {
            column.setPosition(position);
            position += SPARSE_POSITION_GAP;
        }
    }

    @Transactional
    public void deleteColumn(UUID id) {
        if (columnRepo.findByIdOrThrow(id).isArchived()) {
            columnRepo.deleteById(id);
        } else {
            throw new BusinessLogicRtException("Only archived columns can be deleted.");
        }
    }

    @Transactional
    public void setArchived(UUID id, boolean archived) {
        columnRepo.findByIdOrThrow(id).setArchived(archived);
    }
}
