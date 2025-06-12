import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Authenticated from "./Middleware/Authenticated";
import AuthProtect from "./Middleware/AuthProtect";
import App from "./Components/Index";
import Auth from "./Components/Auth/User/Auth";
import Register from "./Components/Auth/User/Register";
import SignIn from "./Components/Auth/User/SignIn";
import SignOut from "./Components/Auth/SignOut";
import Dashboard from "./Components/Board/Dashboard";
import Other from "./Components/UI/Other";

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/auth" element={<Auth/>}>
                    <Route element={<Authenticated />}> {/* 비인증 체크 */}
                        <Route path="register" element={<Register/>}/>
                        <Route path="signIn" element={<SignIn/>}/>
                    </Route>
                    <Route path="signOut" element={<SignOut/>}/>
                </Route>
                <Route element={<AuthProtect />}> {/* 인증된 계정 */}
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/other" element={<Other />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default Router;