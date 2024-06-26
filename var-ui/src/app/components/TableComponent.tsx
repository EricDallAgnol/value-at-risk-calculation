import { Button, Space, SpinProps, Table, message } from "antd";
import type { ColumnsType } from "antd/es/table";
import React, { useEffect, useState } from "react";
import { PnLVector, DataType } from "../types/types";

const uid = () => {
  return Date.now().toString(36) + Math.random().toString(36).substr(2);
};

const columns: ColumnsType<DataType> = [
  {
    title: "AsOfDate",
    dataIndex: "asOfDate",
    key: "asOfDate",
    ellipsis: true,
  },
  {
    title: "TradeID",
    dataIndex: "tradeID",
    key: "tradeID",
    ellipsis: true,
  },
  {
    title: "PnL Vector",
    dataIndex: "pnLVector",
    key: "pnLVector",
    ellipsis: true,
    render: (text: PnLVector | null) =>
      text?.pnLVector === null ? (
        <></>
      ) : (
        <span>
          {text?.pnLVector.map((value) => <span key={uid()}>{value} </span>)}
        </span>
      ),
  },
];

const TableComponent = ({
  setSelectedTrades,
  setRefresh,
  refresh,
}: {
  setSelectedTrades: (value: DataType[]) => void;
  setRefresh: (value: boolean) => void;
  refresh: boolean;
}) => {
  const [isLoading, setIsLoading] = useState<boolean | SpinProps | undefined>(
    false,
  );
  const [data, setData] = useState<DataType[]>([]);

  // To refresh items displayed
  useEffect(() => {
    if (refresh)
      refreshDataFrom(setIsLoading, setData)
        .then()
        .finally(() => setRefresh(false));
  }, [refresh, setRefresh]);

  const rowSelection = {
    onChange: (selectedRowKeys: React.Key[], selectedRows: DataType[]) => {
      setSelectedTrades(
        selectedRows.map((row) => ({
          asOfDate: row.asOfDate,
          tradeID: row.tradeID,
          pnLVector: row.pnLVector,
        })),
      );
    },
  };
  return (
    <div className="w-fit">
      <Table
        rowSelection={{
          type: "checkbox",
          ...rowSelection,
        }}
        columns={columns}
        dataSource={data}
        loading={isLoading}
        pagination={{ pageSize: 10 }}
        scroll={{ y: 240 }}
        bordered
        title={() => (
          <span style={{ fontWeight: "bold" }}>
            Trade Positions with PnL Vector
          </span>
        )}
        footer={() => (
          <Space>
            <Button type="primary" onClick={() => setRefresh(true)}>
              Refresh
            </Button>
            <Button
              type="default"
              onClick={() => {
                dropPnL();
                setRefresh(true);
              }}
            >
              Delete All
            </Button>
          </Space>
        )}
      />
    </div>
  );
};

const refreshDataFrom = async (
  setLoading: (newLoadingValue: boolean) => void,
  setData: (newData: DataType[]) => void,
): Promise<void> => {
  setLoading(true);
  fetch(`/data/all-trades`, {
    method: "GET",
    redirect: "follow",
  })
    .then((response) => response.json())
    .then((data: DataType[]) => {
      const parsedData: DataType[] = data.map((item) => ({
        ...item,
        key: item.tradeID,
      }));
      setData(parsedData);
    })
    .catch((error) => console.log(`Error: ${JSON.stringify(error)}`))
    .finally(() => setLoading(false));
};

const dropPnL = async (): Promise<void> => {
  fetch(`/data/drop-pnl`, {
    method: "DELETE",
    redirect: "follow",
  })
    .then((response) => response.text())
    .then((result) => console.log(result))
    .catch((error) => message.error(error));
};

export default TableComponent;
