import {
    Button, Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure,
} from "@chakra-ui/react";
import CreateCustomerForm from "./CreateCustomerForm.jsx";

/**
 * Add Icon String method for buttons
 *
 * @returns {string}
 * @constructor
 */
const AddIcon = () => "+"

/**
 * Close Icon String method for buttons
 *
 * @returns {string}
 * @constructor
 */
const CloseIcon = () => "x"

/**
 * Creates a Drawer that contains a form to add new customers
 *
 * @param fetchCustomers
 * @returns {JSX.Element}
 * @constructor
 */
const DrawerForm = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    return <>
        {/*Creates a button with the onOpen event that opens the Drawer*/}
        <Button leftIcon={<AddIcon/>}
                colorScheme={"telegram"}
                onClick={onOpen}>
                Add Customer
        </Button>
        {/*Drawer that contains the form to add a customer*/}
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Register a new customer</DrawerHeader>

                {/*Passing fetchCustomers method, so it can update page when creation is complete*/}
                <DrawerBody>
                    <CreateCustomerForm fetchCustomers={fetchCustomers}/>
                </DrawerBody>

                {/*Creating button with onClose event to close Drawer*/}
                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"telegram"}
                        onClick={onClose}>
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default DrawerForm
