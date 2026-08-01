import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import SignupPage from "./pages/SignUpPage";
import Dashboard from "./pages/Dashboard";
import LoginPage1 from "./pages/LoginPage1";
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  return (
      <div className="App">
          <Router>
              <Routes>
                  <Route path="/" element={<LoginPage1/>} />
                  <Route path="/auth/register" element={ <SignupPage/>} />
                  <Route path = "/auth/hello" element={<Dashboard/>}/>
              </Routes>
          </Router>
      </div>
  );
}

export default App;
