import "./App.css";
import {Routes, Route, NavLink} from "react-router-dom";

function Foo1() {
    return (
        <p>Users</p>
    )
}

function Foo2() {
    return (
        <p>Devices</p>
    )
}

export default function App() {
    return (
        <main className="main-content">
            <nav className="navbar">
                <NavLink to="/users">Users</NavLink>
                <NavLink to="/devices">Devices</NavLink>
            </nav>
            <Routes>
                <Route path="/users" element={<Foo1/>}/>
                <Route path="/devices" element={<Foo2/>}/>
            </Routes>
        </main>
    );
}
