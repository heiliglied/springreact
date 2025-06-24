import { useNavigate } from "react-router";

export const decodeJwtPayload = (token) => {
    try {
        const base64Payload = token.split('.')[1]; // 페이로드 부분 가져오기
        const jsonPayload = JSON.parse(decodeURIComponent(atob(base64Payload).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join('')));

        return jsonPayload;
    } catch (e) {
        return null;
    }
}

export const createToken = (access, refresh) => {
    //로컬 스토리지에 토큰 값 집어넣음.
    try {
        localStorage.setItem("accessToken", access);
        localStorage.setItem("refreshToken", refresh);

        const payload = decodeJwtPayload(access);

        //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
        sessionStorage.setItem("auth_id", payload.auth_id);
        sessionStorage.setItem("auth_user_id", payload.user_id);
        sessionStorage.setItem("auth_roll", payload.roll);
        sessionStorage.setItem("auth_name", payload.name);
        sessionStorage.setItem("auth_email", payload.email);
        sessionStorage.setItem("auth_exp", payload.exp);

        return true;
    } catch (e) {
        return null;
    }
}

export const refreshToken = () => {
    const navigate = useNavigate();

    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
        navigate("/auth/signIn");
        return false;
    }

    const url = "/api/auth/refreshToken";
    fetch(url,  {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            refreshToken: refreshToken,
        }),
        credentials: 'include',
    }).then((response) => 
        response.json()
    ).then((result) => {
        if(result.status == "error") {
            navigate("/auth/signIn");
            return false;
        } else {
            let newAccessToken = result.accessToken;
            let newRefreshToken
                    
            const newPayload = decodeJwtPayload(newAccessToken); // 페이로드 부분 가져오기
            if(!newPayload) {
                navigate("/auth/signIn");
                return false;
            }

            //로컬 스토리지에 토큰 값 집어넣음.
            localStorage.setItem("accessToken", newAccessToken);
            if(newRefreshToken) {
                localStorage.setItem("refreshToken", newRefreshToken);
            }

            //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
            sessionStorage.setItem("auth_id", newPayload.auth_id);
            sessionStorage.setItem("auth_user_id", newPayload.user_id);
            sessionStorage.setItem("auth_roll", newPayload.roll);
            sessionStorage.setItem("auth_name", newPayload.name);
            sessionStorage.setItem("auth_email", newPayload.email);
            sessionStorage.setItem("auth_exp", newPayload.exp);

            return true;
        }
    }).catch(() => {
        navigate("/auth/signIn");
        return;
    });
}