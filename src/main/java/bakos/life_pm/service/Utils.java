package bakos.life_pm.service;

import bakos.life_pm.exception.PositionOverflowException;
import lombok.experimental.UtilityClass;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@UtilityClass
public class Utils {

    public static int getSparseOrder(Integer prevPositionValue, Integer nextPositionValue) throws PositionOverflowException {
        int prev = prevPositionValue == null ? 0 : prevPositionValue;
        int next = nextPositionValue == null ? (prev + SPARSE_POSITION_GAP) * 2 : nextPositionValue;

        int newPosition = (prev + next) / 2;
        double precisePosition = (prev + next) / 2.0;

        if (newPosition == (int) precisePosition) {  // this should also handle integer overflow?
            return newPosition;
        } else {
            throw new PositionOverflowException();
        }
    }


}
