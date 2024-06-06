import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {ChakraProvider} from "@chakra-ui/react";
import {createStandaloneToast} from "@chakra-ui/react";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import './index.css'
import Login from "./components/login/Login.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";

/**
 * Create a toast container so we can add toasts in this application
 */
const {ToastContainer} = createStandaloneToast()

const router = createBrowserRouter([{
    path:"/",
    element:<Login/>
    },
    {
        path: "dashboard",
        element: <App/>
    }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <ChakraProvider>
            <AuthProvider>
                <RouterProvider router={router}/>
            </AuthProvider>
            <ToastContainer/>
        </ChakraProvider>
    </React.StrictMode>,
)
