import Swal from "sweetalert2";

function App() {
    try {
        localStorage.setItem("accessToken", "");
        localStorage.setItem("refreshToken", "");

        //인증정보는 session 스토리지에. 앱 모든 인증에 세션스토리지 체크 시킴.
        sessionStorage.setItem("auth_id", "");
        sessionStorage.setItem("auth_user_id", "");
        sessionStorage.setItem("auth_roll", "");
        sessionStorage.setItem("auth_name", "");
        sessionStorage.setItem("auth_email", "");
        sessionStorage.setItem("auth_exp", "");

        location.href = "/";
    } catch(error) {
        Swal.fire({
            title: '경고',
            text: '로그아웃 하지 못했습니다.', 
            icon: 'warning',
            confirmButtonText: '확인',
        });
        return false;
    }

    /* JWT 방식이라 갔다올 필요 없음.
    let url = '/api/auth/logout';

    fetch(url,  {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
    }).then((response) => {
        if(response.status == 200) {
            let result = response.json();

            if(result.status == 'success') {
                Swal.fire({
                    title: '알림',
                    text: '로그아웃 되었습니다.', 
                    icon: 'success',
                    confirmButtonText: '확인',
                    allowOutsideClick: false, // 바깥 클릭 금지
                    allowEscapeKey: false // Esc 키 금지
                }).then(result => {
                    if(result.isConfirmed) {
                        location.href = "/";
                    }
                });
            } else if(result.status == 'warning') {
                Swal.fire({
                    title: '알림',
                    text: '로그인 된 계정이 없습니다.', 
                    icon: 'warning',
                    confirmButtonText: '확인',
                    allowOutsideClick: false, // 바깥 클릭 금지
                    allowEscapeKey: false // Esc 키 금지
                }).then(result => {
                    if(result.isConfirmed) {
                        location.href = "/";
                    }
                });
            } else {
                Swal.fire({
                    title: '경고',
                    text: '로그아웃 하지 못했습니다.', 
                    icon: 'warning',
                    confirmButtonText: '확인',
                });
                return false;
            }

        } else {
            Swal.fire({
                title: '경고',
                text: '로그아웃 하지 못했습니다.', 
                icon: 'warning',
                confirmButtonText: '확인',
            });
            return false;
        }
    });
    */
}

export default App;