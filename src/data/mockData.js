// Policy Status
export const PolicyStatus = {
  DRAFT: "DRAFT",
  ACTIVE: "ACTIVE",
  CANCELLED: "CANCELLED",
};

// Claim Status
export const ClaimStatus = {
  PENDING: "PENDING",
  APPROVED: "APPROVED",
  REJECTED: "REJECTED",
  HIGH_RISK: "HIGH_RISK",
};

// Coverage Type
export const CoverageType = {
  Home: "Home",
  Auto: "Auto",
  Health: "Health",
};

// Mock Policies Data
export const mockPolicies = [
  {
    id: "POL-001",
    policyNumber: "HP-2026-001234",
    customerName: "John Smith",
    coverageType: "Home",
    status: "ACTIVE",
    startDate: "2026-01-15",
    endDate: "2027-01-15",
    basePremium: 1200,
    tax: 180,
    finalPremium: 1380,
    details: {
      address: "123 Main Street, New York, NY",
      propertyValue: 500000,
      coverageAmount: 500000,
    },
  },
  {
    id: "POL-002",
    policyNumber: "AP-2026-005678",
    customerName: "Sarah Johnson",
    coverageType: "Auto",
    status: "ACTIVE",
    startDate: "2026-02-01",
    endDate: "2027-02-01",
    basePremium: 800,
    tax: 120,
    finalPremium: 920,
    details: {
      vehicleMake: "Toyota",
      vehicleModel: "Camry",
      vehicleYear: 2024,
      coverageAmount: 50000,
    },
  },
  {
    id: "POL-003",
    policyNumber: "HP-2026-009012",
    customerName: "Michael Brown",
    coverageType: "Health",
    status: "ACTIVE",
    startDate: "2026-03-10",
    endDate: "2027-03-10",
    basePremium: 2400,
    tax: 360,
    finalPremium: 2760,
    details: {
      age: 35,
      medicalHistory: "No pre-existing conditions",
      coverageAmount: 1000000,
    },
  },
];

// Mock Claims Data
export const mockClaims = [
  {
    id: "CLM-001",
    claimNumber: "C-2026-001",
    policyId: "POL-001",
    policyNumber: "HP-2026-001234",
    customerName: "John Smith",
    description: "Water damage from burst pipe in basement",
    amount: 15000,
    status: "APPROVED",
    submittedDate: "2026-03-15",
    processedDate: "2026-03-20",
    photos: ["photo1.jpg", "photo2.jpg"],
  },
  {
    id: "CLM-002",
    claimNumber: "C-2026-002",
    policyId: "POL-002",
    policyNumber: "AP-2026-005678",
    customerName: "Sarah Johnson",
    description: "Vehicle collision on highway",
    amount: 8500,
    status: "PENDING",
    submittedDate: "2026-04-01",
    photos: ["photo3.jpg"],
  },
];

// Dashboard Stats Function
export const getDashboardStats = (role, userId) => {
  if (role === "agent") {
    return {
      totalPolicies: mockPolicies.length,
      activePolicies: mockPolicies.filter((p) => p.status === "ACTIVE").length,
      totalClaims: mockClaims.length,
      pendingClaims: mockClaims.filter((c) => c.status === "PENDING").length,
      totalPremium: mockPolicies.reduce(
        (sum, p) => sum + p.finalPremium,
        0
      ),
      claimsPaid: mockClaims
        .filter((c) => c.status === "APPROVED")
        .reduce((sum, c) => sum + c.amount, 0),
    };
  } else {
    return {
      totalPolicies: 2,
      activePolicies: 1,
      totalClaims: 3,
      pendingClaims: 1,
      totalPremium: 3000,
      claimsPaid: 15000,
    };
  }
};

// Analytics Data
export const getAnalyticsData = () => {
  return {
    policyTrends: [
      { month: "Jan", home: 45, auto: 32, health: 28 },
      { month: "Feb", home: 52, auto: 38, health: 34 },
    ],
    claimRates: [
      { month: "Jan", submitted: 24, approved: 18, rejected: 4 },
      { month: "Feb", submitted: 28, approved: 22, rejected: 5 },
    ],
    premiumAnalytics: [
      { type: "Home", value: 45, color: "#0d6efd" },
      { type: "Auto", value: 30, color: "#28a745" },
      { type: "Health", value: 25, color: "#ffc107" },
    ],
  };
};