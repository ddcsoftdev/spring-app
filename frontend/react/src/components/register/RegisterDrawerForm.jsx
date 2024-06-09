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
import RegisterForm from "./RegisterForm.jsx";


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
const RegisterDrawerForm = ({fetchCustomers}) => {
    const {isOpen, onOpen, onClose} = useDisclosure()
    const handleFormSuccess = () => {
        onClose();
    }
    return <>
        {/*Creates a button with the onOpen event that opens the Drawer*/}
        <Button
                mt={"-20px"}
                onClick={onOpen}>
                Register
        </Button>
        {/*Drawer that contains the form to add a customer*/}
        <Drawer isOpen={isOpen} onClose={onClose} size={"xl"}>
            <DrawerOverlay/>
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Register User</DrawerHeader>

                {/*Passing fetchCustomers method, so it can update page when creation is complete*/}
                <DrawerBody>
                    <RegisterForm onFormSuccess={handleFormSuccess}/>
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

export default RegisterDrawerForm
