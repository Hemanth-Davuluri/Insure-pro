"use client";

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTheme } from "../contexts/ThemeContext";
import { Save, ArrowLeft } from "lucide-react";

export function PolicyCreate() {
  const { theme } = useTheme();
  const navigate = useNavigate();

  const [coverageType, setCoverageType] = useState("Home");
  const [customerName, setCustomerName] = useState("");
  const [basePremium, setBasePremium] = useState(0);

  // Dynamic fields
  const [homeAddress, setHomeAddress] = useState("");
  const [propertyValue, setPropertyValue] = useState(0);
  const [vehicleMake, setVehicleMake] = useState("");
  const [vehicleModel, setVehicleModel] = useState("");
  const [vehicleYear, setVehicleYear] = useState(2024);
  const [age, setAge] = useState(30);
  const [medicalHistory, setMedicalHistory] = useState("");

  const getStyles = () => {
    if (theme === "glassmorphism") {
      return {
        card: {
          background: "rgba(255, 255, 255, 0.1)",
          backdropFilter: "blur(10px)",
          border: "1px solid rgba(255, 255, 255, 0.2)",
        },
        text: { primary: "#fff", secondary: "rgba(255,255,255,0.8)" },
      };
    } else if (theme === "dark") {
      return {
        card: {
          background: "#1e293b",
          border: "1px solid #334155",
        },
        text: { primary: "#f1f5f9", secondary: "#cbd5e1" },
      };
    } else {
      return {
        card: {
          background: "#fff",
          border: "1px solid #dee2e6",
        },
        text: { primary: "#212529", secondary: "#6c757d" },
      };
    }
  };

  const styles = getStyles();

  const tax = basePremium * 0.15;
  const finalPremium = basePremium + tax;

  const handleSubmit = (e) => {
    e.preventDefault();
    alert("Policy created successfully!");
    navigate("/dashboard/policies");
  };

  return (
    <div>
      {/* Header */}
      <div className="flex items-center gap-4 mb-6">
        <button
          onClick={() => navigate("/dashboard/policies")}
          className="p-2 rounded-lg"
          style={{ background: "#e9ecef" }}
        >
          <ArrowLeft className="w-5 h-5" />
        </button>
        <div>
          <h1>Create New Policy</h1>
          <p>Fill in the details to create a new insurance policy</p>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Form */}
        <div className="lg:col-span-2">
          <form onSubmit={handleSubmit} className="p-6 rounded-xl" style={styles.card}>
            <div className="space-y-4">

              {/* Customer */}
              <input
                type="text"
                placeholder="Customer Name"
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                required
                className="w-full px-4 py-2 rounded-lg"
              />

              {/* Coverage */}
              <select
                value={coverageType}
                onChange={(e) => setCoverageType(e.target.value)}
                className="w-full px-4 py-2 rounded-lg"
              >
                <option value="Home">Home</option>
                <option value="Auto">Auto</option>
                <option value="Health">Health</option>
              </select>

              {/* HOME */}
              {coverageType === "Home" && (
                <>
                  <input
                    placeholder="Property Address"
                    value={homeAddress}
                    onChange={(e) => setHomeAddress(e.target.value)}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                  <input
                    type="number"
                    placeholder="Property Value"
                    value={propertyValue}
                    onChange={(e) => {
                      const val = Number(e.target.value);
                      setPropertyValue(val);
                      setBasePremium(val * 0.0024);
                    }}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                </>
              )}

              {/* AUTO */}
              {coverageType === "Auto" && (
                <>
                  <input
                    placeholder="Vehicle Make"
                    value={vehicleMake}
                    onChange={(e) => setVehicleMake(e.target.value)}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                  <input
                    placeholder="Vehicle Model"
                    value={vehicleModel}
                    onChange={(e) => setVehicleModel(e.target.value)}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                  <input
                    type="number"
                    value={vehicleYear}
                    onChange={(e) => {
                      const year = Number(e.target.value);
                      setVehicleYear(year);
                      setBasePremium(800 + (2026 - year) * 50);
                    }}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                </>
              )}

              {/* HEALTH */}
              {coverageType === "Health" && (
                <>
                  <input
                    type="number"
                    value={age}
                    onChange={(e) => {
                      const a = Number(e.target.value);
                      setAge(a);
                      setBasePremium(1200 + a * 30);
                    }}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                  <textarea
                    placeholder="Medical History"
                    value={medicalHistory}
                    onChange={(e) => setMedicalHistory(e.target.value)}
                    className="w-full px-4 py-2 rounded-lg"
                  />
                </>
              )}

              <button
                type="submit"
                className="w-full py-3 rounded-lg flex justify-center gap-2"
                style={{ background: "#0d6efd", color: "#fff" }}
              >
                <Save className="w-5 h-5" />
                Create Policy
              </button>
            </div>
          </form>
        </div>

        {/* Calculator */}
        <div className="p-6 rounded-xl" style={styles.card}>
          <h3>Premium Calculator</h3>
          <p>Base: ${basePremium.toFixed(2)}</p>
          <p>Tax: ${tax.toFixed(2)}</p>
          <h4>Final: ${finalPremium.toFixed(2)}</h4>
        </div>
      </div>
    </div>
  );
}
export default PolicyCreate;