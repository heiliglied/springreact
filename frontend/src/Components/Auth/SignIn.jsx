import React, { useState } from "react";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";

function App() {
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

    function signIn() {
        const password_regex = /^(?=.*[a-zA-Z])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9-_!@#$%^]{8,10}$|^(?=.*[0-9])(?=.*[-_!@#$%^])[A-Za-z0-9-_!@#$%^]{8,10}$/;
        if(!password_regex.test(password)) {
            alert('비밀번호 형식이 일치하지 않습니다.');
            return false;
        }

        fetch(url,  {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                user_id: user_id,
                password: password,
            }),
            credentials: 'include',
        }).then((response) => {
            return response.json();
        }).then((result) => {
            console.log(result);
            return false;
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
                        location.href = "/";
                    }
                });
            }
        });
    }

    return (
        <div className="absolute top-0 left-0 w-full h-full overflow-y-auto flex flex-wrap justify-center items-stretch flex-row-reverse">
            <div className='container w-[480px] h-full ml-auto flex flex-col justify-center'>
                <div className='flex items-center justify-center w-full'>
                    <span className='text-2xl items-center'>로그인</span>
                </div>
                <div className='mt-10 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>ID : </div>
                        <div className='w-[60%]'>
                            <input type="text" name="user_id" value={user_id} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>Password : </div>
                        <div className='w-[60%]'>
                            <input type="password" name="password" value={password} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'></div>
                        <div className='w-[60%]'>
                            <button type="button" onClick={signIn} id="signIn" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                                로그인
                            </button>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex justify-center mt-2'>
                        <Link className='underline text-xl text-blue-600' to="/auth/register">회원가입 화면으로 이동</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;