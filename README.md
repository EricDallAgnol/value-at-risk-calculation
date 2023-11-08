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


## CSV File

The CSV file expected by the Application should contain the following informations : AsOfDate, TradeId, PnL Vector
> ⚠️Note: the header fields name don't matter but the order does !


## 🖋️ Assumptions

- In this project, we are assuming to receive trades with the same currency
- The UI served by Spring is built to target `http://localhost:8081`
