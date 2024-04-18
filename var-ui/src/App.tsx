import { useState } from 'react';
import TableComponent from './components/TableComponent';
import UploadComponent from './components/UploadComponent';
import VaRForm from './components/VaRForm';
import './index.css';


export interface PnLVector {
  pnLVector: number[];
}

export interface DataType {
  key?: React.Key;
  asOfDate: string;
  tradeID: string;
  pnLVector: PnLVector;
}



function App() {
  const [selectedTrades, setSelectedTrades] = useState<DataType[]>();
  const [refresh, setRefresh] = useState<Boolean>(true);


  return (
    <div className="App">
      <header className="App-header">
        <h1>Value-At-Risk Calculation</h1>
      </header>
      <div className='upload'>
        <UploadComponent setRefresh={setRefresh} />
      </div>
      <div className='table'>
        <TableComponent setSelectedTrades={setSelectedTrades} setRefresh={setRefresh} refresh={refresh}/>
      </div>
      <div className='form'>
        <VaRForm selectedTrades={selectedTrades}/>
      </div>
    </div>
  );
}

export default App;
