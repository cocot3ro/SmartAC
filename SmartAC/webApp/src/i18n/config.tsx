import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import HttpApi from "i18next-http-backend";
import { initReactI18next, useTranslation } from "react-i18next";

interface Lang {
    code: string;
    locale: string;
    name: string;
}

const supportedLngs: Lang[] = [
    {
        code: "en",
        locale: "en-US",
        name: "English"
    },
    {
        code: "es",
        locale: "es-ES",
        name: "Español"
    }
];
const defaultLang: Lang = supportedLngs[0];

i18n
    .use(HttpApi)
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        detection: {
            order: [ "localStorage", "navigator" ],
            caches: [ "localStorage" ]
        },
        fallbackLng: defaultLang.code,
        supportedLngs: supportedLngs.map((item) => item.code),
        debug: true,
        interpolation: {
            escapeValue: false
        }
    });

const getFlagCodeForLocale = (locale?: string) => {
    const thisLocale = (locale || defaultLang.code).slice(0, 2);

    // only add to this if your flag is different from the locale code
    const specialCases: Record<string, string> = {
        ja: "jp", // Japan
        zh: "cn", // China
        vi: "vn", // Vietnam
        ko: "kr", // Korea
        cs: "cz", // Czechia
        ga: "ie", // Ireland (Irish)
        et: "ee", // Estonia (ISO 3166-1). "et" as a country code would be Ethiopia.
        uk: "ua", // Ukraine
        fa: "ir" // Iran (Persian)
    };

    if (specialCases[thisLocale]) {
        return specialCases[thisLocale].toUpperCase();
    }

    return thisLocale.toUpperCase();
};

const T = ({ id }: { id: string }) => {
    let { t } = useTranslation();

    return (
        <span data-translation-id={ id }>
			{ t(id) }
		</span>
    );
};

const isRtl = () => {
    return i18n.dir(i18n.resolvedLanguage) === "rtl";
};

export default i18n;
export { supportedLngs, defaultLang, getFlagCodeForLocale, isRtl, T };
