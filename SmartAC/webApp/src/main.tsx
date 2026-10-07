import React from 'react';
import ReactDOM from 'react-dom/client';
import App from "./App.tsx";
import "./App.css";
import '@tabler/core/dist/js/tabler.min.js';
import "@tabler/core/dist/css/tabler.min.css";

const rootElement = document.getElementById('root');
if (!rootElement) throw new Error('Failed to find the root element');

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <App/>
    </React.StrictMode>
);
