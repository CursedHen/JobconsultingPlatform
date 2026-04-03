import { useState } from "react";
import ConsultantApproval from "./ConsultantApproval";
import SystemStatus from "./SystemStatus";
import PolicyManager from "./PolicyManager";

export default function AdminDashboard() {
  const [activeTab, setActiveTab] = useState("status");

  const activeStyle = { backgroundColor: "lightblue" };

  return (
    <div>
      <h1>Admin Dashboard</h1>

      <button
        onClick={() => setActiveTab("status")}
        style={activeTab === "status" ? activeStyle : {}}
      >
        System Status
      </button>

      <button
        onClick={() => setActiveTab("approvals")}
        style={activeTab === "approvals" ? activeStyle : {}}
      >
        Consultant Approvals
      </button>

      <button
        onClick={() => setActiveTab("policies")}
        style={activeTab === "policies" ? activeStyle : {}}
      >
        System Policies
      </button>

      <div style={{ marginTop: "20px" }}>
        {activeTab === "status" && <SystemStatus />}
        {activeTab === "approvals" && <ConsultantApproval />}
        {activeTab === "policies" && <PolicyManager />}
      </div>
    </div>
  );
}