import React, { FC, ReactNode } from "react";

interface ButtonProps {
  children: ReactNode;
  icon?: string;
  onClick?: () => void;
}

const CustomButton: FC<ButtonProps> = ({ children, icon, onClick }) => {
  return (
    <button
      onClick={onClick}
      className={
        "bg-tertiary py-3 px-8 rounded-xl outline-none w-fit text-white font-bold shadow-md shadow-primary hover:text-[#6e4cb8] hover:scale-110"
      }
    >
      {icon ? (
        <div className="flex items-center gap-2.5 justify-center">
          <p className="[text-shadow:_0_4px_4px_rgb(0_0_0_/_50%)]">
            {children}
          </p>
          <img width={25} height={25} src={icon} alt="" />
        </div>
      ) : (
        <p className="[text-shadow:_0_4px_4px_rgb(0_0_0_/_50%)]">{children}</p>
      )}
    </button>
  );
};

export default CustomButton;
