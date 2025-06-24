import React from "react";
import { Link } from "react-router-dom";

function RegisterForm({formData, changeInputValue, onJoinSubmit}) {
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
                            <button type="button" onClick={onJoinSubmit} id="join" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800">
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

export default RegisterForm;