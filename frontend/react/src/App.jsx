import SidebarWithHeader from "./components/shared/SideBar.jsx";
import {Wrap,
        WrapItem,
        Spinner,
        Text} from "@chakra-ui/react";
import {useState, useEffect} from "react";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";

const App = () => {
    //Create a useState with an empty array
    const [customers, setCustomers] = useState([])
    //Create a useState with a false variable for loading
    const [loading, setLoading] = useState(false)

   //Get info of customers from server with a React Hook
    useEffect( () =>{
        //Set loading to true as in "start loading"
        setLoading(true)
        getCustomers()
            //Set customers to the payload of request
            .then((res) => setCustomers(res.data))
            //Show error if http request failed
            .catch((err) => console.log(err))
            //Set loading to false as in "request finished"
            .finally(() => setLoading(false))
        }, [])

    //Create a loading component
    if (loading){
       return(
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

    //If no customers found then notify user
    if (customers.length <= 0){
        return (
            <SidebarWithHeader>
                <Text>No Customer Available</Text>
            </SidebarWithHeader>
        )
    }

    //If everything goes well just print customers
    return (
        //Map all customers name
            <SidebarWithHeader>
                <Wrap spacing={"30px"} justify={"center"} align={"center"}>
                {customers.map((customer, index) =>(
                    <WrapItem key={index}>
                    <CardWithImage {...customer}></CardWithImage>
                    </WrapItem>
                ))}
                </Wrap>
            </SidebarWithHeader>
    )
}
export default App
