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
    REJECTION_REASON("rejectionReason"),
    ALL_CARS("all_cars"),
    EXCEPTION_MESSAGE("exception_message"),
    ORDER_REQUEST_INFOS("order_request_infos"),
    CAR_ID_TO_CHECK("car_id_to_check"),
    CAR_OCCUPATION("car_occupation");

    private final String name;
}
