import './App.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import ProtectedRoute from "./routes/ProtectedRoute";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="auth/login" element={<LoginPage />} />
              <Route path="auth/hello" element={<ProtectedRoute><MainPage/></ProtectedRoute>} />
              <Route path="*" element={<Navigate to="/login" />} />
          </Routes>
      </Router>
  );
}

export default App;
