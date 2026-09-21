import React from "react";
import {useLocation, useNavigate} from "react-router-dom";

interface NavLinkProps {
    icon: React.ComponentType;
    title: string;
    path: string;
}

export function NavLink({icon, title, path}: NavLinkProps) {

    let navigate = useNavigate();
    let location = useLocation()

    return (
        <li className={`nav-item ${location.pathname === path ? "active" : ""}`}>
            <a className="nav-link" onClick={() => navigate(path)}>
                <span className="nav-link-icon">
                    {React.createElement(icon)}
                </span>
                <span className="nav-link-title">{title}</span>
            </a>
        </li>
    );
}
