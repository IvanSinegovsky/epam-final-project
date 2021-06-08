package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.security.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandTitle {
    LOGIN(RoleName.ALL),
    REGISTER(RoleName.ALL),
    HOME(RoleName.ALL),
    CAR_CATALOG(RoleName.ALL),
    ERROR(RoleName.ALL),
    ORDER_LIST_COMMAND(RoleName.ADMIN);

    private final RoleName permission;
}
