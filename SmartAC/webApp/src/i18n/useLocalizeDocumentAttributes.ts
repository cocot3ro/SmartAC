import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { isRtl } from "src/i18n/config.tsx";

function useLocalizeDocumentAttributes() {
    const { t, i18n } = useTranslation();

    useEffect(() => {
        const language = i18n.resolvedLanguage;

        if (language) {
            // Set the <HTML lang> attribute.
            document.documentElement.lang = language;

            // Set the <HTML dir> attribute.
            document.documentElement.dir = i18n.dir(language);

            // Load the corresponding Tabler stylesheet.

            let cancelled = false;

            async function loadTablerStyles() {
                const cssUrl = isRtl()
                    ? (await import("@tabler/core/dist/css/tabler.rtl.min.css?url")).default
                    : (await import("@tabler/core/dist/css/tabler.min.css?url")).default;

                if (cancelled) return;

                let link = document.querySelector("link#tabler-styles") as HTMLLinkElement | null;

                if (!link) {
                    link = document.createElement("link");
                    link.id = "tabler-styles";
                    link.rel = "stylesheet";
                    document.head.appendChild(link);
                }

                link.href = cssUrl;
            }

            // noinspection JSIgnoredPromiseFromCall
            loadTablerStyles();

            return () => {
                cancelled = true;
            };
        }
    }, [ i18n.resolvedLanguage, i18n ]);

    useEffect(() => {
        document.title = t("app_title");
    }, [ t ]);

}

export { useLocalizeDocumentAttributes };