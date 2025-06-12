import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import { useState } from "react";
import SignInForm from "../../ui/auth/SignInForm";
import { createToken } from "../../utils/JwtUtils";

function App({from}) {
    let url = '/api/auth/signIn';

    const [user_id, setUserId] = useState('');
    const [password, setPassword] = useState('');

    function changeInputValue(event) {
        const { name, value } = event.target;
        let re_value = value;

        if(name == 'user_id') {
            re_value = value.replace(/[^A-Za-z0-9]/g, '');
            setUserId(re_value);
        } else {
            setPassword(re_value);
        }
    }

    async function signIn(event) {

        const password_regex = /^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$/;
        if(!password_regex.test(password)) {
            alert('비밀번호 형식이 일치하지 않습니다.');
            return false;
        }

        await fetch(url,  {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user_id: user_id,
                password: password,
                roll: from,
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
                }).then((check) => {
                    if(check.isConfirmed) {
                        const result = createToken(result.accessToken, result.refreshToken);
                        if(!result) {
                            return;
                        }

                        location.href = "/";
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

    return (
        <SignInForm
            from={from}
            user_id={user_id}
            password={password}
            changeInputValue={changeInputValue}
            signIn={signIn}
        />
    );
}

export default App;