import {LocalePicker, NavLink, ThemeSwitcher} from "src/components";
import {IconCpu, IconHome, IconSettings, IconUser} from "@tabler/icons-react";
import {useTranslation} from "react-i18next";

export function Header() {

    const {t} = useTranslation();

    return (
        <header className="navbar navbar-expand-md d-print-none">
            <div className="container-xl">
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-menu"
                        aria-controls="navbar-menu" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>

                <a href="/" aria-label={t("app_title")} className="navbar-brand navbar-brand-autodark me-3">
                    <span>{t("app_title")}</span>
                </a>

                <div className="collapse navbar-collapse" id="navbar-menu">
                    <ul className="navbar-nav">
                        {/*TODO: Localization*/}
                        <NavLink icon={IconHome} title={"Index"} path="/"/>
                        <NavLink icon={IconUser} title={"Users"} path="/users"/>
                        <NavLink icon={IconCpu} title={"Devices"} path="/devices"/>
                        <NavLink icon={IconSettings} title={"Settings"} path="/settings"/>
                    </ul>
                </div>

                <div className={"navbar-nav flex-row order-md-last"}>
                    <div className="d-none d-md-flex">
                        <div className="nav-item">
                            <LocalePicker/>
                        </div>
                        <div className="nav-item">
                            <ThemeSwitcher/>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    );
}
