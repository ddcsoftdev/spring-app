import {createContext, useContext, useEffect, useState} from "react";
import {getCustomers, login as performLogin} from "../../services/client.js";
import {jwtDecode} from "jwt-decode";
import {useNavigate} from "react-router-dom";

// Create a context for authentication
const AuthContext = createContext({});

/**
 * AuthProvider component to manage authentication state and provide authentication functions.
 *
 * @param {Object} props - The props for the AuthProvider.
 * @param {ReactNode} props.children - The children components that will have access to the AuthContext.
 *
 * @returns {JSX.Element} The AuthProvider component with the AuthContext.Provider.
 */
const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
       let token = localStorage.getItem("access_token");
       if (token){
           token = jwtDecode(token);
           getCustomers()
               .then(res => {
                   res.data.forEach((customer) => {
                       if (customer.email === token.sub) {
                           setUser({ ...customer});
                       }
                   })
           })
       }
    }, [])

    /**
     * Function to handle user login.
     *
     * @param {Object} usernameAndPassword - An object containing the username and password.
     * @param {string} usernameAndPassword.username - The user's username.
     * @param {string} usernameAndPassword.password - The user's password.
     *
     * @returns {Promise<void>} A promise that resolves if the login is successful, or rejects with an error.
     */
    const login = async (usernameAndPassword) => {
        return new Promise((resolve, reject) => {
            performLogin(usernameAndPassword)
                .then(res => {
                    const jwtToken = res.headers["authorization"];
                    localStorage.setItem("access_token", jwtToken);
                    setUser({ ...res.data.customerDTO});
                    resolve(res);
                })
                .catch(err => reject(err));
        });
    }

    const logout = () => {
        localStorage.removeItem("access_token");
        setUser(null);
    }

    const isUserAuthenticated = () => {
        const jwtToken = localStorage.getItem("access_token");
        if (!jwtToken){
            return false;
        }

        const {exp: expiration} = jwtDecode(jwtToken);
        if (Date.now() > expiration * 1000){
            logout();
            return false;
        }
        return true;
    }

    return (
        <AuthContext.Provider value={{ user, login, logout, isUserAuthenticated }}>
            {children}
        </AuthContext.Provider>
    );
}

/**
 * Custom hook to use the AuthContext.
 *
 * @returns {Object} The context value, including user state and login function.
 */
export const useAuth = () => useContext(AuthContext);

export default AuthProvider;
