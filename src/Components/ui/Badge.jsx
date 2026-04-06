"use client";

import React from "react";
import { Slot } from "@radix-ui/react-slot";

// Utility function (replacement for cn)
function cn(...classes) {
  return classes.filter(Boolean).join(" ");
}

// Badge variant styles
const badgeVariants = (variant = "default") => {
  const base =
    "inline-flex items-center justify-center rounded-md border px-2 py-0.5 text-xs font-medium w-fit whitespace-nowrap";

  const variants = {
    default: "bg-blue-500 text-white border-transparent",
    secondary: "bg-gray-200 text-black border-transparent",
    destructive: "bg-red-500 text-white border-transparent",
    outline: "text-black border-gray-300",
  };

  return cn(base, variants[variant]);
};

// Badge Component
export function Badge({
  className,
  variant = "default",
  asChild = false,
  ...props
}) {
  const Comp = asChild ? Slot : "span";

  return (
    <Comp
      data-slot="badge"
      className={cn(badgeVariants(variant), className)}
      {...props}
    />
  );
}

export { badgeVariants };