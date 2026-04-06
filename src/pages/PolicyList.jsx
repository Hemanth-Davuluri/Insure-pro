import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useTheme } from '../contexts/ThemeContext';
import { mockPolicies } from '../data/mockData';
import { Search, Filter, Plus, Eye } from 'lucide-react';

export function PolicyList() {
  const { user } = useAuth();
  const { theme } = useTheme();
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [statusFilter, setStatusFilter] = useState('ALL');

  const getStyles = () => {
    if (theme === 'glassmorphism') {
      return {
        card: { background: 'rgba(255,255,255,0.1)', backdropFilter: 'blur(10px)', border: '1px solid rgba(255,255,255,0.2)', boxShadow: '0 8px 32px 0 rgba(31,38,135,0.37)' },
        text: { primary: '#ffffff', secondary: 'rgba(255,255,255,0.8)' },
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

  const filteredPolicies = mockPolicies.filter((policy) => {
    const matchesSearch =
      policy.policyNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
      policy.customerName.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesStatus = statusFilter === 'ALL' || policy.status === statusFilter;
    return matchesSearch && matchesStatus;
  });

  return (
    <div>
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 style={{ margin: 0, marginBottom: 8, color: styles.text.primary }}>Insurance Policies</h1>
          <p style={{ margin: 0, color: styles.text.secondary }}>
            {user.role === 'agent' ? 'Manage all insurance policies' : 'Your insurance policies'}
          </p>
        </div>
        {user.role === 'agent' && (
          <button
            onClick={() => navigate('/dashboard/policies/create')}
            className="flex items-center gap-2 px-4 py-2 rounded-lg transition-all"
            style={{ background: '#0d6efd', color: '#fff', border: 'none', cursor: 'pointer' }}
          >
            <Plus className="w-5 h-5" /> Create Policy
          </button>
        )}
      </div>

      {/* Filters */}
      <div className="mb-6 p-4 rounded-xl" style={styles.card}>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5" style={{ color: styles.text.secondary }} />
            <input
              type="text"
              placeholder="Search by policy number or customer..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2 rounded-lg outline-none"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255,255,255,0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255,255,255,0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: styles.text.primary,
              }}
            />
          </div>
          <div className="relative">
            <Filter className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5" style={{ color: styles.text.secondary }} />
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              className="w-full pl-10 pr-4 py-2 rounded-lg outline-none"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255,255,255,0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255,255,255,0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: styles.text.primary,
              }}
            >
              <option value="ALL">All Statuses</option>
              <option value="ACTIVE">Active</option>
              <option value="DRAFT">Draft</option>
              <option value="CANCELLED">Cancelled</option>
            </select>
          </div>
        </div>
      </div>

      {/* Policies Table */}
      <div className="rounded-xl overflow-hidden" style={styles.card}>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr style={{ borderBottom: `1px solid ${theme === 'glassmorphism' ? 'rgba(255,255,255,0.2)' : theme === 'dark' ? '#334155' : '#dee2e6'}` }}>
                {['Policy Number', 'Customer', 'Type', 'Status', 'Premium', 'Start Date', 'Actions'].map((header) => (
                  <th key={header} className="text-left p-4" style={{ color: styles.text.secondary }}>{header}</th>
                ))}
              </tr>
            </thead>
            <tbody>
              {filteredPolicies.map((policy) => (
                <tr key={policy.id} style={{ borderBottom: `1px solid ${theme === 'glassmorphism' ? 'rgba(255,255,255,0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa'}` }}>
                  <td className="p-4" style={{ color: styles.text.primary }}>{policy.policyNumber}</td>
                  <td className="p-4" style={{ color: styles.text.primary }}>{policy.customerName}</td>
                  <td className="p-4" style={{ color: styles.text.primary }}>{policy.coverageType}</td>
                  <td className="p-4">
                    <span className="px-3 py-1 rounded-full inline-block" style={{ background: `${getStatusColor(policy.status)}20`, color: getStatusColor(policy.status) }}>
                      {policy.status}
                    </span>
                  </td>
                  <td className="p-4" style={{ color: styles.text.primary }}>${policy.finalPremium.toLocaleString()}</td>
                  <td className="p-4" style={{ color: styles.text.primary }}>{new Date(policy.startDate).toLocaleDateString()}</td>
                  <td className="p-4">
                    <button
                      onClick={() => navigate(`/dashboard/policies/${policy.id}`)}
                      className="flex items-center gap-1 px-3 py-1 rounded-lg transition-all"
                      style={{
                        background: theme === 'glassmorphism' ? 'rgba(255,255,255,0.1)' : theme === 'dark' ? '#334155' : '#e9ecef',
                        color: styles.text.primary,
                        border: 'none',
                        cursor: 'pointer',
                      }}
                    >
                      <Eye className="w-4 h-4" /> View
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {filteredPolicies.length === 0 && (
        <div className="text-center py-12" style={{ color: styles.text.secondary }}>
          No policies found matching your criteria
        </div>
      )}
    </div>
  );
}
export default PolicyList;