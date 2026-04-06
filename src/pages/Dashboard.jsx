"use client";

import React from "react";
import { useAuth } from "../contexts/AuthContext";
import { useTheme } from "../contexts/ThemeContext";
import { getDashboardStats } from "../data/mockData";
import {
  FileText,
  ClipboardList,
  DollarSign,
  TrendingUp,
  AlertCircle,
  CheckCircle,
} from "lucide-react";

export function Dashboard() {
  const { user } = useAuth();
  const { theme } = useTheme();

  const stats = getDashboardStats(user?.role);

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

  const statCards = [
    {
      title: "Total Policies",
      value: stats.totalPolicies,
      icon: FileText,
      color: "#0d6efd",
      change: "+12%",
    },
    {
      title: "Active Policies",
      value: stats.activePolicies,
      icon: CheckCircle,
      color: "#28a745",
      change: "+8%",
    },
    {
      title: "Total Claims",
      value: stats.totalClaims,
      icon: ClipboardList,
      color: "#ffc107",
      change: "+5%",
    },
    {
      title: "Pending Claims",
      value: stats.pendingClaims,
      icon: AlertCircle,
      color: "#fd7e14",
      change: "-3%",
    },
    {
      title: "Total Premium",
      value: `$${stats.totalPremium.toLocaleString()}`,
      icon: DollarSign,
      color: "#17a2b8",
      change: "+15%",
    },
    {
      title: "Claims Paid",
      value: `$${stats.claimsPaid.toLocaleString()}`,
      icon: TrendingUp,
      color: "#6f42c1",
      change: "+10%",
    },
  ];

  return (
    <div>
      {/* Header */}
      <div className="mb-6">
        <h1 style={{ margin: 0, marginBottom: "8px", color: styles.text.primary }}>
          Welcome back, {user?.name}
        </h1>
        <p style={{ margin: 0, color: styles.text.secondary }}>
          Here's an overview of your{" "}
          {user?.role === "agent" ? "portfolio" : "insurance dashboard"}
        </p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {statCards.map((stat) => {
          const Icon = stat.icon;
          return (
            <div key={stat.title} className="p-6 rounded-xl" style={styles.card}>
              <div className="flex justify-between mb-4">
                <Icon className="w-6 h-6" style={{ color: stat.color }} />
                <span>{stat.change}</span>
              </div>
              <div>
                <div style={{ color: styles.text.secondary }}>{stat.title}</div>
                <div style={{ color: styles.text.primary }}>{stat.value}</div>
              </div>
            </div>
          );
        })}
      </div>

      {/* Quick Actions */}
      <div className="mt-8">
        <h2 style={{ color: styles.text.primary }}>Quick Actions</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          {user?.role === "agent" ? (
            <>
              <ActionCard title="Create Policy" href="/dashboard/policies/create" styles={styles} />
              <ActionCard title="View Policies" href="/dashboard/policies" styles={styles} />
              <ActionCard title="Review Claims" href="/dashboard/claims" styles={styles} />
              <ActionCard title="View Analytics" href="/dashboard/analytics" styles={styles} />
            </>
          ) : (
            <>
              <ActionCard title="My Policies" href="/dashboard/policies" styles={styles} />
              <ActionCard title="Submit Claim" href="/dashboard/claims/submit" styles={styles} />
              <ActionCard title="Track Claims" href="/dashboard/claims" styles={styles} />
              <ActionCard title="View Analytics" href="/dashboard/analytics" styles={styles} />
            </>
          )}
        </div>
      </div>
    </div>
  );
}

// Action Card Component
function ActionCard({ title, href, styles }) {
  return (
    <a
      href={href}
      className="block p-4 rounded-xl hover:scale-105 transition"
      style={styles.card}
    >
      <h3 style={{ color: styles.text.primary }}>{title}</h3>
    </a>
  );
}
export default Dashboard;