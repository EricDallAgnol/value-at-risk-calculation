import { motion } from "framer-motion";
import React from "react";
import { fadeIn, transition } from "../utils/transitions";
import { styles } from "../styles";
import { texts } from "../utils/texts";

const AboutToolCard: React.FC = () => {
  return (
    <div className="w-full mt-5">
      <motion.div
        variants={fadeIn("down")}
        transition={transition()}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: false }}
        className="green-pink-gradient p-[1px] rounded-[20px]"
      >
        <div className="bg-tertiary rounded-[20px] px-12 min-h-[180px] h-fit flex flex-col pb-8">
          <h1 className={styles.sectionHeadText}>About this tool</h1>
          {texts.aboutTool}
        </div>
      </motion.div>
    </div>
  );
};

export default AboutToolCard;
