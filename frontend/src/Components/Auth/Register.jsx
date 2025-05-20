//import NavBar from '../Navbar';
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Swal from "sweetalert2";

function App() {
    let url = '/api/auth/signUp';

    const [formData, setFormData] = useState(
        {
            user_id: '',
            password: '',
            re_password: '',
            email: '',
            name: ''
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
                    <span className='text-2xl items-center'>회원가입</span>
                </div>
                <div className='mt-10 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>ID : </div>
                        <div className='w-[60%]'>
                            <input type="text" name="user_id" value={formData.user_id} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>Password : </div>
                        <div className='w-[60%]'>
                            <input type="password" name="password" value={formData.password} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>Re-Password : </div>
                        <div className='w-[60%]'>
                            <input type="password" name="re_password" value={formData.re_password} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>E-mail : </div>
                        <div className='w-[60%]'>
                            <input type="text" name="email" value={formData.email} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'>Nickname : </div>
                        <div className='w-[60%]'>
                            <input type="text" name="name" value={formData.name} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="" required onChange={changeInputValue}/>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex'>
                        <div className='w-[40%] p-2.5 flex justify-end'></div>
                        <div className='w-[60%]'>
                            <button type="button" onClick={join} id="join" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
                                가입하기
                            </button>
                        </div>
                    </div>
                </div>
                <div className='mt-4 flex items-center justify-center w-full'>
                    <div className='w-[80%] flex justify-center mt-2'>
                        <Link className='underline text-xl text-blue-600' to="/auth/signIn">로그인 화면으로 이동</Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
