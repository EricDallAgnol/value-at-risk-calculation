export const transition = () => {
  return {
    duration: 0.75,
    delay: 0.2,
    ease: "easeIn",
  };
};

export const fadeIn = (direction: "up" | "down") => {
  return {
    hidden: {
      opacity: 0,
      y: direction === "down" ? -85 : 85,
    },
    visible: {
      opacity: 1,
      y: 0,
    },
  };
};
