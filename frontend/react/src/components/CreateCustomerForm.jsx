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

/**
 * Create a Box where we can write text and be sent to our form
 *
 * @param label
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
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

/**
 * Create a Box where we can add options that can be selected and sent to form
 *
 * @param label
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
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

/**
 * Create the form itself to create a new customer
 *
 * @param fetchCustomers
 * @returns {JSX.Element}
 * @constructor
 */
const CreateCustomerForm = ({fetchCustomers}) => {
    return (
        <!--This is used to wrap code without the need of a div-->
        <>
            <!--This sections creates the form-->

            <!--Add the attributes and initial values for our form-->
            <!--Then add a validation Schema for each attribute-->
            <!--Finally add functionality when we submit-->
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
                    //Set submitting value to true
                    setSubmitting(true)

                    //Add customer with the "customer" value that has been received from the form
                    //Using method created in client.js
                    addCustomer(customer)
                        .then(r => {
                            //Log axios response to console
                            console.log(r)
                            //Create a successNotification with method from notification.js
                            successNotification(
                                "Customer Added",
                                `${customer.name} was successfully added.`)
                            //Method that gets and refreshes customers displayed
                            //This method is passed as a prop from App.jsx
                            fetchCustomers()})
                        .catch(err => {
                            //Log error to console
                            console.log(err)
                            //Create errorNotification with method from notification.js
                            errorNotification(
                                //This gets the error code
                                err.code,
                                //This gets the actual message from the error
                                err.response.data.message)})
                        .finally(() => {
                            //Finish promise by setting submit = false
                            setSubmitting(false)
                    })
                }}>

                <!--This section is going to create an instance of the form-->
                <!--It gets booleans to verify form state-->
                {({isValid, isSubmitting}) => (
                    <Form>
                        <!--Add each field with type and default elements-->
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

                            <!--Button only works if form IsValid and is NOT submitting-->
                            <Button isDisabled={!isValid || isSubmitting} type="submit"
                                    colorScheme={"green"}>Submit</Button>
                        </Stack>
                    </Form>)}
            </Formik>
        </>
    );
};

export default CreateCustomerForm