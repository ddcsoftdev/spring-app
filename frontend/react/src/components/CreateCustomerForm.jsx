import {Formik, Form, useField} from 'formik';
import * as Yup from 'yup';
import {
    Alert,
    AlertDescription,
    AlertIcon,
    AlertTitle,
    Box,
    Button,
    FormLabel,
    Input,
    Select,
    Stack
} from "@chakra-ui/react";
import {addCustomer} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";
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

const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

// And now we can use these
const CreateCustomerForm = ({fetchCustomers}) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    age: '',
                    gender: '',
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    age: Yup.number()
                        .min(18, 'Must be at least 18')
                        .max(99, 'Must be less than 100')
                        .required('Required'),
                    gender: Yup.string()
                        .oneOf(
                            ['MALE', 'FEMALE'],
                            'Invalid Gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(customer, {setSubmitting}) => {
                    setSubmitting(true)
                    addCustomer(customer)
                        .then(r =>{
                            console.log(r)
                            successNotification(
                                "Customer Added",
                                `${customer.name} was successfully added.`
                                )
                            fetchCustomers()
                        }).catch(err => {
                            console.log(err)
                        errorNotification(
                            err.code,
                            err.response.data.message
                        )
                        }).finally(()=> {
                            setSubmitting(false)
                            })
                }}
            >
                {({isValid, isSubmitting}) => (
                    <Form>
                        <Stack spacing={15}>
                            <MyTextInput
                                label="Name"
                                name="name"
                                type="text"
                                placeholder="Jane"
                            />

                            <MyTextInput
                                label="Email"
                                name="email"
                                type="text"
                                placeholder="Jane@example.com"
                            />

                            <MyTextInput
                                label="Age"
                                name="age"
                                type="number"
                                placeholder="35"
                            />

                            <MySelect label="Gender" name="gender">
                                <option value="">Select a gender</option>
                                <option value="MALE">Male</option>
                                <option value="FEMALE">Female</option>
                            </MySelect>

                            <Button isDisabled={!isValid || isSubmitting} type="submit"
                                    colorScheme={"green"}>Submit</Button>
                        </Stack>
                    </Form>
                )
                }
            </Formik>
        </>
    );
};

export default CreateCustomerForm