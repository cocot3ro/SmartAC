import {useNavigate} from "react-router-dom";
import useLocalizeDocumentAttributes from "./i18n/useLocalizeDocumentAttributes.ts";
import Header from "./components/Header/Header.tsx";
import Footer from "./components/Footer/Footer.tsx";
import {Home} from "./pages/Home/Home.tsx";

function App() {

    let navigate = useNavigate();

    useLocalizeDocumentAttributes();

    return (
        <div>
            <Header/>

            <div style={{flex: 1}}>
                <Home/>
            </div>

            <Footer/>
        </div>
        // <div className="page">
        //     <div className="page-wrapper">
        //         <div className="container-xl py-4">
        //             <div className="card">
        //                 <div className="card-body">
        //                     <h3 className="card-title">React + Tabler</h3>
        //                     <p className="text-secondary mb-0">Your Tabler setup is working.</p>
        //                 </div>
        //             </div>
        //         </div>
        //     </div>
        // </div>
    );
}

export default App;
