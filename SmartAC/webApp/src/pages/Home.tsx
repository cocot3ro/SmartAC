import {showError, showSuccess} from "src/notifications";

export default function Home() {
    const notify = () =>  {
        showSuccess("This is a success notification!");
        showError("This is an error notification!");
    }

    return (
        <div>
            <p>Home page</p>
            <button onClick={notify}>Notificar</button>
        </div>
    );
}
