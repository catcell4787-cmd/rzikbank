import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignupPage from "./pages/SignUpPage";
import Dashboard from "./pages/Dashboard";

function App() {
  return (
      <div className="App">
          <Router>
              <Routes>
                  <Route path="/" element={<LoginPage/>} />
                  <Route path="/auth/register" element={ <SignupPage/>} />
                  <Route path = "/auth/hello" element={<Dashboard/>}/>
              </Routes>
          </Router>
      </div>
  );
}

export default App;
