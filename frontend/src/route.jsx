import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import App from "./Components/Index";
import Auth from "./Components/Auth/Auth";
import Register from "./Components/Auth/Register";
import SignIn from "./Components/Auth/SignIn";
import SignOut from "./Components/Auth/SignOut";

const authMiddleWare = () => {
    if(sessionStorage.getItem("auth_id") == "" || sessionStorage.getItem("auth_id") == undefined) {
        if(localStorage.getItem("accessToken") == "" || localStorage.getItem("accessToken") == undefined) {
            location.href="/auth/signIn";
        } else {
            let accessToken = localStorage.getItem("accessToken");
            const base64Payload = accessToken.split('.')[1]; // 페이로드 부분 가져오기
            const jsonPayload = JSON.parse(decodeURIComponent(atob(base64Payload).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join('')));
            //accessToken 유효기간 체크. 및 갱신.
            if(jsonPayload.exp) {

            }
        }
    }
};

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