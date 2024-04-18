package com.gms.var.entities;

import com.gms.var.enums.VarModeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PnLVector implements Serializable {

    protected double[] pnLVector;

    /**
     * Retrieve the quantile index
     * Handle several modes :
     * - Simple : quantileIndex = pnLVector.length * quantile
     * - Centered : quantileIndex = pnLVector.length * quantile + 0.5
     * - Exc : quantileIndex = (pnLVector.length + 1) * quantile
     * - Inc : quantileIndex = (pnLVector.length - 1) * quantile + 1
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @return the quantile index
     */
    public double getPnLVectorQuantileIndex(double quantile, VarModeEnum mode) {
        double quantileIndex;
        int vectorLength = this.pnLVector.length;
        quantileIndex = switch (mode) {
            case SIMPLE -> vectorLength * quantile;
            case CENTERED -> vectorLength * quantile + 0.5;
            case EXC -> (vectorLength + 1) * quantile;
            default -> (vectorLength - 1) * quantile + 1;
        };
        return quantileIndex - 1;
    }
}
