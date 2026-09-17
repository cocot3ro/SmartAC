import React from 'react';
import ReactDOM from 'react-dom/client';
import {MemoryRouter} from "react-router-dom";
import App from "./App.tsx";
import './i18n/config.ts';
import '@tabler/core/dist/css/tabler.min.css';
import '@tabler/core/dist/js/tabler.min.js';

const rootElement = document.getElementById('root');
if (!rootElement) throw new Error('Failed to find the root element');

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <React.Suspense fallback={<div>Loading...</div>}>
            <MemoryRouter initialEntries={["/users"]}>
                <App/>
            </MemoryRouter>
        </React.Suspense>
    </React.StrictMode>
);
