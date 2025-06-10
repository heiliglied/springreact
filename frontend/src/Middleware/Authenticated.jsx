import { useEffect } from "react";
import { useNavigate, Outlet } from "react-router-dom";

const Authenticated = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const authCheck = async() => {
            const authId = sessionStorage.getItem("auth_id");
            const accessToken = localStorage.getItem("accessToken");

            if(!authId || !accessToken) {
                console.log('a');
                return <Outlet/>;
            } else {
                console.log('b');
                navigate("/");
            }
        }

        authCheck();
    });
}

export default Authenticated;