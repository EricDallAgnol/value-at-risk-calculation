import { Button, Form, InputNumber, Modal, Select, notification } from "antd";
import React, { useMemo, useState } from "react";
import { DataType } from "../types/types";

const { Option } = Select;

interface VaRFormType {
  quantile: number;
  mode: string;
  interpolation: string;
}

const VaRForm = ({
  selectedTrades,
}: {
  selectedTrades: DataType[] | undefined;
}) => {
  const [form] = Form.useForm<VaRFormType>();
  const [api, contextHolder] = notification.useNotification();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContext, setModalContext] = useState<
    VaRFormType & { result: string }
  >();
  const selectedTradesPnL = useMemo(() => selectedTrades, [selectedTrades]);

  const onModeChange = (value: string) => {
    form.setFieldsValue({ mode: value });
  };

  const onInterpolationChange = (value: string) => {
    form.setFieldsValue({ interpolation: value });
  };

  const openErrorNotification = (error: string) => {
    api["error"]({
      message: "",
      description: `${error}`,
    });
  };

  const showModal = (result: string, values: VaRFormType) => {
    setModalContext({
      result: result,
      quantile: values.quantile,
      mode: values.mode,
      interpolation: values.interpolation,
    });
    setIsModalOpen(true);
  };

  const handleOk = () => {
    setIsModalOpen(false);
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };

  const onFinish = (values: VaRFormType) => {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    fetch(`calculation/var`, {
      method: "POST",
      headers: myHeaders,
      redirect: "follow",
      body: JSON.stringify({
        quantile: values.quantile,
        mode: values.mode,
        interpolation: values.interpolation,
        tradePositionWithPnL: selectedTradesPnL,
      }),
    })
      .then((response) => response.text())
      .then((result) => {
        if (!isNaN(parseFloat(result))) {
          showModal(result, values);
        } else {
          throw Error(result);
        }
      })
      .catch((error) => {
        openErrorNotification(error);
      });
  };

  const onReset = () => {
    form.resetFields();
  };

  return (
    <>
      {contextHolder}
      <Modal
        title="Value-At-Risk"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <p>Quantile : {modalContext?.quantile}</p>
        <p>Mode : {modalContext?.mode}</p>
        <p>Interpolation : {modalContext?.interpolation}</p>
        <p>Value-At-Risk : {modalContext?.result}</p>
      </Modal>
      <div className="flex flex-col justify-center items-center w-full">
        <div className="green-pink-gradient p-[1px] rounded-[20px]">
          <div className="bg-tertiary rounded-[20px] px-12 pt-5 flex flex-col mt-7">
            <Form
              labelAlign="left"
              layout="vertical"
              form={form}
              name="control-hooks"
              onFinish={onFinish}
              style={{ maxWidth: "100%" }}
            >
              <Form.Item
                name="quantile"
                label={<label className="text-white font-bold">Quantile</label>}
                rules={[{ required: true }]}
              >
                <InputNumber min={0.001} max={0.999} step={0.001} />
              </Form.Item>

              <Form.Item
                name="mode"
                label={<label className="text-white font-bold">Mode</label>}
                rules={[{ required: true }]}
              >
                <Select
                  placeholder="Select a mode..."
                  onChange={onModeChange}
                  allowClear
                >
                  <Option value="SIMPLE">SIMPLE</Option>
                  <Option value="CENTERED">CENTERED</Option>
                  <Option value="INC">INC</Option>
                  <Option value="EXC">EXC</Option>
                </Select>
              </Form.Item>

              <Form.Item
                name="interpolation"
                label={
                  <label className="text-white font-bold">Interpolation</label>
                }
                rules={[{ required: true }]}
              >
                <Select
                  placeholder="Select an interpolation..."
                  onChange={onInterpolationChange}
                  allowClear
                >
                  <Option value="HIGHER">HIGHER</Option>
                  <Option value="LOWER">LOWER</Option>
                  <Option value="NEAREST">NEAREST</Option>
                  <Option value="LINEAR">LINEAR</Option>
                </Select>
              </Form.Item>

              <Form.Item>
                <div className="items-center justify-center flex w-full">
                  <Button
                    type="primary"
                    htmlType="submit"
                    style={{ marginRight: 5 }}
                  >
                    Compute VaR
                  </Button>
                  <Button htmlType="button" onClick={onReset}>
                    Reset
                  </Button>
                </div>
              </Form.Item>
            </Form>
          </div>
        </div>
      </div>
    </>
  );
};

export default VaRForm;
