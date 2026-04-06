import React from "react";

// Utility function to merge class names
function cn(...classes) {
  return classes.filter(Boolean).join(" ");
}

// Alert variant styles
const alertVariants = (variant = "default") => {
  const base =
    "relative w-full rounded-lg border px-4 py-3 text-sm grid items-start";

  const variants = {
    default: "bg-white text-black",
    destructive: "bg-red-100 text-red-600",
  };

  return cn(base, variants[variant]);
};

// Alert Component
export function Alert({ className, variant = "default", children }) {
  return (
    <div role="alert" className={cn(alertVariants(variant), className)}>
      {children}
    </div>
  );
}

// Alert Title Component
export function AlertTitle({ className, children }) {
  return (
    <div className={cn("font-medium text-sm mb-1", className)}>
      {children}
    </div>
  );
}

// Alert Description Component
export function AlertDescription({ className, children }) {
  return (
    <div className={cn("text-gray-600 text-sm", className)}>
      {children}
    </div>
  );
}