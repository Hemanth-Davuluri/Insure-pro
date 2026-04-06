import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useTheme } from '../contexts/ThemeContext';
import { mockPolicies } from '../data/mockData';
import { ArrowLeft, Download, Calendar, DollarSign, FileText, User } from 'lucide-react';

export function PolicyDetail() {
  const { id } = useParams();
  const { theme } = useTheme();
  const navigate = useNavigate();

  const policy = mockPolicies.find((p) => p.id === id);

  if (!policy) {
    return (
      <div className="text-center py-12">
        <h2>Policy not found</h2>
      </div>
    );
  }

  const getStyles = () => {
    if (theme === 'glassmorphism') {
      return {
        card: {
          background: 'rgba(255, 255, 255, 0.1)',
          backdropFilter: 'blur(10px)',
          border: '1px solid rgba(255, 255, 255, 0.2)',
          boxShadow: '0 8px 32px 0 rgba(31, 38, 135, 0.37)',
        },
        text: { primary: '#ffffff', secondary: 'rgba(255, 255, 255, 0.8)' },
      };
    } else if (theme === 'dark') {
      return {
        card: { background: '#1e293b', border: '1px solid #334155', boxShadow: '0 1px 3px rgba(0,0,0,0.3)' },
        text: { primary: '#f1f5f9', secondary: '#cbd5e1' },
      };
    } else {
      return {
        card: { background: '#ffffff', border: '1px solid #dee2e6', boxShadow: '0 1px 3px rgba(0,0,0,0.1)' },
        text: { primary: '#212529', secondary: '#6c757d' },
      };
    }
  };

  const styles = getStyles();

  const getStatusColor = (status) => {
    switch (status) {
      case 'ACTIVE': return '#28a745';
      case 'DRAFT': return '#ffc107';
      case 'CANCELLED': return '#dc3545';
      default: return '#6c757d';
    }
  };

  const handleDownloadPDF = () => alert('Downloading policy PDF...');

  return (
    <div>
      {/* Header */}
      <div className="flex items-center gap-4 mb-6">
        <button
          onClick={() => navigate('/dashboard/policies')}
          className="p-2 rounded-lg"
          style={{
            background: theme === 'glassmorphism'
              ? 'rgba(255, 255, 255, 0.1)'
              : theme === 'dark'
              ? '#334155'
              : '#e9ecef',
            border: 'none',
            cursor: 'pointer',
            color: styles.text.primary,
          }}
        >
          <ArrowLeft className="w-5 h-5" />
        </button>
        <div className="flex-1">
          <h1 style={{ margin: 0, marginBottom: 8, color: styles.text.primary }}>Policy Details</h1>
          <p style={{ margin: 0, color: styles.text.secondary }}>{policy.policyNumber}</p>
        </div>
        <button
          onClick={handleDownloadPDF}
          className="flex items-center gap-2 px-4 py-2 rounded-lg transition-all"
          style={{ background: '#0d6efd', color: '#fff', border: 'none', cursor: 'pointer' }}
        >
          <Download className="w-5 h-5" />
          Download PDF
        </button>
      </div>

      {/* Content Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Main Info */}
        <div className="lg:col-span-2 space-y-6">
          {/* Status */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center justify-between mb-4">
              <h3 style={{ margin: 0, color: styles.text.primary }}>Policy Status</h3>
              <span
                className="px-4 py-2 rounded-full"
                style={{
                  background: `${getStatusColor(policy.status)}20`,
                  color: getStatusColor(policy.status),
                }}
              >
                {policy.status}
              </span>
            </div>
          </div>

          {/* Customer Info */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-4">
              <User className="w-5 h-5" style={{ color: '#0d6efd' }} />
              <h3 style={{ margin: 0, color: styles.text.primary }}>Customer Information</h3>
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Name</div>
                <div style={{ color: styles.text.primary }}>{policy.customerName}</div>
              </div>
              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Policy Number</div>
                <div style={{ color: styles.text.primary }}>{policy.policyNumber}</div>
              </div>
            </div>
          </div>

          {/* Coverage Details */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-4">
              <FileText className="w-5 h-5" style={{ color: '#0d6efd' }} />
              <h3 style={{ margin: 0, color: styles.text.primary }}>Coverage Details</h3>
            </div>
            <div className="space-y-3">
              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Coverage Type</div>
                <div style={{ color: styles.text.primary }}>{policy.coverageType} Insurance</div>
              </div>

              {policy.coverageType === 'Home' && (
                <>
                  <div>
                    <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Property Address</div>
                    <div style={{ color: styles.text.primary }}>{policy.details.address}</div>
                  </div>
                  <div>
                    <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Property Value</div>
                    <div style={{ color: styles.text.primary }}>${policy.details.propertyValue?.toLocaleString()}</div>
                  </div>
                </>
              )}

              {policy.coverageType === 'Auto' && (
                <div>
                  <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Vehicle</div>
                  <div style={{ color: styles.text.primary }}>
                    {policy.details.vehicleYear} {policy.details.vehicleMake} {policy.details.vehicleModel}
                  </div>
                </div>
              )}

              {policy.coverageType === 'Health' && (
                <>
                  <div>
                    <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Age</div>
                    <div style={{ color: styles.text.primary }}>{policy.details.age} years</div>
                  </div>
                  <div>
                    <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Medical History</div>
                    <div style={{ color: styles.text.primary }}>{policy.details.medicalHistory}</div>
                  </div>
                </>
              )}

              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Coverage Amount</div>
                <div style={{ color: styles.text.primary }}>${policy.details.coverageAmount?.toLocaleString()}</div>
              </div>
            </div>
          </div>
        </div>

        {/* Sidebar */}
        <div className="lg:col-span-1 space-y-6">
          {/* Policy Period */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-4">
              <Calendar className="w-5 h-5" style={{ color: '#0d6efd' }} />
              <h3 style={{ margin: 0, color: styles.text.primary }}>Policy Period</h3>
            </div>
            <div className="space-y-3">
              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>Start Date</div>
                <div style={{ color: styles.text.primary }}>
                  {new Date(policy.startDate).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })}
                </div>
              </div>
              <div>
                <div style={{ color: styles.text.secondary, marginBottom: 4 }}>End Date</div>
                <div style={{ color: styles.text.primary }}>
                  {new Date(policy.endDate).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })}
                </div>
              </div>
            </div>
          </div>

          {/* Premium */}
          <div className="p-6 rounded-xl" style={styles.card}>
            <div className="flex items-center gap-3 mb-4">
              <DollarSign className="w-5 h-5" style={{ color: '#0d6efd' }} />
              <h3 style={{ margin: 0, color: styles.text.primary }}>Premium</h3>
            </div>
            <div className="space-y-3">
              <div className="flex justify-between">
                <span style={{ color: styles.text.secondary }}>Base Premium</span>
                <span style={{ color: styles.text.primary }}>${policy.basePremium.toLocaleString()}</span>
              </div>
              <div className="flex justify-between">
                <span style={{ color: styles.text.secondary }}>Tax</span>
                <span style={{ color: styles.text.primary }}>${policy.tax.toLocaleString()}</span>
              </div>
              <div
                className="pt-3 mt-3 flex justify-between"
                style={{ borderTop: `1px solid ${theme === 'glassmorphism' ? 'rgba(255,255,255,0.2)' : theme === 'dark' ? '#334155' : '#dee2e6'}` }}
              >
                <span style={{ color: styles.text.primary }}>Final Premium</span>
                <span style={{ color: '#0d6efd' }}>${policy.finalPremium.toLocaleString()}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default PolicyDetail;