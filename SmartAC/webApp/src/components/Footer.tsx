import packageJson from '../../package.json';
import {IconGitHub} from '@tabler/icons-react';

export default function Footer() {
    return (
        <footer className="footer footer-transparent d-print-none">
            <div className="container-xl">
                <div className="row text-center align-items-center flex-row-reverse">
                    <div className="col-lg-auto ms-lg-auto">
                        <ul className="list-inline list-inline-dots mb-0">
                            <li className="list-inline-item">
                                <a href="#" className="link-secondary">Documentation</a>
                            </li>
                            <li className="list-inline-item">
                                <a href="#" className="link-secondary">License</a>
                            </li>
                            <li className="list-inline-item">
                                <a href="#" className="link-secondary">Source code</a>
                            </li>
                        </ul>
                    </div>
                    <div className="col-12 col-lg-auto mt-3 mt-lg-0">
                        <ul className="list-inline list-inline-dots mb-0">
                            <li className="list-inline-item">Copyright © 2026 <a href="#" className="link-secondary">Your
                                company</a> . All rights reserved.
                            </li>
                            <li className="list-inline-item">
                                <a href="#" className="link-secondary">v1.0.0</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    );
}
