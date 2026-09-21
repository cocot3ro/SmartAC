import { useTranslation } from "react-i18next";
import { supportedLngs } from "src/i18n/config.ts";
import {LangIcon} from "src/components";

export function LocaleSwitcher() {
    const { i18n } = useTranslation();

    return (
        <div className="flex items-center">
            <div className="locale-switcher">
                <LangIcon />

                <select
                    value={i18n.resolvedLanguage}
                    onChange={(e) => i18n.changeLanguage(e.target.value)}
                >
                    {Object.entries(supportedLngs).map(([code, name]) => (
                        <option value={code} key={code}>
                            {name}
                        </option>
                    ))}
                </select>
            </div>
        </div>
    );
}