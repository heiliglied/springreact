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
            const currentTime = Math.floor(Date.now() / 1000);
            if(jsonPayload.exp < currentTime) {
                let url = "/api/auth/refreshToken"
                fetch(url,  {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        refreshToken: localStorage.getItem("refreshToken"),
                    }),
                    credentials: 'include',
                }).then((response) => 
                    response.json()
                ).then((result) => {
                    if(result.status == "error") {
                        Swal.fire({
                            title: '경고',
                            text: result.msg, 
                            icon: 'warning',
                            confirmButtonText: '확인',
                        });
                        return false;
                    } else {
                        Swal.fire({
                            title: '알림',
                            text: result.msg, 
                            icon: 'success',
                            confirmButtonText: '확인',
                            allowOutsideClick: false, // 바깥 클릭 금지
                            allowEscapeKey: false // Esc 키 금지
                        }).then(check => {
                            if(check.isConfirmed) {
                                let accessToken = result.accessToken;
                                
                                const base64Payload = accessToken.split('.')[1]; // 페이로드 부분 가져오기
                                const jsonPayload = JSON.parse(decodeURIComponent(atob(base64Payload).split('').map(function(c) {
                                    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                                }).join('')));
        
                                //로컬 스토리지에 토큰 값 집어넣음.
                                localStorage.setItem("accessToken", accessToken);
                                localStorage.setItem("refreshToken", result.refreshToken);
        
                                //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
                                sessionStorage.setItem("auth_id", jsonPayload.auth_id);
                                sessionStorage.setItem("auth_user_id", jsonPayload.user_id);
                                sessionStorage.setItem("auth_roll", jsonPayload.roll);
                                sessionStorage.setItem("auth_name", jsonPayload.name);
                                sessionStorage.setItem("auth_email", jsonPayload.email);
                                sessionStorage.setItem("auth_exp", jsonPayload.exp);
                            }
                        });
                    }
                }).catch(() => {
                    Swal.fire({
                        title: '오류',
                        text: '로그인 과정에 오류가 발생하였습니다.', 
                        icon: 'error',
                        confirmButtonText: '확인',
                    });
                    return false;
                });
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