"use client";

import React from "react";
import * as AspectRatioPrimitive from "@radix-ui/react-aspect-ratio";

// AspectRatio Component
export function AspectRatio(props) {
  return (
    <AspectRatioPrimitive.Root
      data-slot="aspect-ratio"
      {...props}
    />
  );
}