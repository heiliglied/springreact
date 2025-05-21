import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./Components/Index";
import Auth from "./Components/Auth/Auth";
import Register from "./Components/Auth/Register";
import SignIn from "./Components/Auth/SignIn";
import SignOut from "./Components/Auth/SignOut";

const Router = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<App/>}/>
                <Route path="/auth" element={<Auth/>}>
                    <Route path="register" element={<Register/>}/>
                    <Route path="signIn" element={<SignIn/>}/>
                    <Route path="signOut" element={<SignOut/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default Router;