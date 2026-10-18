import { useQueryClient } from "@tanstack/react-query";
import cn from "classnames";
import { Field, Form, Formik } from "formik";
import { useState } from "react";
import { Alert } from "react-bootstrap";
import { Button, LocalePicker, Page, ThemeSwitcher } from "src/components";
import { useAuthState } from "src/context";
import styles from "./index.module.css";
import { validatePassword } from "src/modules/Validations.ts";
import { setup } from "src/api/backend";
import { T } from "src/i18n";
import { useTranslation } from "react-i18next";

interface Payload {
    password: string;
}

export default function Setup() {
    const queryClient = useQueryClient();
    const { login } = useAuthState();
    const [ errorMsg, setErrorMsg ] = useState<string | null>(null);

    const { t } = useTranslation();

    const onSubmit = async (values: Payload, { setSubmitting }: any) => {
        setErrorMsg(null);

        try {
            setup(values.password)
                .then(() => login(values.password)
                    .then(() => {
                        queryClient.refetchQueries({ queryKey: [ "health" ] });
                    })
                    .catch(() => setErrorMsg(t("setup.error")))
                )
                .catch(() => setErrorMsg(t("setup.error")));
        } catch (err: any) {
            setErrorMsg(err.message);
        }
        setSubmitting(false);
    };

    return (
        <Page className="page page-center">
            <div className={ cn("d-none", "d-md-flex", styles.helperBtns) }>
                <LocalePicker />
                <ThemeSwitcher />
            </div>
            <div className="container container-tight py-4">
                <div className="card card-md">
                    <Alert variant="danger" show={ !!errorMsg } onClose={ () => setErrorMsg(null) } dismissible>
                        { errorMsg }
                    </Alert>
                    <Formik
                        initialValues={
                            { password: "" } as any
                        }
                        onSubmit={ onSubmit }
                    >
                        { ({ isSubmitting }) => (
                            <Form>
                                <div className="card-body text-center py-4 p-sm-5">
                                    <h1 className="mt-5">
                                        <T id="setup.title" />
                                    </h1>
                                    <p className="text-secondary">
                                        <T id="setup.preamble" />
                                    </p>
                                </div>
                                <hr />
                                <div className="card-body">
                                    <div className="mb-3">
                                        <Field name="password" validate={ validatePassword() }>
                                            { ({ field, form }: any) => (
                                                <div className="form-floating mb-3">
                                                    <input
                                                        id="password"
                                                        type="password"
                                                        autoComplete="new-password"
                                                        className={ `form-control ${ form.errors.password && form.touched.password ? "is-invalid" : "" }` }
                                                        placeholder={ t("user.new-password") }
                                                        { ...field }
                                                    />
                                                    <label htmlFor="password">
                                                        <T id="user.new-password" />
                                                    </label>
                                                    { form.errors.password ? (
                                                        <div className="invalid-feedback">
                                                            { form.errors.password && form.touched.password
                                                                ? form.errors.password
                                                                : null }
                                                        </div>
                                                    ) : null }
                                                </div>
                                            ) }
                                        </Field>
                                    </div>
                                </div>
                                <div className="text-center my-3 mx-3">
                                    <Button
                                        type="submit"
                                        actionType="primary"
                                        data-bs-dismiss="modal"
                                        isLoading={ isSubmitting }
                                        disabled={ isSubmitting }
                                        className="w-100"
                                    >
                                        <T id="save" />
                                    </Button>
                                </div>
                            </Form>
                        ) }
                    </Formik>
                </div>
            </div>
        </Page>
    );
}