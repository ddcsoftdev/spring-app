import axios from 'axios'

/**
 * Get customers from HTTP address with a GET request
 *
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`)
    } catch (e) {
        throw e;
    }
}

/**
 * Delete a customer on HTTP address with a DELETE request
 *
 * @param id
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const deleteCustomer = async (id) => {
    try {
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`)
    } catch (e) {
        throw e;
    }
}

/**
 * Add a new customer on HTTP address with a POST request
 *
 * @param customer
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const addCustomer = async (customer) => {
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            customer)
    } catch (e) {
        throw e;
    }
}

/**
 * Modify a new customer on HTTP address with a PUT request
 *
 * @param customer
 * @param id customer id
 * @returns {Promise<axios.AxiosResponse<any>>}
 */
export const modifyCustomer = async (customer, id) => {
    try {
        return await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            customer)
    } catch (e) {
        throw e;
    }
}

/**
 * Logs in a user by sending their credentials to the authentication endpoint.
 *
 * @param {Object} usernameAndPassword - An object containing the username and password.
 * @param {string} usernameAndPassword.username - The user's username.
 * @param {string} usernameAndPassword.password - The user's password.
 *
 * @returns {Promise<Object>} The response from the server if the login is successful.
 *
 * @throws Will throw an error if the login attempt fails.
 */
export const login = async (usernameAndPassword) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword)
    } catch (e) {
        throw e;
    }
}