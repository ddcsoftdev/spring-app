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

