"use client";

import React from "react";
import * as AvatarPrimitive from "@radix-ui/react-avatar";

// Utility function (since cn was imported earlier)
function cn(...classes) {
  return classes.filter(Boolean).join(" ");
}

// Avatar Root Component
export function Avatar({ className, ...props }) {
  return (
    <AvatarPrimitive.Root
      data-slot="avatar"
      className={cn(
        "relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full",
        className
      )}
      {...props}
    />
  );
}

// Avatar Image Component
export function AvatarImage({ className, ...props }) {
  return (
    <AvatarPrimitive.Image
      data-slot="avatar-image"
      className={cn("aspect-square h-full w-full", className)}
      {...props}
    />
  );
}

// Avatar Fallback Component
export function AvatarFallback({ className, ...props }) {
  return (
    <AvatarPrimitive.Fallback
      data-slot="avatar-fallback"
      className={cn(
        "bg-gray-200 flex h-full w-full items-center justify-center rounded-full",
        className
      )}
      {...props}
    />
  );
}