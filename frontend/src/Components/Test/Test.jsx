import Swal from "sweetalert2";

function Test() {
    let url = "/api/auth/refreshToken";

    fetch(url,  {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("accessToken"),
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
            console.log('test?');
            console.log(result);
            /*
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
                    let refreshToken = result.refreshToken;
                    
                    const base64Payload = accessToken.split('.')[1]; // 페이로드 부분 가져오기
                    const jsonPayload = JSON.parse(decodeURIComponent(atob(base64Payload).split('').map(function(c) {
                        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                    }).join('')));

                    //로컬 스토리지에 토큰 값 집어넣음.
                    localStorage.setItem("accessToken", accessToken);
                    localStorage.setItem("refreshToken", refreshToken);

                    //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
                    sessionStorage.setItem("auth_id", jsonPayload.auth_id);
                    sessionStorage.setItem("auth_user_id", jsonPayload.user_id);
                    sessionStorage.setItem("auth_roll", jsonPayload.roll);
                    sessionStorage.setItem("auth_name", jsonPayload.name);
                    sessionStorage.setItem("auth_email", jsonPayload.email);
                    sessionStorage.setItem("auth_exp", jsonPayload.exp);

                    location.href = "/";
                }
            });
            */
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

export default Test();