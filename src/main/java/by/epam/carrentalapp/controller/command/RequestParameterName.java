package by.epam.carrentalapp.controller.command;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RequestParameterName {
    NAME("name"),
    LASTNAME("lastname"),
    EMAIL("email"),
    PASSWORD("password"),
    PASSPORT_NUMBER("passport_number"),
    SELECTED_ORDER_REQUESTS("selectedOrderRequests"),
    REJECTION_REASON("rejectionReason");

    private final String name;
}
