"use client";

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useTheme } from "../contexts/ThemeContext";
import { ArrowLeft, Upload, Send } from "lucide-react";

export function ClaimSubmit() {
  const { theme } = useTheme();
  const navigate = useNavigate();

  const [policyNumber, setPolicyNumber] = useState("");
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [photos, setPhotos] = useState([]);

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

  const handleSubmit = (e) => {
    e.preventDefault();
    alert("Claim submitted successfully!");
    navigate("/dashboard/claims");
  };

  const handlePhotoUpload = (e) => {
    if (e.target.files) {
      setPhotos(Array.from(e.target.files));
    }
  };

  return (
    <div>
      {/* Header */}
      <div className="flex items-center gap-4 mb-6">
        <button
          onClick={() => navigate("/dashboard/claims")}
          className="p-2 rounded-lg"
        >
          <ArrowLeft className="w-5 h-5" />
        </button>

        <div>
          <h1 style={{ margin: 0 }}>Submit Insurance Claim</h1>
          <p>File a new claim for your insurance policy</p>
        </div>
      </div>

      {/* Form */}
      <div className="max-w-2xl">
        <form onSubmit={handleSubmit} className="p-6 rounded-xl" style={styles.card}>
          <div className="space-y-4">

            {/* Policy Number */}
            <input
              type="text"
              placeholder="Policy Number"
              value={policyNumber}
              onChange={(e) => setPolicyNumber(e.target.value)}
              className="w-full p-2 rounded"
              required
            />

            {/* Amount */}
            <input
              type="number"
              placeholder="Amount"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              className="w-full p-2 rounded"
              required
            />

            {/* Description */}
            <textarea
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full p-2 rounded"
              rows={4}
              required
            />

            {/* Upload */}
            <div>
              <input
                type="file"
                multiple
                onChange={handlePhotoUpload}
              />
              {photos.length > 0 && (
                <div>
                  {photos.map((file, i) => (
                    <div key={i}>{file.name}</div>
                  ))}
                </div>
              )}
            </div>

            {/* Submit */}
            <button
              type="submit"
              className="w-full flex items-center justify-center gap-2 p-2 rounded"
              style={{ background: "#0d6efd", color: "#fff" }}
            >
              <Send className="w-5 h-5" />
              Submit Claim
            </button>

          </div>
        </form>
      </div>
    </div>
  );
}
export default ClaimSubmit;