import { Button, LocalePicker, Page, ThemeSwitcher } from "src/components";
import cn from "classnames";
import styles from "src/pages/Setup/index.module.css";
import { Alert } from "react-bootstrap";
import { Field, Form, Formik } from "formik";
import { T } from "src/i18n";
import { validatePassword } from "src/modules/Validations.ts";

export default function Index() {
    return (
        <Page className="page page-center">
            <div className={cn("d-none", "d-md-flex", "align-items-center", "gap-1", styles.helperBtns)}>
                <LocalePicker/>
                <ThemeSwitcher/>
            </div>
            <div className="container container-tight py-4">
                <div className="card card-md">
                    <Alert variant="danger" show={!!errorMsg} onClose={() => setErrorMsg(null)} dismissible>
                        {errorMsg}
                    </Alert>
                    <Formik
                        initialValues={
                            {email: "", password: ""} as any
                        }
                        onSubmit={onSubmit}
                    >
                        {({isSubmitting}) => (
                            <Form>
                                <div className="card-body text-center py-4 p-sm-5">
                                    <h1 className="mt-5">
                                        <T id={"setup.title"}/>
                                    </h1>
                                    <p className="text-secondary">
                                        <T id={"setup.preamble"}/>
                                    </p>
                                </div>
                                <hr/>
                                <div className="card-body">
                                    <div className="mb-3">
                                        <Field name="password" validate={validatePassword()}>
                                            {({field, form}: any) => (
                                                <div className="form-floating mb-3">
                                                    <input
                                                        id="password"
                                                        type="password"
                                                        autoComplete="new-password"
                                                        className={`form-control ${form.errors.password && form.touched.password ? "is-invalid" : ""}`}
                                                        placeholder={t("user.new-password")}
                                                        {...field}
                                                    />
                                                    <label htmlFor="password">
                                                        <T id={"user.new-password"}/>
                                                    </label>
                                                    {form.errors.password ? (
                                                        <div className="invalid-feedback">
                                                            {form.errors.password && form.touched.password
                                                                ? form.errors.password
                                                                : null}
                                                        </div>
                                                    ) : null}
                                                </div>
                                            )}
                                        </Field>
                                    </div>
                                </div>
                                <div className="text-center my-3 mx-3">
                                    <Button
                                        type="submit"
                                        actionType="primary"
                                        data-bs-dismiss="modal"
                                        isLoading={isSubmitting}
                                        disabled={isSubmitting}
                                        className="w-100"
                                    >
                                        <T id={"save"}/>
                                    </Button>
                                </div>
                            </Form>
                        )}
                    </Formik>
                </div>
            </div>
        </Page>
    );
}