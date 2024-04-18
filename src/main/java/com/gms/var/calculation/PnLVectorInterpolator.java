package com.gms.var.calculation;

import com.gms.var.entities.PnLVector;
import com.gms.var.enums.VaRInterpolationEnum;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class PnLVectorInterpolator {
    protected PnLVector pnLVector;

    /**
     * Compute the interpolated value based on the quantile index and the interpolation mode chosen
     * Handle several modes :
     *  - LOWER : take as quantile index the near lowest integer
     *  - HIGHER : take as quantile index the near highest integer
     *  - NEAREST : take as quantile index the nearest integer
     *  - LINEAR : linear interpolation
     * @param quantileIndex - Quantile Index
     * @param vaRInterpolationEnum - Interpolation mode
     * @return interpolated value
     */
    public double computeInterpolation(double quantileIndex, VaRInterpolationEnum vaRInterpolationEnum) {
        int lastIndex = this.pnLVector.getPnLVector().length - 1;
        return switch (vaRInterpolationEnum) {
            case LOWER -> lowerInterpolation(quantileIndex);
            case HIGHER -> higherInterpolation(quantileIndex, lastIndex);
            case NEAREST -> nearestInterpolation(quantileIndex, lastIndex);
            default -> linearInterpolation(quantileIndex, lastIndex);
        };
    }

    private double lowerInterpolation(double quantileIndex) {
        int index = (int) Math.floor(quantileIndex);
        return this.pnLVector.getPnLVector()[Math.max(index, 0)];
    }

    private double higherInterpolation(double quantileIndex, int lastIndex) {
        int index = (int) Math.ceil(quantileIndex);
        return this.pnLVector.getPnLVector()[Math.min(lastIndex, index)];
    }

    private double nearestInterpolation(double quantileIndex, int lastIndex) {
        int index = (int) Math.round(quantileIndex);
        return this.pnLVector.getPnLVector()[Math.min(lastIndex, index)];
    }

    private double linearInterpolation(double quantileIndex, int lastIndex) {
        int higherIndex = Math.min(lastIndex, (int) Math.ceil(quantileIndex));
        int lowerIndex = Math.max((int) Math.floor(quantileIndex), 0);

        double valueAtHigherIndex = this.pnLVector.getPnLVector()[higherIndex];
        double valueAtLowerIndex = this.pnLVector.getPnLVector()[lowerIndex];

        return this.pnLVector.getPnLVector()[lowerIndex] + (valueAtHigherIndex - valueAtLowerIndex) * (quantileIndex - lowerIndex);
    }
}
