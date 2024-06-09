import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.jsx";

const ProtectedRoute = ({children}) => {
    const {isUserAuthenticated} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (!isUserAuthenticated()){
            navigate("/");
        }
    }, [])

    return isUserAuthenticated() ? children : "Access Denied";
}

export default ProtectedRoute;