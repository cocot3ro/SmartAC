import cn from "classnames";
import styles from "./Page.module.css";
import React from "react";

interface Props {
	children: React.ReactNode;
	className?: string;
}

export function Page({ children, className }: Props) {
	return <div className={cn(className, styles.page)}>{children}</div>;
}
