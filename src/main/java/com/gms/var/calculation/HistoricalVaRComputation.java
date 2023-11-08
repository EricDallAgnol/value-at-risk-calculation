package com.gms.var.calculation;

import com.gms.var.data.entity.PnLTrade;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class HistoricalVaRComputation {

    /**
     * Compute the Value-At-Risk for several P&L Trades
     * @param pnLTrade - List of PnLTrades
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @param vaRInterpolation - Interpolation mode
     * @return the Value-At-Risk for several P&L Trade
     */
    public static double computeTradeVaR(List<PnLTrade> pnLTrade, double quantile, VarMode mode, VaRInterpolation vaRInterpolation) {
        if (pnLTrade.size() == 0) {
            throw new RuntimeException("No trades loaded !");
        }

        if (pnLTrade.size() == 1) {
            return computeTradeVaR(pnLTrade.get(0), quantile, mode, vaRInterpolation);
        }

        double[] aggregatedPnLVector = aggregateVector(pnLTrade);
        return computeTradeVaR(aggregatedPnLVector, quantile, mode, vaRInterpolation);
    }

    /**
     * Compute the Value-At-Risk for one P&L Trade
     * @param pnLTrade - PnLTrade
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @param vaRInterpolation - Interpolation mode
     * @return the Value-At-Risk for one P&L Trade
     */
    private static double computeTradeVaR(PnLTrade pnLTrade, double quantile, VarMode mode, VaRInterpolation vaRInterpolation) {
        return computeTradeVaR(pnLTrade.getPnl(), quantile, mode, vaRInterpolation);
    }

    /**
     * Compute the Value-At-Risk from a PnL Vector
     * @param pnLVector - PnL Vector
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @param vaRInterpolation - Interpolation mode
     * @return the Value-At-Risk for one P&L Trade
     */
    private static double computeTradeVaR(double[] pnLVector, double quantile, VarMode mode, VaRInterpolation vaRInterpolation) {
        Arrays.sort(pnLVector);
        double quantileIndex = getQuantileIndex(pnLVector, quantile, mode);

        if ((quantileIndex % 1) != 0) {
            return computeInterpolation(pnLVector, quantileIndex, vaRInterpolation);
        } else {
            return pnLVector[(int) quantileIndex];
        }
    }


    /**
     * Retrieve the quantile index
     * Handle several modes :
     * - Simple : quantileIndex = pnLVector.length * quantile
     * - Centered : quantileIndex = pnLVector.length * quantile + 0.5
     * - Exc : quantileIndex = (pnLVector.length + 1) * quantile
     * - Inc : quantileIndex = (pnLVector.length - 1) * quantile + 1
     * @param pnLVector - P&L Vector
     * @param quantile - Quantile
     * @param mode - Mode to retrieve the quantile index
     * @return the quantile index
     */
    private static double getQuantileIndex(double[] pnLVector, double quantile, VarMode mode) {
        double quantileIndex;
        int vectorLength = pnLVector.length;
        switch (mode) {
            case SIMPLE:
                quantileIndex = vectorLength * quantile;
                break;
            case CENTERED:
                quantileIndex = vectorLength * quantile + 0.5;
                break;
            case EXC:
                quantileIndex = (vectorLength + 1) * quantile;
                break;
            default:
            case INC:
                quantileIndex = (vectorLength -1) * quantile + 1;
        }
        return quantileIndex - 1;
    }

    /**
     * Compute the interpolated value based on the quantile index and the interpolation mode chosen
     * Handle several modes :
     *  - LOWER : take as quantile index the near lowest integer
     *  - HIGHER : take as quantile index the near highest integer
     *  - NEAREST : take as quantile index the nearest integer
     *  - LINEAR : linear interpolation
     * @param pnLVector - P&L Vector
     * @param quantileIndex - Quantile Index
     * @param vaRInterpolation - Interpolation mode
     * @return interpolated value
     */
    private static double computeInterpolation(double[] pnLVector, double quantileIndex, VaRInterpolation vaRInterpolation) {
        int index;
        switch (vaRInterpolation) {
            case LOWER:
                index = (int) Math.floor(quantileIndex);
                return pnLVector[Math.max(index, 0)];
            case HIGHER:
                index = (int) Math.ceil(quantileIndex);
                return pnLVector[index];
            case NEAREST:
                index = (int) Math.round(quantileIndex);
                return pnLVector[index];
            default:
            case LINEAR:
                int higherIndex = (int) Math.ceil(quantileIndex);
                int lowerIndex = Math.max((int) Math.floor(quantileIndex), 0);

                double valueAtHigherIndex = pnLVector[higherIndex];
                double valueAtLowerIndex = pnLVector[lowerIndex];

                return pnLVector[lowerIndex] + (valueAtHigherIndex - valueAtLowerIndex) * (quantileIndex - lowerIndex);
        }
    }

    /**
     * Aggregate Vectors
     * If vectors have a different size, exceeding entries will be dropped
     * @param pnLTrade - List of PnLTrades
     * @return aggregated vectors as double[]
     */
    private static double[] aggregateVector(List<PnLTrade> pnLTrade) {
        List<double[]> pnlVectorList = pnLTrade.stream().map(PnLTrade::getPnl).collect(Collectors.toList());
        double[] aggregatedVector;

        aggregatedVector = pnlVectorList.stream().reduce(HistoricalVaRComputation::aggregate2Vectors).orElse(new double[]{});
        return aggregatedVector;


    }

    /**
     * Helper method to aggregate 2 vectors
     * Truncate extra values if the vectors have not the same size
     * @param vector1 - Vector 1
     * @param vector2 - Vector 2
     * @return aggregation of the 2 provided vectors
     */
    private static double[] aggregate2Vectors(double[] vector1, double[] vector2) {
        IntStream range = IntStream.range(0, Math.min(vector1.length, vector2.length));
        DoubleStream stream = range.mapToDouble(i -> vector1[i] + vector2[i]);
        return stream.toArray();
    }



}
