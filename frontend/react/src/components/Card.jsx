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
} from '@chakra-ui/react'
import {deleteCustomer} from "../services/client.js";


export default function CardWithImage({id, name, email, age, gender}) {
    //adapt gender to fit "male" or "female"
    const converGenderEntry = (gender) =>{
        if (gender === "MALE"){
            return "men"
        } else if (gender === "FEMALE"){
            return "women"
        }
    }

    //delete customer and refresh page
    const deleteAndRefreshPage = (id) => {
        deleteCustomer(id)
            .then(r => console.log("deleting customer with id: " + id))
            .catch((err) => console.log(err))
            .finally(window.location.reload())
    }

    return (
        <Center py={6}>
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

                <Flex justify={'center'} mt={-12}>
                    <Avatar
                        size={'xl'}
                        src={
                            `https://randomuser.me/api/portraits/${converGenderEntry(gender)}/${id}.jpg`
                        }
                        css={{
                            border: '2px solid white',
                        }}
                    />
                </Flex>

                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag borderRadioyes={"full"}>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray'} fontScale={"xs"}>{email}</Text>
                        <Text color={'gray'}>Age {age}</Text>
                        <Text color={'gray'}>{gender.toString().toLowerCase()}</Text>
                        <Button>Modify</Button>
                        <Button onClick={ () => deleteAndRefreshPage(id)}>Delete</Button>
                    </Stack>

                </Box>
            </Box>
        </Center>
    )
}