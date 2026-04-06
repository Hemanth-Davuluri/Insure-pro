import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useTheme } from '../contexts/ThemeContext';
import { Shield, UserPlus } from 'lucide-react';

export function Register() {
  const navigate = useNavigate();
  const { register } = useAuth();
  const { theme, setTheme } = useTheme();
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('customer');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setLoading(true);
    try {
      await register(name, email, password, role);
      navigate('/dashboard');
    } catch (error) {
      setError('Registration failed');
    } finally {
      setLoading(false);
    }
  };

  const themes = ['minimal', 'glassmorphism', 'dark'];
  const roles = ['agent', 'customer'];

  return (
    <div
      className="min-h-screen flex items-center justify-center p-6"
      style={{
        background:
          theme === 'glassmorphism'
            ? 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'
            : theme === 'dark'
            ? '#0f172a'
            : '#f8f9fa',
      }}
    >
      <div
        className="w-full max-w-md p-8 rounded-2xl"
        style={{
          background:
            theme === 'glassmorphism'
              ? 'rgba(255, 255, 255, 0.1)'
              : theme === 'dark'
              ? '#1e293b'
              : '#ffffff',
          backdropFilter: theme === 'glassmorphism' ? 'blur(10px)' : 'none',
          boxShadow:
            theme === 'glassmorphism'
              ? '0 8px 32px 0 rgba(31, 38, 135, 0.37)'
              : theme === 'dark'
              ? '0 10px 25px rgba(0, 0, 0, 0.5)'
              : '0 10px 25px rgba(0, 0, 0, 0.1)',
          border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : 'none',
          color: theme === 'dark' || theme === 'glassmorphism' ? '#f1f5f9' : '#212529',
        }}
      >
        <div className="flex items-center justify-center mb-8">
          <Shield
            className="w-12 h-12 mr-3"
            style={{ color: theme === 'glassmorphism' ? '#ffffff' : '#0d6efd' }}
          />
          <h1 style={{ margin: 0, color: theme === 'dark' || theme === 'glassmorphism' ? '#ffffff' : '#212529' }}>
            InsurePro
          </h1>
        </div>

        <div className="mb-6 flex gap-2 justify-center">
          {themes.map((t) => (
            <button
              key={t}
              onClick={() => setTheme(t)}
              className="px-3 py-1 rounded-lg transition-all capitalize"
              style={{
                background:
                  theme === t
                    ? theme === 'glassmorphism'
                      ? 'rgba(255, 255, 255, 0.3)'
                      : '#0d6efd'
                    : theme === 'glassmorphism'
                    ? 'rgba(255, 255, 255, 0.1)'
                    : theme === 'dark'
                    ? '#334155'
                    : '#e9ecef',
                color:
                  theme === t ? '#ffffff' : theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#6c757d',
                border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : 'none',
              }}
            >
              {t}
            </button>
          ))}
        </div>

        {error && (
          <div
            className="mb-4 p-3 rounded-lg"
            style={{
              background: 'rgba(220, 53, 69, 0.1)',
              border: '1px solid #dc3545',
              color: '#dc3545',
            }}
          >
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block mb-2" style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}>
              Full Name
            </label>
            <input
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
              className="w-full px-4 py-2 rounded-lg border outline-none transition-all"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255, 255, 255, 0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: theme === 'dark' || theme === 'glassmorphism' ? '#f1f5f9' : '#212529',
              }}
              placeholder="John Doe"
            />
          </div>

          <div>
            <label className="block mb-2" style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}>
              Email
            </label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full px-4 py-2 rounded-lg border outline-none transition-all"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255, 255, 255, 0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: theme === 'dark' || theme === 'glassmorphism' ? '#f1f5f9' : '#212529',
              }}
              placeholder="john@example.com"
            />
          </div>

          <div>
            <label className="block mb-2" style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}>
              Password
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="w-full px-4 py-2 rounded-lg border outline-none transition-all"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255, 255, 255, 0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: theme === 'dark' || theme === 'glassmorphism' ? '#f1f5f9' : '#212529',
              }}
              placeholder="••••••••"
            />
          </div>

          <div>
            <label className="block mb-2" style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}>
              Confirm Password
            </label>
            <input
              type="password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              className="w-full px-4 py-2 rounded-lg border outline-none transition-all"
              style={{
                background: theme === 'glassmorphism' ? 'rgba(255, 255, 255, 0.1)' : theme === 'dark' ? '#334155' : '#f8f9fa',
                border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.2)' : theme === 'dark' ? '1px solid #475569' : '1px solid #dee2e6',
                color: theme === 'dark' || theme === 'glassmorphism' ? '#f1f5f9' : '#212529',
              }}
              placeholder="••••••••"
            />
          </div>

          <div>
            <label className="block mb-2" style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}>
              Role
            </label>
            <div className="flex gap-4">
              {roles.map((r) => (
                <label key={r} className="flex items-center cursor-pointer">
                  <input
                    type="radio"
                    name="role"
                    value={r}
                    checked={role === r}
                    onChange={(e) => setRole(e.target.value)}
                    className="mr-2"
                  />
                  <span
                    className="capitalize"
                    style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#cbd5e1' : '#495057' }}
                  >
                    {r}
                  </span>
                </label>
              ))}
            </div>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 rounded-lg flex items-center justify-center gap-2 transition-all"
            style={{
              background: theme === 'glassmorphism' ? 'rgba(255, 255, 255, 0.2)' : '#0d6efd',
              color: '#ffffff',
              border: theme === 'glassmorphism' ? '1px solid rgba(255, 255, 255, 0.3)' : 'none',
              opacity: loading ? 0.7 : 1,
              cursor: loading ? 'not-allowed' : 'pointer',
            }}
          >
            <UserPlus className="w-5 h-5" />
            {loading ? 'Creating Account...' : 'Create Account'}
          </button>
        </form>

        <div className="mt-6 text-center">
          <span style={{ color: theme === 'dark' || theme === 'glassmorphism' ? '#94a3b8' : '#6c757d' }}>
            Already have an account?{' '}
          </span>
          <Link
            to="/login"
            style={{
              color: theme === 'glassmorphism' ? '#ffffff' : '#0d6efd',
              textDecoration: 'none',
            }}
          >
            Sign In
          </Link>
        </div>
      </div>
    </div>
  );
}
export default Register;