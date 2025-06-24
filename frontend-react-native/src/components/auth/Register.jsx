import { useState } from "react";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";
import RegisterForm from "../../ui/auth/RegisterForm";

function App() {
    let url = '/api/auth/signUp';

    const [formData, setFormData] = useState(
        {
            user_id: '',
            password: '',
            re_password: '',
            email: '',
            name: '',
        }
    )

    function changeInputValue(event) {
        const { name, value } = event.target;
        let re_value = value;

        if(name == 'user_id') {
            re_value = value.replace(/[^A-Za-z0-9]/g, '');
        }

        setFormData(
            {
                ...formData,
                [name]: re_value,
            }
        )
    }

    function join() {
        const password_regex = /^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$/;
        const email_regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if(!password_regex.test(formData.password)) {
            alert('비밀번호 형식이 일치하지 않습니다.');
            return false;
        }

        if(formData.password != formData.re_password) {
            alert('비밀번호가 일치하지 않습니다.');
            return false;
        }

        if(!email_regex.test(formData.email)) {
            alert('이메일 형식을 확인 해 주세요.');
            return false;
        }

        fetch(url,  {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user_id: formData.user_id,
                password: formData.password,
                re_password: formData.re_password,
                email: formData.email,
                name: formData.email
            }),
            credentials: 'include',
        }).then((response) => {
            return response.json();
        }).then((result) => {
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
                }).then(result => {
                    if(result.isConfirmed) {
                        //location.href = "/";
                    }
                });
            }
        });
    }

    return (
        <RegisterForm formData={formData}
            changeInputValue={changeInputValue}
            onJoinSubmit={join}
        />
    );
}

export default App;
