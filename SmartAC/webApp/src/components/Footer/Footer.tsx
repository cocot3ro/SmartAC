import "./Footer.css";
import {Typography} from "@mui/material";
import packageJson from '../../../package.json';

export default function Footer() {
    return (
        <footer>
            <Typography variant="body2">
                v${packageJson.version}
            </Typography>
        </footer>
    );
}
