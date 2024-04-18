package com.gms.var.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
@Table(name = "PnL Trades")
@Getter
@Setter
@NoArgsConstructor
@IdClass(TradePositionKey.class)
public class TradePositionWithPnL implements Serializable {

    @Id
    private LocalDate asOfDate;
    @Id
    private String tradeID;
    @Lob
    private PnLVector pnLVector;

    public TradePositionWithPnL(LocalDate asOfDate, String tradeID, PnLVector pnLVector) {
        this.asOfDate = asOfDate;
        this.tradeID = tradeID;
        this.pnLVector = pnLVector;
    }

    @Override
    public String toString() {
        return String.format("TradePositionWithPnL[AsOfDate=%s, TradeId=%s, PnL=%s]", asOfDate, tradeID, Arrays.toString(pnLVector.getPnLVector()));
    }
}
