import { motion } from "framer-motion";
import React from "react";
import { fadeIn, transition } from "../utils/transitions";
import { styles } from "../styles";
import AboutToolCard from "../components/AboutToolCard";
import { Link } from "react-scroll";
import CustomButton from "../components/CustomButton";
import SectionWrapper from "../components/hoc/SectionWrapper";

const Hero: React.FC = () => {
  return (
    <>
      <div className="w-full xl:w-fit">
        <motion.h1
          variants={fadeIn("down")}
          transition={transition()}
          initial="hidden"
          whileInView="visible"
          viewport={{ once: false }}
          className={`${styles.heroHeadText} text-white-100`}
        >
          Online Value-At-Risk Computation
        </motion.h1>
      </div>
      <AboutToolCard />

      <motion.div
        variants={fadeIn("down")}
        transition={transition()}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: false }}
        className="my-12 flex flex-col items-center sm:flex-row justify-center w-full gap-6"
      >
        <Link to="var" smooth>
          <CustomButton icon="/icons/arrow.png">Compute my VaR</CustomButton>
        </Link>
        <Link to="info" smooth>
          <CustomButton icon="/icons/arrow.png">More Information</CustomButton>
        </Link>
      </motion.div>
    </>
  );
};

export default SectionWrapper(Hero, "hero");
