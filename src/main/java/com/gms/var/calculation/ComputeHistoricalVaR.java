package com.gms.var.calculation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gms.var.entities.PnLVector;
import com.gms.var.entities.TradePositionWithPnL;
import com.gms.var.enums.VaRInterpolationEnum;
import com.gms.var.enums.VarModeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public class ComputeHistoricalVaR {

    protected List<TradePositionWithPnL> tradePositionWithPnL;
    protected double quantile;
    protected VarModeEnum varModeEnum;
    protected VaRInterpolationEnum vaRInterpolationEnum;

    /**
     * Compute the Value-At-Risk for several TradePositionWithPnLs
     * @return the Value-At-Risk for several TradePositionWithPnLs
     */
    public double computeTradeVaR() throws JsonProcessingException {
        if (this.tradePositionWithPnL.size() == 0) {
            throw new RuntimeException("No trades loaded !");
        }

        if (this.tradePositionWithPnL.size() == 1) {
            return computeTradeVaR(this.tradePositionWithPnL.get(0), this.quantile, this.varModeEnum, this.vaRInterpolationEnum);
        }
        PnLVectorAggregator pnLVectorAggregator = new PnLVectorAggregator(this.tradePositionWithPnL.stream().map(TradePositionWithPnL::getPnLVector).toList());
        PnLVector aggregatedPnLVector = pnLVectorAggregator.aggregateVector();
        return computeTradeVaR(aggregatedPnLVector, this.quantile, this.varModeEnum, this.vaRInterpolationEnum);
    }

    /**
     * Compute the Value-At-Risk for one TradePositionWithPnL
     * @param tradePositionWithPnL - TradePositionWithPnL
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @param vaRInterpolationEnum - Interpolation mode
     * @return the Value-At-Risk for one TradePositionWithPnL
     */
    private static double computeTradeVaR(TradePositionWithPnL tradePositionWithPnL, double quantile, VarModeEnum mode, VaRInterpolationEnum vaRInterpolationEnum) {
        return computeTradeVaR(tradePositionWithPnL.getPnLVector(), quantile, mode, vaRInterpolationEnum);
    }

    /**
     * Compute the Value-At-Risk from a PnL Vector
     * @param pnLVector - PnL Vector
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @param vaRInterpolationEnum - Interpolation mode
     * @return the Value-At-Risk for one P&L Trade
     */
    private static double computeTradeVaR(PnLVector pnLVector, double quantile, VarModeEnum mode, VaRInterpolationEnum vaRInterpolationEnum) {
        Arrays.sort(pnLVector.getPnLVector());
        double quantileIndex = pnLVector.getPnLVectorQuantileIndex(quantile, mode);

        if ((quantileIndex % 1) != 0) {
            PnLVectorInterpolator pnLVectorInterpolator = new PnLVectorInterpolator(pnLVector);
            return pnLVectorInterpolator.computeInterpolation(quantileIndex, vaRInterpolationEnum);
        } else {
            return pnLVector.getPnLVector()[(int) quantileIndex];
        }
    }









}
