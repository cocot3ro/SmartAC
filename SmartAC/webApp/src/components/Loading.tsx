export function Loading() {
    return (
        <div className="empty text-center">
            <div className="text-secondary mb-3">{ "Loading ..." }</div>
            <div className="progress progress-sm">
                <div className="progress-bar progress-bar-indeterminate" />
            </div>
        </div>
    );
}
