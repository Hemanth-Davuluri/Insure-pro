"use client";

import React from "react";
import { Slot } from "@radix-ui/react-slot";
import { ChevronRight, MoreHorizontal } from "lucide-react";

// Utility function (replacement for cn)
function cn(...classes) {
  return classes.filter(Boolean).join(" ");
}

// Breadcrumb Wrapper
export function Breadcrumb(props) {
  return <nav aria-label="breadcrumb" {...props} />;
}

// Breadcrumb List
export function BreadcrumbList({ className, ...props }) {
  return (
    <ol
      className={cn(
        "flex flex-wrap items-center gap-2 text-sm text-gray-500",
        className
      )}
      {...props}
    />
  );
}

// Breadcrumb Item
export function BreadcrumbItem({ className, ...props }) {
  return (
    <li
      className={cn("inline-flex items-center gap-1.5", className)}
      {...props}
    />
  );
}

// Breadcrumb Link
export function BreadcrumbLink({
  asChild = false,
  className,
  ...props
}) {
  const Comp = asChild ? Slot : "a";

  return (
    <Comp
      className={cn(
        "hover:text-black transition-colors cursor-pointer",
        className
      )}
      {...props}
    />
  );
}

// Current Page
export function BreadcrumbPage({ className, ...props }) {
  return (
    <span
      role="link"
      aria-disabled="true"
      aria-current="page"
      className={cn("text-black font-normal", className)}
      {...props}
    />
  );
}

// Separator
export function BreadcrumbSeparator({
  children,
  className,
  ...props
}) {
  return (
    <li
      role="presentation"
      aria-hidden="true"
      className={cn("", className)}
      {...props}
    >
      {children || <ChevronRight size={14} />}
    </li>
  );
}

// Ellipsis (for collapsed breadcrumbs)
export function BreadcrumbEllipsis({ className, ...props }) {
  return (
    <span
      role="presentation"
      aria-hidden="true"
      className={cn(
        "flex h-8 w-8 items-center justify-center",
        className
      )}
      {...props}
    >
      <MoreHorizontal size={16} />
      <span className="sr-only">More</span>
    </span>
  );
}