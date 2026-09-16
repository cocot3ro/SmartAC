import React from 'react';
import ReactDOM from 'react-dom/client';
import {MemoryRouter} from "react-router-dom";
import App from "./App.tsx";

const rootElement = document.getElementById('root');
if (!rootElement) throw new Error('Failed to find the root element');

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <MemoryRouter initialEntries={["/users"]}>
            <App/>
        </MemoryRouter>
    </React.StrictMode>
);
