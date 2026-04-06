import React, { useState } from 'react';
import { Outlet, useNavigate, useLocation, Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useTheme } from '../contexts/ThemeContext';
import {
  LayoutDashboard,
  FileText,
  ClipboardList,
  BarChart3,
  LogOut,
  Shield,
  Menu,
  X
} from 'lucide-react';

const navItems = [
  { path: '/dashboard', label: 'Dashboard', icon: LayoutDashboard, roles: ['agent', 'customer'] },
  { path: '/dashboard/policies', label: 'Policies', icon: FileText, roles: ['agent', 'customer'] },
  { path: '/dashboard/claims', label: 'Claims', icon: ClipboardList, roles: ['agent', 'customer'] },
  { path: '/dashboard/analytics', label: 'Analytics', icon: BarChart3, roles: ['agent', 'customer'] },
];

export function DashboardLayout() {
  const { user, logout } = useAuth();
  const { theme } = useTheme();
  const navigate = useNavigate();
  const location = useLocation();
  const [sidebarOpen, setSidebarOpen] = useState(true);

  if (!user) return <Navigate to="/login" replace />;

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const getStyles = () => {
    if (theme === 'glassmorphism') {
      return {
        container: { background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)' },
        sidebar: { background: 'rgba(255, 255, 255, 0.1)', backdropFilter: 'blur(10px)', borderRight: '1px solid rgba(255, 255, 255, 0.2)' },
        content: { background: 'transparent' },
        card: { background: 'rgba(255, 255, 255, 0.1)', backdropFilter: 'blur(10px)', border: '1px solid rgba(255, 255, 255, 0.2)' },
        text: { primary: '#ffffff', secondary: 'rgba(255, 255, 255, 0.8)' }
      };
    } else if (theme === 'dark') {
      return {
        container: { background: '#0f172a' },
        sidebar: { background: '#1e293b', borderRight: '1px solid #334155' },
        content: { background: '#0f172a' },
        card: { background: '#1e293b', border: '1px solid #334155' },
        text: { primary: '#f1f5f9', secondary: '#cbd5e1' }
      };
    } else {
      return {
        container: { background: '#f8f9fa' },
        sidebar: { background: '#ffffff', borderRight: '1px solid #dee2e6' },
        content: { background: '#f8f9fa' },
        card: { background: '#ffffff', border: '1px solid #dee2e6' },
        text: { primary: '#212529', secondary: '#6c757d' }
      };
    }
  };

  const styles = getStyles();

  return (
    <div className="flex h-screen overflow-hidden" style={styles.container}>
      {/* Sidebar */}
      <div className={`${sidebarOpen ? 'w-64' : 'w-0'} transition-all duration-300 overflow-hidden`} style={styles.sidebar}>
        <div className="h-full flex flex-col">
          {/* Logo */}
          <div className="p-6 flex items-center justify-between">
            <div className="flex items-center">
              <Shield className="w-8 h-8 mr-2" style={{ color: theme === 'glassmorphism' ? '#ffffff' : '#0d6efd' }} />
              <h2 style={{ margin: 0, color: styles.text.primary }}>InsurePro</h2>
            </div>
          </div>

          {/* User Info */}
          <div className="px-6 py-4 mb-4" style={{ ...styles.card, margin: '0 16px', borderRadius: '12px' }}>
            <div style={{ color: styles.text.primary }}>{user.name}</div>
            <div className="capitalize" style={{ color: styles.text.secondary }}>{user.role}</div>
          </div>

          {/* Navigation */}
          <nav className="flex-1 px-4">
            {navItems
              .filter(item => item.roles.includes(user.role))
              .map(item => {
                const Icon = item.icon;
                const isActive = location.pathname === item.path || (item.path !== '/dashboard' && location.pathname.startsWith(item.path));
                return (
                  <button
                    key={item.path}
                    onClick={() => navigate(item.path)}
                    className="w-full flex items-center px-4 py-3 mb-2 rounded-lg transition-all"
                    style={{
                      background: isActive
                        ? theme === 'glassmorphism'
                          ? 'rgba(255, 255, 255, 0.2)'
                          : theme === 'dark'
                          ? '#334155'
                          : '#e9ecef'
                        : 'transparent',
                      color: styles.text.primary,
                      border: 'none',
                      cursor: 'pointer'
                    }}
                  >
                    <Icon className="w-5 h-5 mr-3" />
                    {item.label}
                  </button>
                );
              })}
          </nav>

          {/* Logout */}
          <div className="p-4">
            <button
              onClick={handleLogout}
              className="w-full flex items-center px-4 py-3 rounded-lg transition-all"
              style={{
                background: theme === 'glassmorphism'
                  ? 'rgba(255, 255, 255, 0.1)'
                  : theme === 'dark'
                  ? '#334155'
                  : '#e9ecef',
                color: styles.text.primary,
                border: 'none',
                cursor: 'pointer'
              }}
            >
              <LogOut className="w-5 h-5 mr-3" />
              Logout
            </button>
          </div>
        </div>
      </div>

      {/* Main Content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        {/* Header */}
        <header
          className="p-4 flex items-center justify-between"
          style={{ ...styles.card, borderRadius: 0, borderTop: 'none', borderLeft: 'none', borderRight: 'none' }}
        >
          <button
            onClick={() => setSidebarOpen(!sidebarOpen)}
            className="p-2 rounded-lg transition-all"
            style={{ background: 'transparent', border: 'none', cursor: 'pointer', color: styles.text.primary }}
          >
            {sidebarOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>

          <div style={{ color: styles.text.secondary }}>
            {new Date().toLocaleDateString('en-US', {
              weekday: 'long',
              year: 'numeric',
              month: 'long',
              day: 'numeric'
            })}
          </div>
        </header>

        {/* Content */}
        <main className="flex-1 overflow-auto p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
export default DashboardLayout;