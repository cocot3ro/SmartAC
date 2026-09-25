import {useEffect} from "react";
import {useTranslation} from "react-i18next";

function useLocalizeDocumentAttributes() {
    const {t, i18n} = useTranslation();

    useEffect(() => {
        if (i18n.resolvedLanguage) {

            // Set the <HTML lang> attribute.
            document.documentElement.lang = i18n.resolvedLanguage;

            // Set the <HTML dir> attribute.
            document.documentElement.dir = i18n.dir(i18n.resolvedLanguage);

            if (i18n.dir(i18n.resolvedLanguage) === "rtl") {
                import("@tabler/core/dist/css/tabler.rtl.min.css");
            } else {
                import("@tabler/core/dist/css/tabler.min.css");
            }
        }

        document.title = t("app_title");
    }, [i18n, i18n.resolvedLanguage, t]);
}

export {useLocalizeDocumentAttributes};