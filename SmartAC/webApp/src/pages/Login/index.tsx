import { Field, Form, Formik } from "formik";
import { useEffect, useRef, useState } from "react";
import Alert from "react-bootstrap/Alert";
import { Button, LocalePicker, Page, ThemeSwitcher } from "src/components";
import { useAuthState } from "src/context";
import { useHealth } from "src/hooks";
import { validatePassword } from "src/modules/Validations";
import { T } from "src/i18n";
import { useTranslation } from "react-i18next";
import styles from "./index.module.css";

function LoginForm() {
    const passwordRef = useRef<HTMLInputElement>(null);
    const [ formErr, setFormErr ] = useState("");
    const { login } = useAuthState();

    const { t } = useTranslation();

    const onSubmit = async (values: any, { setSubmitting }: any) => {
        setFormErr("");
        try {
            await login(values.password);
        } catch (err) {
            if (err instanceof Error) {
                setFormErr(err.message);
            }
        }
        setSubmitting(false);
    };

    useEffect(() => {
        passwordRef.current?.focus();
    }, []);

    return (
        <>
            <h2 className="h2 text-center mb-4">
                <T id="login.title"/>
            </h2>
            { formErr !== "" && <Alert variant="danger">{ formErr }</Alert> }
            <Formik
                initialValues={
                    { password: "" } as any
                }
                onSubmit={ onSubmit }
            >
                { ({ isSubmitting }) => (
                    <Form>
                        <div className="mb-2">
                            <Field name="password" validate={ validatePassword() }>
                                { ({ field, form }: any) => (
                                    <label className="form-label">
                                        <T id="password"/>
                                        <input
                                            { ...field }
                                            type="password"
                                            autoComplete="current-password"
                                            required
                                            ref={ passwordRef }
                                            className={ `form-control ${ form.errors.password && form.touched.password ? " is-invalid" : "" }` }
                                            placeholder={ t("password") }
                                        />
                                        <div className="invalid-feedback">{ form.errors.password }</div>
                                    </label>
                                ) }
                            </Field>
                        </div>
                        <div className="form-footer">
                            <Button type="submit" fullWidth color="azure" isLoading={ isSubmitting }>
                                <T id="log-in"/>
                            </Button>
                        </div>
                    </Form>
                ) }
            </Formik>
        </>
    );
}

export default function Login() {
    const health = useHealth();

    const getVersion = () => {
        if (!health.data) {
            return "";
        }
        const v = health.data.version;
        return `v${ v.major }.${ v.minor }.${ v.patch }`;
    };

    return (
        <Page className="page page-center">
            <div className="container container-tight py-4">
                <div className="d-flex justify-content-between align-items-center mb-4 ps-4 pe-3">
                    <img
                        className={styles.logo}
                        src="/images/logo.png"
                        alt=""
                    />
                    <div className="d-flex align-items-center gap-1">
                        <LocalePicker/>
                        <ThemeSwitcher/>
                    </div>
                </div>
                <div className="card card-md">
                    <div className="card-body">
                        <LoginForm/>
                    </div>
                </div>
                <div className="text-center text-secondary mt-3">{ getVersion() }</div>
            </div>
        </Page>
    );
}
