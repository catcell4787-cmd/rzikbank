// src/ProtectedRoute.js
import { Navigate, Outlet } from 'react-router-dom';

const ProtectedRoute = () => {
    // Проверяем наличие токена в localStorage
    const isAuthenticated = !!localStorage.getItem('authToken');

    // Если пользователь не авторизован, перенаправляем на /login
    // <Outlet /> — это "заполнитель", куда рендерится дочерний маршрут (например, Dashboard)
    return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default ProtectedRoute;