package brunorsch.minedasantigas.teleports;

import static org.apache.commons.lang3.math.NumberUtils.isNumber;
import static org.apache.commons.lang3.math.NumberUtils.toDouble;

import lombok.Getter;

@Getter
public class CoordsValidator {
    private double x;
    private double y;
    private double z;
    private boolean isValid;

    public CoordsValidator(String x, String y, String z) {
        this.isValid = isNumber(x) && isNumber(y) && isNumber(z);
        this.x = toDouble(x);
        this.y = toDouble(y);
        this.z = toDouble(z);
    }
}