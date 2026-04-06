"use client";

import React from "react";
import { useTheme } from "next-themes";
import { Toaster as Sonner, ToasterProps } from "sonner";

const Toaster = (props) => {
  const { theme = "system" } = useTheme(); // Get current theme

  return (
    <Sonner
      theme={theme}
      className="toaster group"
      style={{
        "--normal-bg": "var(--popover)",             // Toast background
        "--normal-text": "var(--popover-foreground)", // Toast text color
        "--normal-border": "var(--border)",          // Toast border color
      }}
      {...props}
    />
  );
};

export default Toaster;