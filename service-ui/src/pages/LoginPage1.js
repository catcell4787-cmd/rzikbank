import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import api from "../api/axiosConfig";
import {Button, Container, Form} from "react-bootstrap";

export default function LoginPage() {
    const [form, setForm] = useState({email: "", password: ""});
    const navigate = useNavigate();
    const [error, setError] = useState('');

    const handleChange = (e) =>
        setForm({...form, [e.target.name]: e.target.value});

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post("/auth/login", form);
            localStorage.setItem("token", response.data.token);
            console.log('Login successful:', response.data);
            navigate("auth/hello")
        } catch (err) {
            console.error('Login failed:', error.response ? error.response.data : error.message);
            setError('Login failed')
        }
    }

    return (
        <Container className="w-25">
        <Form>
            <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Text>Email address</Form.Text>
                <Form.Control type="email" placeholder="Enter email" onChange={(e) => handleChange}/>
                <Form.Text className="text-muted">
                    We'll never share your email with anyone else.
                </Form.Text>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Text>Password</Form.Text>
                <Form.Control type="password" placeholder="Password" onChange={(e) => handleChange}/>
            </Form.Group>
            <Button variant="primary" type="submit" onClick={handleSubmit}>
                Submit
            </Button>
        </Form>
        </Container>
    );
}