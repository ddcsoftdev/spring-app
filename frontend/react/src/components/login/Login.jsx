import {
    Flex,
    Box,
    FormControl,
    FormLabel,
    Input,
    Checkbox,
    Stack,
    Link,
    Button,
    Heading,
    Text,
    useColorModeValue, Alert, AlertIcon, Image, GridItem, Grid,
} from '@chakra-ui/react';
import {Formik, Form, useField} from "formik";
import * as Yup from 'yup'
import logo from '../../assets/logo.png'
import register from '../../assets/register.png'
import {useAuth} from "../context/AuthContext.jsx";
import {errorNotification} from "../../services/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {
    const {login} = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            initialValues={{username: '', password: ''}}
            validateOnMount={true}
            validationSchema={
                Yup.object({
                    username: Yup.string()
                        .email('Must be valid email')
                        .required('Email is required'),
                    password: Yup.string()
                        .max(20, 'Password cannot be more than 20 characters')
                        .min(4, 'Password cannot be less than 4 characters')
                        .required('Password is required')
                })
        }
            onSubmit={(values, {setSubmitting}) => {
                setSubmitting(true);
                login(values).then(res => {
                    navigate("/dashboard");
                }).catch(err => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(() => {
                    setSubmitting(false);
                });
            }}>

            {({isValid, isSubmitting}) => {
                return (<Form>
                    <Stack spacing={'15px'}>
                        <MyTextInput
                        label={'Email'}
                        name={'username'}
                        type={'email'}
                        placeholder={'user@example.com'}
                        />
                        <MyTextInput
                            label={'Password'}
                            name={'password'}
                            type={'password'}
                            placeholder={'Password'}
                        />
                        <Button
                            type={'submit'}
                            disabled={!isValid || isSubmitting}>
                            Login</Button>
                    </Stack>
                </Form>)
            }}
        </Formik>
    );
}
export default function Login() {
    const {user} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user){
            navigate("/dashboard")
        }
    }, [])
    return (
        <Flex
            minH={'100vh'}
            alignItems={'center'}
            justifyContent={'center'}
            bg={useColorModeValue('gray.50', 'gray.800')}>
            <Grid
                templateColumns={{ base: '1fr', md: '1fr 1fr' }}
                width="100%"
                height="100%"
            >
                <GridItem>
                    <Flex alignItems="center" justifyContent="center" height="100%">
                        <Stack spacing={8} width="100%" maxW="lg" px={6}>
                            <Stack align={'center'}>
                                <Heading fontSize={'4xl'}>Sign in to your account</Heading>
                            </Stack>
                            <LoginForm />
                        </Stack>
                    </Flex>
                </GridItem>
                <GridItem>
                    <Link href="/">
                        <Image
                            src={register}
                            alt="register Image"
                            objectFit="cover"
                            width="100%"
                            height="100vh"
                        />
                    </Link>
                </GridItem>
            </Grid>
        </Flex>
    );
}