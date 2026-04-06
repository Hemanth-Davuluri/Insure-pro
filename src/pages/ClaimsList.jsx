"use client";

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { useTheme } from "../contexts/ThemeContext";
import { mockClaims } from "../data/mockData";
import { Search, Filter, Plus, Eye, AlertTriangle } from "lucide-react";

export function ClaimsList() {
  const { user } = useAuth();
  const { theme } = useTheme();
  const navigate = useNavigate();

  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  const getStyles = () => {
    if (theme === "glassmorphism") {
      return {
        card: {
          background: "rgba(255, 255, 255, 0.1)",
          backdropFilter: "blur(10px)",
          border: "1px solid rgba(255, 255, 255, 0.2)",
        },
        text: {
          primary: "#ffffff",
          secondary: "rgba(255, 255, 255, 0.8)",
        },
      };
    } else if (theme === "dark") {
      return {
        card: {
          background: "#1e293b",
          border: "1px solid #334155",
        },
        text: {
          primary: "#f1f5f9",
          secondary: "#cbd5e1",
        },
      };
    } else {
      return {
        card: {
          background: "#ffffff",
          border: "1px solid #dee2e6",
        },
        text: {
          primary: "#212529",
          secondary: "#6c757d",
        },
      };
    }
  };

  const styles = getStyles();

  const filteredClaims = mockClaims.filter((claim) => {
    const matchesSearch =
      claim.claimNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
      claim.customerName.toLowerCase().includes(searchTerm.toLowerCase()) ||
      claim.policyNumber.toLowerCase().includes(searchTerm.toLowerCase());

    const matchesStatus =
      statusFilter === "ALL" || claim.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  const getStatusColor = (status) => {
    switch (status) {
      case "APPROVED":
        return "#28a745";
      case "PENDING":
        return "#fd7e14";
      case "REJECTED":
      case "HIGH_RISK":
        return "#dc3545";
      default:
        return "#6c757d";
    }
  };

  return (
    <div>
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ margin: 0, marginBottom: "8px", color: styles.text.primary }}>
            Claims Management
          </h1>
          <p style={{ margin: 0, color: styles.text.secondary }}>
            {user?.role === "agent"
              ? "Review and process insurance claims"
              : "Track your insurance claims"}
          </p>
        </div>

        {user?.role === "customer" && (
          <button
            onClick={() => navigate("/dashboard/claims/submit")}
            className="flex items-center gap-2 px-4 py-2 rounded-lg"
            style={{
              background: "#0d6efd",
              color: "#fff",
              border: "none",
              cursor: "pointer",
            }}
          >
            <Plus className="w-5 h-5" />
            Submit Claim
          </button>
        )}
      </div>

      {/* Filters */}
      <div className="mb-6 p-4 rounded-xl" style={styles.card}>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5" />
            <input
              type="text"
              placeholder="Search..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 rounded-lg outline-none"
            />
          </div>

          <div className="relative">
            <Filter className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5" />
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              className="w-full pl-10 pr-4 py-2 rounded-lg outline-none"
            >
              <option value="ALL">All Statuses</option>
              <option value="PENDING">Pending</option>
              <option value="APPROVED">Approved</option>
              <option value="REJECTED">Rejected</option>
              <option value="HIGH_RISK">High Risk</option>
            </select>
          </div>
        </div>
      </div>

      {/* Table */}
      <div className="rounded-xl overflow-hidden" style={styles.card}>
        <table className="w-full">
          <thead>
            <tr>
              <th className="p-4 text-left">Claim</th>
              <th className="p-4 text-left">Customer</th>
              <th className="p-4 text-left">Policy</th>
              <th className="p-4 text-left">Amount</th>
              <th className="p-4 text-left">Status</th>
              <th className="p-4 text-left">Date</th>
              <th className="p-4 text-left">Actions</th>
            </tr>
          </thead>

          <tbody>
            {filteredClaims.map((claim) => (
              <tr key={claim.id}>
                <td className="p-4">{claim.claimNumber}</td>
                <td className="p-4">{claim.customerName}</td>
                <td className="p-4">{claim.policyNumber}</td>
                <td className="p-4">${claim.amount}</td>
                <td className="p-4">
                  <span style={{ color: getStatusColor(claim.status) }}>
                    {claim.status}
                  </span>
                </td>
                <td className="p-4">
                  {new Date(claim.submittedDate).toLocaleDateString()}
                </td>
                <td className="p-4">
                  <button onClick={() => alert(claim.claimNumber)}>
                    <Eye className="w-4 h-4" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filteredClaims.length === 0 && (
        <div className="text-center py-6">
          No claims found
        </div>
      )}
    </div>
  );
}
export default ClaimsList;