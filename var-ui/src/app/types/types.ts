export interface PnLVector {
  pnLVector: number[];
}

export interface DataType {
  key?: React.Key;
  asOfDate: string;
  tradeID: string;
  pnLVector: PnLVector;
}
