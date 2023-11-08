package com.gms.var.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PnLTradeKey.class)
public class PnLTrade implements Serializable {

    @Id
    private LocalDate asOfDate;
    @Id
    private String tradeID;
    private double[] pnl;

    @Override
    public String toString() {
        return String.format("PnLTrade[AsOfDate=%s, TradeId=%s, PnL=%s]", asOfDate, tradeID, Arrays.toString(pnl));
    }
}
