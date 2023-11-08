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

The CSV file expected by the Application should contain the following informations : AsOfDate, TradeId, PnL Vector
> ⚠️Note: the header fields name don't matter but the order does !

--------
## 🖋️ Assumptions

- In this project, we are assuming to receive trades with the same currency
- The UI served by Spring is built to target `http://localhost:8081`
