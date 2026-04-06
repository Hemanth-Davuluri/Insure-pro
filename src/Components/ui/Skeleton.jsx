"use client";

import React from "react";
import { cn } from "./utils";

// Skeleton component
export function Skeleton({ className, ...props }) {
  return (
    <div
      data-slot="skeleton"
      className={cn("bg-accent animate-pulse rounded-md", className)}
      {...props}
    />
  );
}