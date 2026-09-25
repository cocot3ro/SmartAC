import {toast} from "react-toastify";
import {Msg} from "src/notifications";
import styles from "./Msg.module.css";

const showSuccess = (message: string) => {
    toast(Msg, {
        className: styles.toaster,
        data: {
            type: "success",
            title: "Success",
            message,
        },
    });
};

const showError = (message: string) => {
    toast(<Msg/>, {
        className: styles.toaster,
        data: {
            type: "error",
            title: "Error",
            message,
        },
    });
};

const showObjectSuccess = (obj: string, action: string) => {
    showSuccess(
        "Foo"
    );
};

export {showSuccess, showError, showObjectSuccess};
