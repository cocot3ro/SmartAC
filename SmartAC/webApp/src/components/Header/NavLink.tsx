import React from "react";

interface NavLinkProps {
    icon: React.ComponentType;
    title: string;
    onClick: () => void;
}

export default function NavLink({icon, title, onClick}: NavLinkProps) {
    return (
        <li className="nav-item active">
            <a className="nav-link" onClick={onClick}>
                <span className="nav-link-icon">
                    {React.createElement(icon)}
                </span>
                <span className="nav-link-title">{title}</span>
            </a>
        </li>
    );
}
