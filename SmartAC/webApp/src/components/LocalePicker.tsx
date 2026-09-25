import cn from "classnames";
import {Flag} from "src/components";
import {useTheme} from "src/hooks";
import {getFlagCodeForLocale, supportedLngs} from "src/i18n";
import styles from "src/components/LocalePicker.module.css";
import {useTranslation} from "react-i18next";

interface Props {
    menuAlign?: "start" | "end";
}

function LocalePicker({menuAlign = "start"}: Props) {
    // const { locale, setLocale } = useLocaleState();
    const {getTheme} = useTheme();

    const {i18n} = useTranslation();

    const classes = ["btn", "dropdown-toggle", "btn-sm", styles.btn];
    const cns = cn(...classes, getTheme() === "dark" ? "btn-ghost-dark" : "btn-ghost-light");

    return (
        <div className="dropdown">
            <button type="button" className={cns} data-bs-toggle="dropdown">
                <Flag countryCode={getFlagCodeForLocale(i18n.resolvedLanguage)}/>
            </button>
            <div
                className={cn("dropdown-menu", {
                    "dropdown-menu-end": menuAlign === "end",
                })}
            >
                {supportedLngs.map((item) => (
                    <a
                        className="dropdown-item"
                        key={item.code}
                        onClick={(e) => {
                            e.preventDefault();
                            i18n.changeLanguage(item.code);
                        }}
                    >
                        <Flag countryCode={getFlagCodeForLocale(item.code)}/> <span>{item.name}</span>
                    </a>
                ))}
            </div>
        </div>
    );
}

export {LocalePicker};
