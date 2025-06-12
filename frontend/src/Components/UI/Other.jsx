import React, { useState } from "react";

function Other() {
    const navigate = useNavigate();

    useEffect(() => {
        const Authenticated = async() => {
            const url = "/api/scheduler/authTest";

            fetch(url,  {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("accessToken"),
                    'refreshToken': localStorage.getItem("refreshToken"),
                },
                credentials: 'include',
            }).then((response) => {
                const data = {
                    'statusCode': response.status,
                    'data': response.json(),
                };

                return data;
            }).then((result) => {
                if(result.statusCode == 401) {
                    if(result.data.status == 'refresh') {

                    } else {
                        navigate('/auth/signIn');
                    }
                }
            });
        }

        Authenticated();
    });

    return (
        <div>
            <h1>outlet 테스트</h1>
        </div>
    );
}

export default Other;