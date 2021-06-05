package by.epam.carrentalapp.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandTitle {
    LOGIN("ALL"),
    REGISTER("ALL"),
    HOME("ALL"),
    CAR_CATALOG("ALL"),
    ERROR("ALL"),
    ORDER_LIST_COMMAND("ADMIN");

    private final String rolePermission;
}
