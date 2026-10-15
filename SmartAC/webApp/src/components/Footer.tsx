import { useCheckVersion, useHealth } from "src/hooks";
// import { useTranslation } from "react-i18next";
import { type VersionModel } from "webapp-shared";

export function Footer() {

    // const { t } = useTranslation();

    const health = useHealth();
    const { data: versionData } = useCheckVersion();

    const formatVersion = (v: VersionModel)=> {
        return `v${v.major}.${v.minor}.${v.patch}`;
    }

    const getVersion = () => {
        if (!health.data) {
            return "";
        }

        const v = health.data.version;
        return formatVersion(v);
    };

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
                                <a href="https://codeberg.org/cocot3ro/SmartAC" className="link-secondary">
                                    <span>Source code</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div className="col-12 col-lg-auto mt-3 mt-lg-0">
                        <ul className="list-inline list-inline-dots mb-0">
                            <li className="list-inline-item">
                                Copyright © 2026 <a href="#" className="link-secondary">cocot3ro</a>. All rights
                                reserved.
                            </li>

                            <li className={ "list-inline-item" }>
                                Theme by <a href={ "https://tabler.io/" } className={ "link-secondary" }>Tabler</a>
                            </li>

                            <li className="list-inline-item">
                                <a
                                    href={`https://github.com/NginxProxyManager/nginx-proxy-manager/releases/tag/${getVersion()}`}
                                    className="link-secondary"
                                    target="_blank"
                                    rel="noopener"
                                >
                                    {" "}
                                    {getVersion()}{" "}
                                </a>
                            </li>

                            {versionData?.updateAvailable && versionData?.latest && (
                                <li className="list-inline-item">
                                    <a
                                        href={`https://codeberg.org/cocot3ro/smartac/releases/tag/${versionData.latest}`}
                                        className="link-warning fw-bold"
                                        target="_blank"
                                        rel="noopener"
                                        title={`New version ${versionData.latest} is available`}
                                    >
                                        Update available: {formatVersion(versionData.latest)}
                                        {/*{t("update-available", { latestVersion: checkVersion.data.latest })}*/}
                                    </a>
                                </li>
                            )}
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    );
}
