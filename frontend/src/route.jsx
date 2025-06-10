import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Authenticated from "./Middleware/Authenticated";
import AuthProtect from "./Middleware/AuthProtect";
import App from "./Components/Index";
import Auth from "./Components/Auth/Auth";
import Register from "./Components/Auth/Register";
import SignIn from "./Components/Auth/SignIn";
import SignOut from "./Components/Auth/SignOut";
import Dashboard from "./Components/Board/Dashboard";
import Other from "./Components/UI/Other";

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/auth" element={<Auth/>}>
                    <Route element={<Authenticated />}>
                        <Route path="register" element={<Register/>}/>
                        <Route path="signIn" element={<SignIn/>}/>
                    </Route>
                    <Route path="signOut" element={<SignOut/>}/>
                </Route>
                <Route element={<AuthProtect />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/other" element={<Other />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default Router;