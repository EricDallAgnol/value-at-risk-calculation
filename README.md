# Value-At-Risk Calculation (Historical Method)

This mini-project aims to calculate the Value-At-Risk with the Historical method, starting from a P&L input CSV file.

## 🛠️ Installation :

```shell script
mvn clean install
```

## ▶️ Start the application

```shell script
java  -jar var-0.9.0-SNAPSHOT.jar
```

You can now reach the UI Application on : http://localhost:8081/var-view/index.html

![image](https://github.com/EricDallAgnol/value-at-risk-calculation/assets/83015366/7cab3ef5-379a-4ebb-8179-d1c8ffcc7dc6)

--------
### How to compute the VaR ❓

1. Upload your PnL Trades using the UI App
2. Select the trades for which you want to compute the VaR
3. Provide the quantile value
4. Provide the mode
5. Provide the interpolation
6. Click on the button "Compute VaR"

#### Parameters :
- **mode** : method to calculate the quantile index.
  - For a given quantile *q* and a vector *V* :
    -  `simple` : *V*.length * *q*
    -  `centered` : *V*.length * *q* + 0.5
    -  `exc` : (*V*.length + 1) * *q*
    -  `inc` : (*V*.length - 1) * *q*
      
- **interpolation** :
  - For a given quantile index *k* with *i < k < j* and  a sorted vector *V* :
    - `linear` : value = *V*[i] + (*V*[j] - *V*[i]) * (k - i)
    - `lower` : value = *V*[i]
    - `higher` : value = *V*[j]
    - `nearest` : value = *V*[i] or value = *V*[j] depending on which is closest to k

> *See also https://en.wikipedia.org/wiki/Quantile#Estimating_quantiles_from_a_sample*
--------
## CSV File

The CSV file expected by the Application should contain the following information : AsOfDate, TradeId, PnL Vector
> ⚠️Note: the header fields name don't matter but the order does !

Here is an example :

```
AsOfDate, TradeID, PnL
2023-11-06,Trade1,4777.03;-35280.68;7922.21;-13171.6;-24578.04;2110.91;4284.55;-9059.76;-1362.05;-15613.03;2246.1;-16573.48;-20827.31;2541.31;-9508.58;-35064.59;-17040.44;16591.91;798.52;836.44;874.36;-7516.22;-37053.97;10073.5;967.91;-16548.52;-13888.8;-17538.83;13654.33;14136.07;-8568.05;-18844.87;23054.34;1642.09;-16835.96;-2014.11;-3647.43;11035.58;20970.14;-1567.4;9963.6;17050.49;1814.14;-2318.31;-9458.98;-8326.99;7527.16;-30.46;253.4;1392.46;15148.15;12939.42;-3797.41;-3298.57;18081.76;28388.15;-28644.53;17683.71;2184.67;10184.18;3331.16;14805.19;7924.26;8273.28;-29062.84;-7798.18;7795.23;17635.44;2118.29;9702.8;-22684.31;10789.03;7684.59;5097.6;8916.11;-13325.48;-19632.81;3888.49;-7939.45;-14013.28;-26983.08;-7833.35;749.99;-8362.95;-8464.74;-19560.24;12900.67;26097.72;-5098.11;-9597.76;703.2;-20461.71;5639.74;18145.38;-13463.12;-11025.01;5472.89;9320.61;-9934.29;-6699.57;-6691.46;-1245.39;19159.92;9494.59;-10064.14;-22721.48;22486.98;-10563.16;4768.13;-7423.96;-853.92;-1674.73;3733.21;2204.35;-6921.57;-18686.07;-2645.25;-1302.89;8020.4;-10836.19;-77.22;-7868.77;-20810.28;-9498.53;-9752.09;1220.81;8417.72;2903.88;-17672.08;7357.75;-1443.02;-18946.31;-4954.1;-20599.05;16379.17;2384.89;-11289.25;13420.35;-29447.86;18660.89;-21715.61;12989.11;465.75;1386.41;786.96;-947.19;-4208.21;8491.13;22559.65;10748.01;8919.61;22476.38;-999.25;-3000.28;4537.12;-5073.43;2983.4;-15546.63;-168.28;12740.42;9817.76;646.4;-28958.71;-9474.07;-1293.06;-12822.25;17798.92;-550.99;-4246.35;3560.95;-6826.88;-4930.04;-6339.37;1250.35;-4538.38;-1432.06;3995.86;-2337.04;-9738.45;1023.94;7990.89;14469.97;11080.83;16837.69;-5556.66;-1422.09;3868.61;-5523.17;27595.03;8016.58;-11139.89;-4730.1;-20160.39;-26119.55;-3978.58;7619.61;-13046.63;11909.27;1080.56;1402.22;-14216.64;5717.95;15378.28;4583.32;-3132.61;-5216.87;17483.05;-10571.14;-15088.71;7940.25;5405.15;-7975.59;7750.66;923.41;-10201.76;10731.11;-13266.15;24548.09;-4637.49;-3009.79;8915.24;-3041.56;-3265.68;2273.12;-1536.19;5354.93;14000.63;-9443.12;-19045.23;-11554.56;6433.38;-2678.38;24842.35;6808.26;20063.43;-23051.87;-22681.04;4784.02;-9503.54;9450.18;-26431.78;5210.31;-3427.59;-10740.61;11689.68;8791.51;-22688.67;-4673.91;-2207.95;5531.82;-23613.26;651.83;14093.92;6079.5
```


--------
## 🖋️ Assumptions

- In this project, we are assuming to receive trades with the same currency
- The UI served by Spring is built to target `http://localhost:8081`
