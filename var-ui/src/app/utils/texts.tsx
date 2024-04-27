import React, { ReactNode } from "react";
import { styles } from "../styles";

export interface TextResourceType {
  aboutTool: ReactNode;
  aboutVaR: ReactNode;
}

const texts: TextResourceType = {
  aboutTool: (
    <>
      <p>
        This online tool aims to compute the Value-At-Risk according to the
        Historical approach.
      </p>
      <p>
        It needs as input a CSV file containing the following information :
        Date, TradeID, P&L Vector.
      </p>
      <br />
      <h1 style={{ fontSize: 24 }}>How to compute your VaR ?</h1>
      <br />
      <li>Upload your PnL Trades CSV file.</li>
      <li>Select the trades for which you want to compute the VaR.</li>
      <li>Provide the quantile value.</li>
      <li>Provide the mode.</li>
      <li>Provide the interpolation.</li>
      <li>Click on the button Compute my VaR.</li>
      <br />
      <p>
        For further details about the measure computation or the configuration -
        please see the related section below.
      </p>
    </>
  ),
  aboutVaR: (
    <>
      <h1 className={styles.sectionHeadText}>Value-At-Risk</h1>
      <p>
        Value-At-Risk represents an investor&apos;s maximum potential loss on
        the value of a financial asset or portfolio of assets, which is expected
        to be achieved only with a given probability over a given time horizon.
      </p>
      <p>
        It is, in other words, the worst expected loss over a given time horizon
        for a given level of confidence.
      </p>
      <p>
        VaR can be seen as a quantile of the profit-and-loss distribution
        associated with holding an asset or a portfolio of assets over a given
        period.
      </p>
      <p>
        This tool is computing the VaR, according to the Historical approach.
      </p>
      <br />
      <h1 className={styles.sectionHeadText}>Parameters</h1>
      <h2 className={styles.sectionSubText}>Mode</h2>
      <p>The method used to compute the index of the quantile.</p>
      <p>Knowing q the quantile of a vector V, available options are :</p>
      <p className="my-4">
        <li>
          <span className="font-bold">simple :</span> V.length * q
        </li>
        <li>
          <span className="font-bold">centered :</span> V.length * q + 0.5
        </li>
        <li>
          <span className="font-bold">exc :</span> (V.length + 1) * q
        </li>
        <li>
          <span className="font-bold">inc :</span> (V.length - 1) * q
        </li>
      </p>
      <h2 className={styles.sectionSubText}>Interpolation</h2>
      <p>
        If the quantile index is not an integer, the interpolation decides what
        value is returned.
      </p>
      <p>
        Knowing a quantile index k with i&lt;k&lt;j for the sorted vector V.
      </p>
      <p className="my-4">
        <li>
          <span className="font-bold">linear :</span> value = V[ i ] + (V[ j ] -
          V[ i ]) * (k - i)
        </li>
        <li>
          <span className="font-bold">lower :</span> value = V[ i ]
        </li>
        <li>
          <span className="font-bold">higher :</span> value = V[ j ]
        </li>
        <li>
          <span className="font-bold">nearest :</span> value = V[ i ] or value =
          V[ j ] depending on which is closest to k
        </li>
      </p>
    </>
  ),
};

export { texts };
