import NavLink from "./NavLink.tsx";
import {IconCpu, IconHome, IconSettings, IconUser} from "@tabler/icons-react";

export default function Header() {

    return (
        <header className="navbar navbar-expand-md d-print-none">
            <div className="container-xl">
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-menu"
                        aria-controls="navbar-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <a href="." aria-label="SmartAC" className="navbar-brand navbar-brand-autodark me-3">
                    <span>SmartAC</span>
                </a>

                <div className="collapse navbar-collapse" id="navbar-menu">
                    <ul className="navbar-nav">
                        {/*TODO: Localization*/}
                        <NavLink icon={IconHome} title={"Home"} path="/"/>
                        <NavLink icon={IconUser} title={"Users"} path="/users"/>
                        <NavLink icon={IconCpu} title={"Devices"} path="/devices"/>
                        <NavLink icon={IconSettings} title={"Settings"} path="/settings"/>
                    </ul>
                </div>
            </div>
        </header>
    );
}
