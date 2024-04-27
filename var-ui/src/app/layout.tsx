import type { Metadata } from "next";
import React from "react";
import "./globals.css";

export const metadata: Metadata = {
  title: "Online Historical VaR Computation",
  description: "Online tool to compute VaR",
  icons: [{ rel: "icon", url: "/icons/favicon.ico" }],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="scrollbar-hide scroll-smooth">{children}</body>
    </html>
  );
}
