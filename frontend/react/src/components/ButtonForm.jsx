import {
    Button,
    FormControl,
    FormLabel,
    Input, Modal, ModalBody, ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay, useDisclosure
} from "@chakra-ui/react";
import {useRef} from "react";
import ModifyCustomerForm from "./ModifyCustomerForm.jsx";

function ButtonForm(props) {

    const { isOpen, onOpen, onClose } = useDisclosure()

    const initialRef = useRef(null)
    const finalRef = useRef(null)
    return (
        <>
            <Button
                colorScheme={props.colorScheme}
                mr={props.mr}
                _hover={props._hover}
                onClick={onOpen}>
                {props.buttonName}</Button>


            <Modal
                initialFocusRef={initialRef}
                finalFocusRef={finalRef}
                isOpen={isOpen}
                onClose={onClose}
            >
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>{props.formTitle}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <ModifyCustomerForm
                            customerName={props.customerName}
                            customerEmail={props.customerEmail}
                            customerAge={props.customerAge}
                            customerGender={props.customerGender}
                            customerId={props.customerId}
                            fetchCustomers={props.fetchCustomers}
                        />
                    </ModalBody>

                    <ModalFooter>
                        <Button onClick={onClose}>Cancel</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default ButtonForm