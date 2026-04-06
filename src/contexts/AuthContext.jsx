import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem("insure-pro-user");
    if (savedUser) {
      setUser(JSON.parse(savedUser));
    }
  }, []);

  const login = async (email, password, role) => {
    // Mock login
    const mockUser = {
      id: Math.random().toString(36).substr(2, 9),
      name: email.split("@")[0],
      email,
      role,
    };

    setUser(mockUser);
    localStorage.setItem("insure-pro-user", JSON.stringify(mockUser));
  };

  const register = async (name, email, password, role) => {
    // Mock registration
    const mockUser = {
      id: Math.random().toString(36).substr(2, 9),
      name,
      email,
      role,
    };

    setUser(mockUser);
    localStorage.setItem("insure-pro-user", JSON.stringify(mockUser));
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("insure-pro-user");
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within AuthProvider");
  }

  return context;
}