import React, { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { decodeJwtPayload, refreshToken } from "@/Components/Auth/JwtUtils";

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

            const jsonPayload = decodeJwtPayload(accessToken);
            const currentTime = Math.floor(Date.now() / 1000);

            if(jsonPayload.exp < currentTime) {
                const refresh = await refreshToken();
                if(!refresh) {
                    return;
                }
            }
        };

        checkAuth();

    }, [navigate]);

    return <Outlet/>;
};

export default AuthProtect;