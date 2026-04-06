import React from "react";
import { createBrowserRouter, Navigate } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import DashboardLayout from "./Components/DashboardLayout";
import Dashboard from "./pages/Dashboard";
import PolicyList from "./pages/PolicyList";
import PolicyCreate from "./pages/PolicyCreate";
import PolicyDetail from "./pages/PolicyDetail";
import ClaimsList from "./pages/ClaimsList";
import ClaimSubmit from "./pages/ClaimSubmit";
import Analytics from "./pages/Analytics";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <Navigate to="/login" replace />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
  {
    path: "/dashboard",
    element: <DashboardLayout />,
    children: [
      { index: true, element: <Dashboard /> },
      { path: "policies", element: <PolicyList /> },
      { path: "policies/create", element: <PolicyCreate /> },
      { path: "policies/:id", element: <PolicyDetail /> },
      { path: "claims", element: <ClaimsList /> },
      { path: "claims/submit", element: <ClaimSubmit /> },
      { path: "analytics", element: <Analytics /> },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/login" replace />,
  },
]);