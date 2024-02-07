import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {
    Wrap,
    WrapItem,
    Spinner,
    Text
} from "@chakra-ui/react";
import {useState, useEffect} from "react";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";
import CreateCustomerDrawerForm from "./components/CreateCustomerDrawerForm.jsx"
import {errorNotification} from "./services/notification.js";

const App = () => {
    //userState returns two values: the current state and a way to set a new one
    //Create a useState with an empty array to set customers
    const [customers, setCustomers] = useState([])
    //Create a useState with a boolean variable for loading
    const [loading, setLoading] = useState(false)
    //Create a useState to set error in case customer loading not possible
    const [err, setError] = useState("")

    /**
     * Method that fetches customer from the http address
     * It's also a way to refresh the content of customers within page
     */
    const fetchCustomers = () => {
        //Set loading to true as in "start loading"
        setLoading(true)
        getCustomers()
            //Set customers to the payload of request
            .then((res) => setCustomers(res.data))
            //Show error if http request failed
            .catch((err) => {
                setError(err.response.data.message)
                errorNotification(
                    err.code,
                    err.response.data.message
                )
                console.log(err)
            })
            //Set loading to false as in "request finished"
            .finally(() => setLoading(false))
    }
    //Get info of customers from server with a React Hook
    useEffect(() => {
        fetchCustomers()
    }, [])

    //Using a component for loading icon
    if (loading) {
        return (
            <SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='green.500'
                    size='xl'/>
            </SidebarWithHeader>
        )
    }
    //If error then display error message
    if (err) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawerForm
                    fetchCustomers={fetchCustomers}/>
                <Text mt={5}>Ops there was an error</Text>
            </SidebarWithHeader>
        )
    }

    //If no customers found then notify user
    if (customers.length <= 0) {
        return (
            <SidebarWithHeader>
                <CreateCustomerDrawerForm
                    fetchCustomers={fetchCustomers}
                />
                <Text mt={5}>No Customer Available</Text>
            </SidebarWithHeader>
        )
    }

    //If everything goes well just print customers
    return (
        //Map all customers name
        <SidebarWithHeader>
            {/* Pass fetchCustomers to the CreateCustomerDrawerForm*/}
            <CreateCustomerDrawerForm
                fetchCustomers={fetchCustomers}
            />
            <Wrap spacing={"30px"} justify={"center"} align={"center"}>
                {/* For each customer then create a card with its info */}
                {customers.map((customer, index) => (
                    <WrapItem key={index}>
                        <CardWithImage fetchCustomers={fetchCustomers} {...customer}></CardWithImage>
                    </WrapItem>
                ))}
            </Wrap>
        </SidebarWithHeader>
    )
}
export default App
