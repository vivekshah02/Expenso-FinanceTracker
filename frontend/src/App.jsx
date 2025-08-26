import React, { useState } from 'react';
import Login from './components/Login';
import Register from './components/Register';
import Dashboard from './components/Dashboard';
import ForgotPassword from './components/ForgotPassword';
import Notification from './components/Notification';

function App() {
  const [currentView, setCurrentView] = useState('login');
  const [user, setUser] = useState(null);
  const [notification, setNotification] = useState(null);

  // Simple user session management
  const handleLogin = (userData) => {
    setUser(userData);
    setCurrentView('dashboard');
  };

  const handleLogout = () => {
    setUser(null);
    setCurrentView('login');
  };

  const showNotification = (message, type = 'info') => {
    setNotification({ message, type });
    setTimeout(() => setNotification(null), 5000);
  };

  const renderView = () => {
    switch (currentView) {
      case 'register':
        return <Register onSwitchToLogin={() => setCurrentView('login')} showNotification={showNotification} />;
      case 'forgot-password':
        return <ForgotPassword onSwitchToLogin={() => setCurrentView('login')} showNotification={showNotification} />;
      case 'dashboard':
        return <Dashboard user={user} onLogout={handleLogout} showNotification={showNotification} />;
      default:
        return (
          <Login
            onLogin={handleLogin}
            onSwitchToRegister={() => setCurrentView('register')}
            onSwitchToForgotPassword={() => setCurrentView('forgot-password')}
            showNotification={showNotification}
          />
        );
    }
  };

  return (
    <div className="min-h-screen bg-gray-50" style={{ backgroundColor: '#ADD8E6' }}>
      <div className="container mx-auto px-4">
        {renderView()}
      </div>
      {notification && (
        <Notification
          message={notification.message}
          type={notification.type}
          onClose={() => setNotification(null)}
        />
      )}
    </div>
  );
}

export default App;