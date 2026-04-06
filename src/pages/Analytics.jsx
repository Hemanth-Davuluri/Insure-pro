"use client";

import React from "react";
import { useAuth } from "../contexts/AuthContext";
import { useTheme } from "../contexts/ThemeContext";
import { getAnalyticsData } from "../data/mockData";
import {
  BarChart,
  Bar,
  LineChart,
  Line,
  PieChart,
  Pie,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import { TrendingUp, PieChart as PieChartIcon, Activity } from "lucide-react";

export function Analytics() {
  const { user } = useAuth();
  const { theme } = useTheme();
  const analyticsData = getAnalyticsData();

  const getStyles = () => {
    if (theme === "glassmorphism") {
      return {
        card: {
          background: "rgba(255, 255, 255, 0.1)",
          backdropFilter: "blur(10px)",
          border: "1px solid rgba(255, 255, 255, 0.2)",
          boxShadow: "0 8px 32px 0 rgba(31, 38, 135, 0.37)",
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
          boxShadow: "0 1px 3px rgba(0, 0, 0, 0.3)",
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
          boxShadow: "0 1px 3px rgba(0, 0, 0, 0.1)",
        },
        text: {
          primary: "#212529",
          secondary: "#6c757d",
        },
      };
    }
  };

  const styles = getStyles();

  return (
    <div>
      <div className="mb-6">
        <h1 style={{ margin: 0, marginBottom: "8px", color: styles.text.primary }}>
          Analytics Dashboard
        </h1>
        <p style={{ margin: 0, color: styles.text.secondary }}>
          {user?.role === "agent"
            ? "Track performance metrics and trends"
            : "View your insurance coverage analytics"}
        </p>
      </div>

      <div className="space-y-6">
        {/* Policy Trends */}
        <div className="p-6 rounded-xl" style={styles.card}>
          <div className="flex items-center gap-3 mb-6">
            <TrendingUp className="w-5 h-5" style={{ color: "#0d6efd" }} />
            <h2 style={{ margin: 0, color: styles.text.primary }}>
              Policy Trends
            </h2>
          </div>

          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={analyticsData.policyTrends}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" stroke={styles.text.secondary} />
              <YAxis stroke={styles.text.secondary} />
              <Tooltip />
              <Legend />
              <Bar dataKey="home" fill="#0d6efd" />
              <Bar dataKey="auto" fill="#28a745" />
              <Bar dataKey="health" fill="#ffc107" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Claim Rates */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-6">
              <Activity className="w-5 h-5" />
              <h2 style={{ margin: 0, color: styles.text.primary }}>
                Claim Rates
              </h2>
            </div>

            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={analyticsData.claimRates}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" stroke={styles.text.secondary} />
                <YAxis stroke={styles.text.secondary} />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey="submitted" stroke="#0d6efd" />
                <Line type="monotone" dataKey="approved" stroke="#28a745" />
                <Line type="monotone" dataKey="rejected" stroke="#dc3545" />
              </LineChart>
            </ResponsiveContainer>
          </div>

          {/* Premium Distribution */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-6">
              <PieChartIcon className="w-5 h-5" />
              <h2 style={{ margin: 0, color: styles.text.primary }}>
                Premium Distribution
              </h2>
            </div>

            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={analyticsData.premiumAnalytics}
                  cx="50%"
                  cy="50%"
                  outerRadius={100}
                  dataKey="value"
                  label={({ type, percent }) =>
                    `${type}: ${(percent * 100).toFixed(0)}%`
                  }
                >
                  {analyticsData.premiumAnalytics.map((entry, index) => (
                    <Cell key={index} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Analytics;
