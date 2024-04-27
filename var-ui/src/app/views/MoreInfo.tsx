import { motion } from "framer-motion";
import React from "react";
import { fadeIn, transition } from "../utils/transitions";
import { styles } from "../styles";
import AboutVaRCard from "../components/AboutVaRCard";
import { Link } from "react-scroll";
import CustomButton from "../components/CustomButton";
import SectionWrapper from "../components/hoc/SectionWrapper";

const MoreInfo: React.FC = () => {
  return (
    <div>
      <div className="w-full xl:w-fit">
        <motion.h1
          variants={fadeIn("down")}
          transition={transition()}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: false }}
          className={`${styles.heroHeadText} text-white-100`}
        >
          More Information
        </motion.h1>
        <AboutVaRCard />
        <div className="flex flex-col items-center justify-center w-full mt-5 pb-5">
          <Link to="var" smooth>
            <CustomButton icon="/icons/arrow.png">Compute my VaR</CustomButton>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default SectionWrapper(MoreInfo, "info");
