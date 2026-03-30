import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import api from "../api/axiosConfig";

export default function LoginPage() {
    const [form, setForm] = useState({email: "", password: ""});
    const navigate = useNavigate();

    const handleChange = (e) =>
        setForm({...form, [e.target.name]: e.target.value});

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const res = await api.post("/auth/login", form);
            localStorage.setItem("token", res.data.token);
            navigate("auth/hello")
        } catch (err) {
            if (err.response && err.response.status === 403) {
                alert("Incorrect email or password");
            } else {
                alert("Login failed. Try again")
            }
        }
    }

    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input name="email" placeholder="Email" onChange={handleChange}/>
                <input name="password" placeholder="Password" type="password" onChange={handleChange}/>
                <button type="submit">Login</button>
            </form>
            <p>Register! <button onClick={() => navigate("/auth/register")}>Register here</button></p>
        </div>
    );
}