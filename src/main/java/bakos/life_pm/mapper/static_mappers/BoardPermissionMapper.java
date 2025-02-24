package bakos.life_pm.mapper.static_mappers;

import bakos.life_pm.dto.response.BoardUserResponse;
import bakos.life_pm.entity.BoardPermission;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BoardPermissionMapper {

    public static BoardUserResponse toBoardUser(BoardPermission permission) {
        BoardUserResponse response = new BoardUserResponse();
        response.setPermission(permission.getPermission());
        response.setUserName(permission.getUserName());
        return response;
    }

}
