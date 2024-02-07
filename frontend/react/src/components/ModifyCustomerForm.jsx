import {Form, Formik, useField} from "formik";
import * as Yup from 'yup';
import {Alert, AlertIcon, Avatar, Box, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {modifyCustomer} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";

/**
 * Create a Box where we can write text and be sent to our form
 *
 * @param label
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
const FormTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props}/>
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
const FormSelectInput = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props}/>
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

const selectGender = (customerGender) => {
    if (customerGender === 'MALE'){
        return (
            <>
            <option value="MALE">Male</option>
            <option value="FEMALE">Female</option>
            </>
        )
    } else {
        return (
            <>
                <option  value="FEMALE">Female</option>
                <option value="MALE">Male</option>
            </>
        )
    }
}

const ModifyCustomerForm = (props) => {
    return (
    <>
    <Formik
        initialValues={{
            name: props.customerName,
            email: props.customerEmail,
            age: props.customerAge,
            gender: props.customerGender,
        }}
        validationSchema={Yup.object({
                name: Yup.string()
                    .max(15, 'Name must be shorter than 15 character')
                    .min(1, 'Name must be longer that 1 character'),
                email: Yup.string()
                    .email('Invalid email address'),
                age: Yup.number()
                    .min(18, 'Must be at least 18')
                    .max(99, 'Must be younger than 99'),
                gender: Yup.string()
                    .oneOf(['MALE', 'FEMALE'], 'Invalid Gender')
            },
        )}
        onSubmit={(customer, {setSubmitting}) => {
            setSubmitting(true)
            modifyCustomer(customer, props.customerId)
                .then(r => {
                    console.log(r)
                    successNotification("Success", "Customer modified")
                    props.fetchCustomers()
                }).catch(err => {
                    console.log(err)
                    errorNotification(err.code, err.response.data.message)
                }).finally(() => {
                    setSubmitting(false)
                })
        }}>
            {/*these values are arguments from Formik*/}
            { ({dirty, isSubmitting}) => {
                return (
                <Form>
                    <Stack spacing={15}>
                        <Avatar
                            alignSelf={'center'}
                            size={'xl'}
                            src={
                                `https://randomuser.me/api/portraits/${convertGenderEntry(props.customerGender)}/${props.customerId}.jpg`
                            }
                            css={{
                                border: '2px solid white',
                            }}
                        />
                        <FormTextInput
                            label='Name'
                            name='name'
                            type='text'
                        />
                        <FormTextInput
                            label='Email'
                            name='email'
                            type='text'
                        />
                        <FormTextInput
                            label='Age'
                            name='age'
                            type='number'
                        />
                        <FormSelectInput label="Gender" name="gender">
                            {selectGender(props.customerGender)}
                        </FormSelectInput>

                        {/*Button only works if form IsValid and is NOT submitting*/}
                        {/*Dirty is a value that lets us know if any values where changed*/}
                        <Button isDisabled={!dirty || isSubmitting} type="submit"
                                colorScheme={"green"}>Save</Button>
                    </Stack>
                </Form>
            )}
        }
    </Formik>
    </>
    )
}

export default  ModifyCustomerForm