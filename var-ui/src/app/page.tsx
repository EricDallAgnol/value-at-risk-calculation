"use client";

import Hero from "./views/Hero";
import MoreInfo from "./views/MoreInfo";
import VaRComputation from "./views/VaRComputation";
import React from "react";

export default function Home() {
  return (
    <div className="relative z-0 bg-gradient-to-r from-slate-900 min-h-screen min-w-[430px]">
      <Hero />
      <VaRComputation />
      <MoreInfo />
    </div>
  );
}
