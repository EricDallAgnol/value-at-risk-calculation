import { motion } from "framer-motion";
import React from "react";
import { texts } from "../utils/texts";
import { fadeIn, transition } from "../utils/transitions";

const AboutVaRCard: React.FC = () => {
  return (
    <div className="w-full h-full mt-5">
      <motion.div
        variants={fadeIn("down")}
        transition={transition()}
        initial="hidden"
        whileInView="visible"
        viewport={{ once: false }}
        className="green-pink-gradient p-[1px] rounded-[20px]"
      >
        <div className="bg-tertiary rounded-[20px] px-12 flex flex-col pb-3">
          {texts.aboutVaR}
        </div>
      </motion.div>
    </div>
  );
};

export default AboutVaRCard;
