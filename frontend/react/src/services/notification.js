import {createStandaloneToast} from "@chakra-ui/react";
import {isClass} from "eslint-plugin-react/lib/util/ast.js";

/**
 * Instantiate a toast to put a message on screen
 */
const {toast} = createStandaloneToast()

/**
 * Creates a notification from toast and add some common parameters for all child notifications
 *
 * @param title
 * @param description
 * @param status
 */
const notification = (title, description, status) => {
    toast({
        title,
        description,
        status,
        isClosable: true,
        duration: 4000,
        position: "top"
    })
}

/**
 * Creates a success notification with the passed title and description
 *
 * @param title
 * @param description
 */
export const successNotification = (title, description) => {
    notification(
        title,
        description,
        "success",
    )
}

/**
 * Creates an error notification with the passed title and description
 * @param title
 * @param description
 */
export const errorNotification = (title, description) => {
    notification(
        title,
        description,
        "error"
    )
}
