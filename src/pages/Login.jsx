"use client";

import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { useTheme } from "../contexts/ThemeContext";
import { Shield, LogIn } from "lucide-react";

export function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const { theme, setTheme } = useTheme();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("customer");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await login(email, password, role);
      navigate("/dashboard");
    } catch (error) {
      console.error("Login failed:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div
      className="min-h-screen flex items-center justify-center p-6"
      style={{
        background:
          theme === "glassmorphism"
            ? "linear-gradient(135deg, #667eea 0%, #764ba2 100%)"
            : theme === "dark"
            ? "#0f172a"
            : "#f8f9fa",
      }}
    >
      <div
        className="w-full max-w-md p-8 rounded-2xl"
        style={{
          background:
            theme === "glassmorphism"
              ? "rgba(255, 255, 255, 0.1)"
              : theme === "dark"
              ? "#1e293b"
              : "#ffffff",
          backdropFilter: theme === "glassmorphism" ? "blur(10px)" : "none",
          boxShadow:
            theme === "glassmorphism"
              ? "0 8px 32px rgba(31, 38, 135, 0.37)"
              : theme === "dark"
              ? "0 10px 25px rgba(0, 0, 0, 0.5)"
              : "0 10px 25px rgba(0, 0, 0, 0.1)",
          border:
            theme === "glassmorphism"
              ? "1px solid rgba(255, 255, 255, 0.2)"
              : "none",
          color:
            theme === "dark" || theme === "glassmorphism"
              ? "#f1f5f9"
              : "#212529",
        }}
      >
        <div className="flex items-center justify-center mb-8">
          <Shield
            className="w-12 h-12 mr-3"
            style={{
              color: theme === "glassmorphism" ? "#ffffff" : "#0d6efd",
            }}
          />
          <h1 style={{ margin: 0 }}>InsurePro</h1>
        </div>

        {/* Theme Switch */}
        <div className="mb-6 flex gap-2 justify-center">
          {["minimal", "glassmorphism", "dark"].map((t) => (
            <button
              key={t}
              onClick={() => setTheme(t)}
              className="px-3 py-1 rounded-lg capitalize"
              style={{
                background:
                  theme === t
                    ? theme === "glassmorphism"
                      ? "rgba(255,255,255,0.3)"
                      : "#0d6efd"
                    : theme === "glassmorphism"
                    ? "rgba(255,255,255,0.1)"
                    : theme === "dark"
                    ? "#334155"
                    : "#e9ecef",
                color: theme === t ? "#fff" : "#6c757d",
              }}
            >
              {t}
            </button>
          ))}
        </div>

        {/* Form */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            className="w-full px-4 py-2 rounded-lg border"
          />

          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            className="w-full px-4 py-2 rounded-lg border"
          />

          {/* Role */}
          <div className="flex gap-4">
            {["agent", "customer"].map((r) => (
              <label key={r}>
                <input
                  type="radio"
                  value={r}
                  checked={role === r}
                  onChange={(e) => setRole(e.target.value)}
                />{" "}
                {r}
              </label>
            ))}
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 rounded-lg flex items-center justify-center gap-2"
            style={{
              background: "#0d6efd",
              color: "#fff",
              opacity: loading ? 0.7 : 1,
            }}
          >
            <LogIn className="w-5 h-5" />
            {loading ? "Signing in..." : "Sign In"}
          </button>
        </form>

        {/* Register */}
        <div className="mt-6 text-center">
          <span>Don't have an account? </span>
          <Link to="/register" style={{ color: "#0d6efd" }}>
            Register
          </Link>
        </div>
      </div>
    </div>
  );
}
export default Login;