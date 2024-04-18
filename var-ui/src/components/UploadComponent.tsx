import { UploadOutlined } from '@ant-design/icons';
import {
  Button,
  Upload,
  UploadFile,
  UploadProps,
  message
} from 'antd';
import { RcFile } from 'antd/es/upload';
import { useState } from 'react';

const UploadComponent = ({setRefresh}: {setRefresh: (value: Boolean) => void}) => {

    const [fileList, setFileList] = useState<UploadFile[]>([]);
    const [uploading, setUploading] = useState(false);
    
    const props: UploadProps = {
        onRemove: (file) => {
          const index = fileList.indexOf(file);
          const newFileList = fileList.slice();
          newFileList.splice(index, 1);
          setFileList(newFileList);
        },
        beforeUpload: (file) => {
          const isCSV = file.type === 'text/csv';
          if (!isCSV) {
            message.error(`${file.name} is not a CSV file !`, 3);
          }
          else {
            setFileList([...fileList, file]);
          }
    
          return false;
        },
        fileList,
      };

    const handleUpload = () => {
        const formData = new FormData();
        var myHeaders = new Headers();
        const requestOptions = {
            method: 'POST',
            body: formData,
            headers: myHeaders
        };
        
        setUploading(true);
        formData.append('file', fileList[0] as RcFile);
        fetch(`${process.env.REACT_APP_SERVER_URL}/data/upload-pnl`, requestOptions)
            .then(response => {
              if (response.status !== 200) {
                throw new Error(response.statusText)
              }
            })
            .then(result => console.log(result))
            .catch(error => message.error(`Error : ${error}`, 3))
            .finally(() => {
              setRefresh(true)
              setFileList([])
              setUploading(false)
            });
    }

    return (
    <>
        <Upload {...props}>
            <Button icon={<UploadOutlined />} type="primary">Click to upload</Button>
        </Upload>
        <Button
        type="primary"
        onClick={handleUpload}
        loading={uploading}
        disabled={fileList.length === 0}
        style={{ marginTop: 16 }}
      >
        {uploading ? 'Uploading' : 'Start Upload'}
      </Button>

      <p style={{ fontStyle:'italic'}}>The application is expecting a csv file with the following header fields : AsOfDate | TradeID | PnL</p>

    </>
)
};

export default UploadComponent;
