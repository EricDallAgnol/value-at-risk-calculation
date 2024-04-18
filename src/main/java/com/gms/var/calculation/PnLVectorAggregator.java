package com.gms.var.calculation;

import com.gms.var.entities.PnLVector;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PnLVectorAggregator {

    protected List<PnLVector> pnLVectorList;

    /**
     * Aggregate Vectors
     * If vectors have a different size, exceeding entries will be dropped
     * @return aggregated list of vectors as double[]
     */
    public PnLVector aggregateVector() {
        int size = pnLVectorList.stream()
                .mapToInt(vector -> vector.getPnLVector().length)
                .max().orElse(0);

        double[] aggregatedVector = new double[size];

        pnLVectorList.parallelStream().forEach(vector -> {
            double[] pnlVector = vector.getPnLVector();
            for (int i = 0; i < Math.min(pnlVector.length, size); i++) {
                aggregatedVector[i] += pnlVector[i];
            }
        });
        return new PnLVector(aggregatedVector);
    }
}
