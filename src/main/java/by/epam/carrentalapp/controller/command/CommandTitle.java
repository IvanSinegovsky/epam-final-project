package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.security.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandTitle {
    HOME(RoleName.ALL),
    LOGIN(RoleName.ALL),
    ERROR(RoleName.ALL),
    REGISTER(RoleName.ALL),
    CAR_CATALOG(RoleName.ALL),
    GO_TO_REGISTER(RoleName.ALL),
    CAR_OCCUPATION(RoleName.ALL),
    LOGIN_AND_MAKE_ORDER(RoleName.ALL),

    LOGOUT(RoleName.CUSTOMER),
    MAKE_ORDER_REQUEST(RoleName.CUSTOMER),
    UNDO_ORDER_REQUEST(RoleName.CUSTOMER),
    ACTIVE_ORDER_REQUEST_LIST(RoleName.CUSTOMER),

    ADD_CAR(RoleName.ADMIN),
    ACCEPT_ORDER(RoleName.ADMIN),
    REJECT_ORDER(RoleName.ADMIN),
    ADD_PROMO_CODE(RoleName.ADMIN),
    PROMO_CODE_LIST(RoleName.ADMIN),
    MAKE_REPAIR_BILL(RoleName.ADMIN),
    DISABLE_PROMO_CODE(RoleName.ADMIN),
    ORDER_REQUEST_LIST(RoleName.ADMIN),
    COMPLETE_ACCEPTED_ORDER(RoleName.ADMIN),
    CHECK_CUSTOMER_STATISTICS(RoleName.ADMIN),
    ACTIVE_ACCEPTED_ORDER_LIST(RoleName.ADMIN);

    private final RoleName permission;
}
