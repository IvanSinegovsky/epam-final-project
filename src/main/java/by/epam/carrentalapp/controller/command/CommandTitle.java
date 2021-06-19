package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.security.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.management.relation.Role;

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
    CHOOSE_CAR(RoleName.CUSTOMER),
    MAKE_ORDER_REQUEST(RoleName.CUSTOMER),
    UNDO_ORDER_REQUEST(RoleName.CUSTOMER),

    ADD_CAR(RoleName.ADMIN),
    ACCEPT_ORDER(RoleName.ADMIN),
    REJECT_ORDER(RoleName.ADMIN),
    PROMO_CODE_LIST(RoleName.ADMIN),
    DISABLE_PROMO_CODE(RoleName.ADMIN),
    ORDER_REQUEST_LIST(RoleName.ADMIN),
    CHECK_CUSTOMER_STATISTICS(RoleName.ADMIN);

    private final RoleName permission;
}
