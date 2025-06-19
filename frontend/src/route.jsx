import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./components/App";
import Auth from "./components/auth/Auth";
import Register from "./components/auth/Register";
import SignIn from "./Components/auth/SignIn";
import SignOut from "./components/auth/SignOut";
import Unauthenticate from "./middleware/Unauthenticate";

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/auth" element={<Auth/>}>
                    <Route path="register" element={<Register/>}/>
                    <Route path="signIn" element={<SignIn from="user"/>}/>
                    <Route element={<Unauthenticate/>}>
                        <Route path="signOut" element={<SignOut/>}/>
                    </Route>
                </Route>
                {/* 인증이 필요한 router */}
                

                <Route path="/admin">
                    <Route path="auth" element={<Auth/>}>
                        <Route path="signIn" element={<SignIn from="admin"/>}/>
                        <Route element={<Unauthenticate/>}>
                            <Route path="signOut" element={<SignOut/>}/>
                        </Route>
                    </Route>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default Router;