import React, { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";

const AuthProtect = () => {
    const navigate = useNavigate();
    useEffect(() => {
        const checkAuth = async() => {
            const authId = sessionStorage.getItem("auth_id");
            const accessToken = localStorage.getItem("accessToken");

            if(!authId || !accessToken) {
                navigate("/auth/signIn");
                return;
            }

            const base64Payload = accessToken.split('.')[1]; // 페이로드 부분 가져오기
            const jsonPayload = JSON.parse(decodeURIComponent(atob(base64Payload).split('').map(function(c) {
                return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
            }).join('')));
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
                        /*
                        Swal.fire({
                            title: '경고',
                            text: result.msg, 
                            icon: 'warning',
                            confirmButtonText: '확인',
                        });
                        return false;
                        */
                        navigate("/auth/signIn");
                        return;
                    } else {
                        let accessToken = result.accessToken;
                                
                        const newPayload = accessToken.split('.')[1]; // 페이로드 부분 가져오기
                        const newJson = JSON.parse(decodeURIComponent(atob(newPayload).split('').map(function(c) {
                            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                        }).join('')));

                        //로컬 스토리지에 토큰 값 집어넣음.
                        localStorage.setItem("accessToken", accessToken);
                        localStorage.setItem("refreshToken", result.refreshToken);

                        //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
                        sessionStorage.setItem("auth_id", newJson.auth_id);
                        sessionStorage.setItem("auth_user_id", newJson.user_id);
                        sessionStorage.setItem("auth_roll", newJson.roll);
                        sessionStorage.setItem("auth_name", newJson.name);
                        sessionStorage.setItem("auth_email", newJson.email);
                        sessionStorage.setItem("auth_exp", newJson.exp);
                    }
                }).catch(() => {
                    navigate("/auth/signIn");
                    return;
                });
            }
        };

        checkAuth();

    }, [navigate]);

    return <Outlet/>;
};

export default AuthProtect;