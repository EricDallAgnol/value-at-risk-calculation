import { Button, Upload, UploadFile, UploadProps, message } from "antd";
import { RcFile } from "antd/es/upload";
import React, { useState } from "react";
import CustomButton from "./CustomButton";
import "../globals.css";

const UploadComponent = ({
  setRefresh,
}: {
  setRefresh: (value: boolean) => void;
}) => {
  const [fileList, setFileList] = useState<UploadFile[]>([]);
  const [uploading, setUploading] = useState(false);

  const props: UploadProps = {
    className: "text-white",
    onRemove: (file) => {
      const index = fileList.indexOf(file);
      const newFileList = fileList.slice();
      newFileList.splice(index, 1);
      setFileList(newFileList);
    },
    beforeUpload: (file) => {
      const isCSV = file.type === "text/csv";
      if (!isCSV) {
        message.error(`${file.name} is not a CSV file !`, 3);
      } else {
        setFileList([...fileList, file]);
      }

      return false;
    },
    fileList,
  };

  const handleUpload = () => {
    const formData = new FormData();
    const myHeaders = new Headers();
    const requestOptions = {
      method: "POST",
      body: formData,
      headers: myHeaders,
    };

    setUploading(true);
    formData.append("file", fileList[0] as RcFile);
    fetch(`/data/upload-pnl`, requestOptions)
      .then((response) => {
        if (response.status !== 200) {
          throw new Error(response.statusText);
        }
      })
      .then((result) => console.log(result))
      .catch((error) => message.error(`Error : ${error}`, 3))
      .finally(() => {
        setRefresh(true);
        setFileList([]);
        setUploading(false);
      });
  };

  return (
    <div className="flex flex-col justify-center items-center w-full">
      <div className="green-pink-gradient p-[1px] rounded-[20px] w-max">
        <div className="bg-tertiary rounded-[20px] px-12 py-5 flex flex-col my-7">
          <Upload {...props}>
            <CustomButton icon="/icons/arrow.png">Click to upload</CustomButton>
          </Upload>
          <Button
            onClick={handleUpload}
            type="primary"
            loading={uploading}
            disabled={fileList.length === 0}
            style={{ marginTop: 8, color: "white" }}
          >
            {uploading ? "Uploading" : "Start Upload"}
          </Button>
        </div>
      </div>

      <p style={{ fontStyle: "italic", marginBottom: 2 }}>
        The application is expecting a csv file with the following header fields
        : AsOfDate | TradeID | PnL
      </p>
    </div>
  );
};

export default UploadComponent;
