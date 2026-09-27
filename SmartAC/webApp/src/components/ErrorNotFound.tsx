import {useNavigate} from "react-router-dom";
import {Button} from "src/components";

export function ErrorNotFound() {
    const navigate = useNavigate();

    // TODO: Localize

    return (
        <div className="container-tight py-4">
            <div className="empty">
                <p className="empty-title">
                    <span>Ups… Has encontrado una página de error</span>
                </p>
                <p className="empty-subtitle text-secondary">
                    <span>Lo sentimos, pero la página que buscas no fue encontrada</span>
                </p>
                <div className="empty-action">
                    <Button type="button" size="md" onClick={() => navigate("/")}>
                        <span>Llévame al inicio</span>
                    </Button>
                </div>
            </div>
        </div>
    );
}
