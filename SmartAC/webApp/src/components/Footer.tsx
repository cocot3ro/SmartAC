import { useCheckVersion, useHealth } from "src/hooks";
import { type VersionModel } from "webapp-shared";
import { T } from "src/i18n";
import { useTranslation } from "react-i18next";

export function Footer() {

    const health = useHealth();
    const {data: versionData} = useCheckVersion();
    const { t } = useTranslation();

    const formatVersion = (v: VersionModel) => {
        return `v${v.major}.${v.minor}.${v.patch}`;
    };

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

                            <li className={"list-inline-item"}>
                                Theme by <a href={"https://tabler.io/"} className={"link-secondary"}>Tabler</a>
                            </li>

                            <li className="list-inline-item">
                                <a
                                    href={`https://codeberg.org/cocot3ro/smartac/releases/tag/${getVersion()}`}
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
                                        title={t("update-available.title", {newVersion: formatVersion(versionData.latest)})}
                                    >
                                        <T id={"update-available"}
                                           data={{newVersion: formatVersion(versionData.latest)}}
                                        />
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
