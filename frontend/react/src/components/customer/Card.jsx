'use client'
import {
    Heading,
    Avatar,
    Box,
    Center,
    Image,
    Flex,
    Text,
    Stack,
    Button,
    Tag,
    useColorModeValue,
    useDisclosure,
    AlertDialog,
    AlertDialogOverlay,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialogBody, AlertDialogFooter,
} from '@chakra-ui/react'
import {deleteCustomer} from "../../services/client.js";
import {errorNotification, successNotification} from "../../services/notification.js";
import {useRef} from "react";
import ModifyCustomerButtonForm from "./ModifyCustomerButtonForm.jsx";

/**
 * Method that creates a card with all data from a customer
 *
 * @param fetchCustomers
 * @param id
 * @param name
 * @param email
 * @param age
 * @param gender
 * @returns {JSX.Element}
 * @constructor
 */
export default function CardWithImage({fetchCustomers, id, name, email, age, gender}) {

    /**
     * Method that converts db gender entry into readable gender tag
     *
     * @param gender
     * @returns {string}
     */
    const convertGenderEntry = (gender) => {
        if (gender === "MALE") {
            return "men"
        } else if (gender === "FEMALE") {
            return "women"
        }
    }

    /**
     * Alert Dialog functions
     */
    const {isOpen, onOpen, onClose} = useDisclosure()
    const cancelRef = useRef()

    /**
     * Deletes a customer and fetches from database, refreshing the content displayed
     *
     * @param id
     */
    const deleteCustomerWithId = (id) => {

        //Method from client.js that deletes the customer
        deleteCustomer(id)
            .then(r => {
                //Log message to log
                console.log("Deleting customer with id: " + id)
                //Create successNotification with method from notification.js
                successNotification(
                    "Customer deleted",
                    `Customer with id ${id} has been deleted`
                )
                //Method that gets and refreshes customers displayed
                //This method is passed as a prop from App.jsx
                fetchCustomers()
            })
            .catch((err) => {
                //Log error to console
                console.log(err)
                //Create errorNotification with method from notification.js
                errorNotification(
                    err.code,
                    err.response.data.message
                )
            })
            .finally(() => {
            })
    }

    return (
        <Center py={6}>
            {/* Box that contains the card*/}
            <Box
                minW={'300px'}
                maxW={'300px'}
                maxH={'450px'}
                minH={'450px'}
                w={'full'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'2xl'}
                rounded={'md'}
                overflow={'hidden'}>
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                        'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit="cover"
                    alt="#"
                />

                {/*Add image from external api address*/}
                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${convertGenderEntry(gender)}/${id}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                {/*Box that contains the customer information*/}
                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderradioyes={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray'} fontscale={"xs"}>{email}</Text>
                        <Text color={'gray'}>Age {age}</Text>
                        <Text color={'gray'}>{gender.toString().toLowerCase()}</Text>
                        <Stack display={'inline'} mt={3}>
                            {/*Modify: Clicking this button triggers a Drawer with form*/}
                            <ModifyCustomerButtonForm
                                buttonName={'Modify'}
                                formTitle={`Modify Customer ${id}: ${name}`}
                                customerName = {name}
                                customerEmail = {email}
                                customerAge = {age}
                                customerGender = {gender}
                                customerId = {id}
                                colorScheme={"blue"}
                                fetchCustomers={fetchCustomers}
                                mr={3}
                                _hover={{
                                    transform: 'translateY(-2px)',
                                    boxShadow: 'lg'
                                }}
                            ></ModifyCustomerButtonForm>
                            {/*Delete: Clicking this button triggers onOpen AlertDialog event*/}
                            <Button
                                colorScheme={"red"}
                                _hover={{
                                    transform: 'translateY(-2px)',
                                    boxShadow: 'lg'
                                }}
                                onClick={onOpen}>Delete
                            </Button>

                            {/*Alert Dialog functionality*/}
                            <AlertDialog
                                isOpen={isOpen}
                                leastDestructiveRef={cancelRef}
                                onClose={onClose}>

                                <AlertDialogOverlay>
                                    <AlertDialogContent>
                                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                            Delete Customer
                                        </AlertDialogHeader>

                                        <AlertDialogBody>
                                            Are you sure you want to delete {name}? You can't undo this action
                                            afterwards.
                                        </AlertDialogBody>

                                        <AlertDialogFooter>
                                            <Button ref={cancelRef} onClick={onClose}>Cancel
                                            </Button>
                                            {/*Delete button that sends a delete request through the API*/}
                                            <Button colorScheme='red'
                                                    ml={3}
                                                    onClick={() => {
                                                        //Delete customer promise
                                                        deleteCustomer(id)
                                                            .then((r) => {
                                                                //Log response to console
                                                                console.log(r)
                                                                //Trigger success notification
                                                                successNotification(
                                                                    `Customer Removed `,
                                                                    `Customer ${name} has been deleted.`
                                                                )
                                                                //fetch new customers and refresh content
                                                                fetchCustomers()
                                                            })
                                                            .catch((err) => {
                                                                //Log response to console
                                                                console.log(err)
                                                                //Trigger error notification
                                                                errorNotification(
                                                                    err.code,
                                                                    err.response.data.message
                                                                )
                                                            })
                                                            .finally(
                                                                //Close Alert Dialog
                                                                onClose
                                                            )
                                                    }}>Delete
                                            </Button>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialogOverlay>
                            </AlertDialog>
                        </Stack>
                    </Stack>
                </Box>

            </Box>
        </Center>
    )
}