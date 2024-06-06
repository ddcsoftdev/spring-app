import { createContext, useContext, useState } from "react";
import { login as performLogin } from "../../services/client.js";

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
                    // Save user information in state
                    setUser({ ...res.data.customerDTO, token: jwtToken });
                    resolve(res);
                })
                .catch(err => reject(err));
        });
    }

    return (
        <AuthContext.Provider value={{ user, login }}>
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
