import i18n from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import HttpApi from "i18next-http-backend";
import {initReactI18next} from "react-i18next";

export const supportedLngs = {
    en: "English",
    es: "Español",
};

i18n
    .use(HttpApi)
    .use(LanguageDetector)
    .use(initReactI18next)
    .init({
        detection: {
            order: ['querystring', 'navigator'],
            lookupQuerystring: 'lng'
        },
        fallbackLng: "en",
        supportedLngs: Object.keys(supportedLngs),
        debug: true,
        interpolation: {
            escapeValue: false,
        },
    });

export default i18n;
