import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {ChakraProvider} from "@chakra-ui/react";
import {createStandaloneToast} from "@chakra-ui/react";
import './index.css'

/**
 * Create a toast container so we can add toasts in this application
 */
const {ToastContainer} = createStandaloneToast()

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ChakraProvider>
            <App/>
            <ToastContainer/>
        </ChakraProvider>
    </React.StrictMode>,
)
