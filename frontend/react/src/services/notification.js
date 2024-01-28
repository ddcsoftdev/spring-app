import {createStandaloneToast} from "@chakra-ui/react";
import {isClass} from "eslint-plugin-react/lib/util/ast.js";

const { toast } = createStandaloneToast()

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

export const successNotification = (title, description) => {
    notification(
        title,
        description,
        "success",
    )
}

export const errorNotification = (title, description) => {
    notification(
        title,
        description,
        "error"
    )
}
