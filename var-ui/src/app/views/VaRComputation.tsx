"use client";

import React, { useState } from "react";
import TableComponent from "../components/TableComponent";
import UploadComponent from "../components/UploadComponent";
import VaRForm from "../components/VaRForm";
import { DataType } from "../types/types";
import { styles } from "../styles";
import { motion } from "framer-motion";
import { Link } from "react-scroll";
import { fadeIn, transition } from "../utils/transitions";
import CustomButton from "../components/CustomButton";
import SectionWrapper from "../components/hoc/SectionWrapper";

const VaRComputation: React.FC = () => {
  const [selectedTrades, setSelectedTrades] = useState<DataType[]>();
  const [refresh, setRefresh] = useState<boolean>(true);

  return (
    <div className="w-full xl:w-fit">
      <motion.h1
        variants={fadeIn("down")}
        transition={transition()}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: false }}
        className={`${styles.heroHeadText} text-white-100 mb-5`}
      >
        VaR Computation
      </motion.h1>
      <div className="items-center justify-center w-full xl:w-fit">
        <div className="upload">
          <UploadComponent setRefresh={setRefresh} />
        </div>
        <div className="table">
          <TableComponent
            setSelectedTrades={setSelectedTrades}
            setRefresh={setRefresh}
            refresh={refresh}
          />
        </div>
        <div className="form">
          <VaRForm selectedTrades={selectedTrades} />
        </div>
        <motion.div
          variants={fadeIn("down")}
          transition={transition()}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: false }}
          className="flex flex-col items-center justify-center w-full mt-5 gap-5"
        >
          <Link to="info" smooth>
            <CustomButton icon="/icons/arrow.png">
              More Information
            </CustomButton>
          </Link>
        </motion.div>
      </div>
    </div>
  );
};

export default SectionWrapper(VaRComputation, "var");
