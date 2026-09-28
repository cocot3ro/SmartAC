import { Loading, Page } from "src/components";

export function LoadingPage() {
	return (
		<Page className="page-center">
			<div className="container-tight py-4">
				<Loading />
			</div>
		</Page>
	);
}
