import packageJson from '../../../package.json';

function Footer() {
    return (
        <footer
            style={{
                padding: '16px 0',
                textAlign: 'left',
            }}
        >
            <p variant="body2" color="text.secondary">
                v{packageJson.version}
            </p>
        </footer>
    );
}

export default Footer;