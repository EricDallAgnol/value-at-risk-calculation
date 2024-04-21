import { Button, Form, InputNumber, Modal, Select, notification } from "antd";
import { useMemo, useState } from "react";
import { DataType } from "../App";

const { Option } = Select;

interface VaRFormType {
    quantile: number;
    mode: string;
    interpolation: string;
}

const layout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 10 },
};

const tailLayout = {
  wrapperCol: { offset: 8, span: 16 },
};

const VaRForm = ({selectedTrades}:{selectedTrades: DataType[] | undefined}) => {
    const [form] = Form.useForm<VaRFormType>();
    const [api, contextHolder] = notification.useNotification();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [modalContext, setModalContext] = useState<VaRFormType & { result: string}>();
    const selectedTradesPnL = useMemo(() => selectedTrades, [selectedTrades]);

    const onModeChange = (value: string) => {
        form.setFieldsValue({ mode: value });
    };

    const onInterpolationChange = (value: string) => {
        form.setFieldsValue({ interpolation: value });
    };

    const openErrorNotification = (error: string) => {
    api["error"]({
        message: '',
        description: `${error}`,
        });
    };

    const showModal = (result: string, values: VaRFormType) => {
        setModalContext({
            result: result,
            quantile: values.quantile,
            mode: values.mode,
            interpolation: values.interpolation

        })
        setIsModalOpen(true);
      };
    
      const handleOk = () => {
        setIsModalOpen(false);
      };
    
      const handleCancel = () => {
        setIsModalOpen(false);
      };

    const onFinish = (values: VaRFormType) => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        fetch(`${process.env.REACT_APP_SERVER_URL}/calculation/var`, {
            method: "POST",
            headers: myHeaders,
            redirect: "follow",
            body: JSON.stringify({
                "quantile": values.quantile,
                "mode": values.mode,
                "interpolation": values.interpolation,
                "tradePositionWithPnL": selectedTradesPnL
            })
          })
            .then(response => response.text())
            .then(result => {
                if (!isNaN(parseFloat(result))) {
                  showModal(result, values);
                }
                else {
                  throw Error(result);
                }
            })
            .catch(error => {
              openErrorNotification(error)
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
        <Form
        {...layout}
        labelAlign="left"
        form={form}
        name="control-hooks"
        onFinish={onFinish}
        style={{ maxWidth: 600 }}
      >
        <Form.Item 
            name="quantile" 
            label={<label>Quantile</label>} 
            rules={[{ required: true }]}
        >
          <InputNumber min={0.001} max={0.999} step={0.001}/>
        </Form.Item>

        <Form.Item 
            name="mode" 
            label={<label>Mode</label>}  
            rules={[{ required: true }]}>
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
            label={<label>Interpolation</label>} 
            rules={[{ required: true }]}>
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

        <Form.Item {...tailLayout}>
            <div style={{margin: 10}}>
            <Button type="primary" htmlType="submit" style={{marginRight: 10}}>
                Compute VaR
            </Button>
            <Button htmlType="button" onClick={onReset}>
                Reset
            </Button>
          </div>
        </Form.Item>
      </Form>
    </>
    )
}

export default VaRForm;