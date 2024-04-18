package com.gms.var.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-config.properties")
public class VarApplicationConfig {
    @Value("${trade-position-file.asOfDate.index}")
    public int asOfDateIndex;
    @Value("${trade-position-file.tradeId.index}")
    public int tradeIdIndex;

    @Value("${trade-position-file.pnLVector.index}")
    public int pnLVectorIndex;

    @Value("${trade-position-file.pnLVector.separator}")
    public String pnLVectorSeparator;

}
