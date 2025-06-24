import { useEffect } from "react";
import { useNavigate, Outlet } from "react-router-dom";

const Unauthenticate = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const authCheck = async() => {
            const authId = sessionStorage.getItem("auth_id");
            const accessToken = localStorage.getItem("accessToken");

            if(!authId || !accessToken) {
                return <Outlet/>;
            } else {
                navigate("/");
            }
        }

        authCheck();
    }, [navigate]);
}

export default Unauthenticate;