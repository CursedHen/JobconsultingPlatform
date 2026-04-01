import React, { useState } from "react";
import Availability from "./Availability";
import Booking from "./Booking";
import { suggestServices, loginAndSave } from "./api"; 

function App() {
  const [view, setView] = useState("login"); // 'login' or 'dashboard'
  const [credentials, setCredentials] = useState({ username: "", password: "" });
  const [aiResponse, setAiResponse] = useState("");
  const [profile, setProfile] = useState("");

  // Handle the "Login" which actually saves the user to PostgreSQL
  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await loginAndSave(credentials);
      alert(response.data); // Should show "User info saved successfully!"
      setView("dashboard"); // Move to dashboard after saving
    } catch (err) {
      alert("Error saving to DB: " + (err.response?.data || err.message));
    }
  };

  const handleAiConsult = async () => {
    setAiResponse("AI is thinking...");
    try {
      const res = await suggestServices(profile);
      setAiResponse(res.data);
    } catch (err) {
      setAiResponse("AI Service Unavailable.");
    }
  };

  // --- VIEW: LOGIN / SIGNUP SCREEN ---
  if (view === "login") {
    return (
      <div style={{ padding: "50px", maxWidth: "400px", margin: "auto", textAlign: "center" }}>
        <h1>Job Consulting Platform</h1>
        <div style={{ border: "1px solid #ccc", padding: "20px", borderRadius: "10px", background: "#fff" }}>
          <h3>Login / Sign Up</h3>
          <form onSubmit={handleLoginSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
            <input 
              type="text" 
              placeholder="Username" 
              required
              onChange={(e) => setCredentials({...credentials, username: e.target.value})} 
              style={{ padding: "10px" }}
            />
            <input 
              type="password" 
              placeholder="Password" 
              required
              onChange={(e) => setCredentials({...credentials, password: e.target.value})} 
              style={{ padding: "10px" }}
            />
            <button type="submit" style={{ padding: "10px", background: "#007bff", color: "white", border: "none", cursor: "pointer" }}>
              Enter Platform
            </button>
          </form>
          <p style={{ fontSize: "0.8rem", color: "#666", marginTop: "10px" }}>
            *Note: Entering credentials saves your info to the PostgreSQL database.
          </p>
        </div>
      </div>
    );
  }

  // --- VIEW: DASHBOARD ---
  return (
    <div style={{ padding: "20px", fontFamily: "sans-serif" }}>
      <button onClick={() => setView("login")} style={{ float: "right" }}>Logout</button>
      <h1>Consultant Dashboard</h1>
      
      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
        <Availability />
        <Booking />
      </div>
      
      <hr style={{ margin: "40px 0" }} />

      <section style={{ backgroundColor: "#eef2f3", padding: "25px", borderRadius: "12px" }}>
        <h2>🤖 AI Career Consultant</h2>
        <p>Current User: <strong>{credentials.username}</strong></p>
        <textarea 
          value={profile} 
          onChange={(e) => setProfile(e.target.value)}
          placeholder="Paste your profile here for AI analysis..."
          style={{ width: "100%", height: "100px", borderRadius: "5px", border: "1px solid #ccc", padding: "10px" }}
        />
        <button onClick={handleAiConsult} style={{ marginTop: "10px", padding: "10px 20px", background: "#2c3e50", color: "white", borderRadius: "5px", cursor: "pointer" }}>
          Get Recommendations
        </button>
        <div style={{ marginTop: "20px", padding: "15px", background: "white", borderRadius: "8px", borderLeft: "5px solid #007bff", minHeight: "50px" }}>
          {aiResponse}
        </div>
      </section>
    </div>
  );
}

export default App;