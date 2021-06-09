package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.admin.OrderListCommand;
import by.epam.carrentalapp.controller.command.customer.ChooseCarCommand;
import by.epam.carrentalapp.controller.command.customer.LogoutCommand;
import by.epam.carrentalapp.controller.command.error.ErrorCommand;
import by.epam.carrentalapp.controller.command.guest.CarCatalogCommand;
import by.epam.carrentalapp.controller.command.guest.HomeCommand;
import by.epam.carrentalapp.controller.command.guest.LoginCommand;
import by.epam.carrentalapp.controller.command.guest.RegisterCommand;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class CommandProvider {
    private static final Map<String, Command> nameToCommand = new HashMap<>();

    static {
        nameToCommand.put(CommandTitle.LOGIN.name(), new LoginCommand());
        nameToCommand.put(CommandTitle.REGISTER.name(), new RegisterCommand());
        nameToCommand.put(CommandTitle.HOME.name(), new HomeCommand());
        nameToCommand.put(CommandTitle.CAR_CATALOG.name(), new CarCatalogCommand());
        nameToCommand.put(CommandTitle.ERROR.name(), new ErrorCommand());
        nameToCommand.put(CommandTitle.ORDER_REQUEST_LIST.name(), new OrderListCommand());
        nameToCommand.put(CommandTitle.CHOOSE_CAR.name(), new ChooseCarCommand());
        nameToCommand.put(CommandTitle.LOGOUT.name(), new LogoutCommand());
    }

    public static Command getCommand(String commandTitle) {
        return nameToCommand.get(commandTitle.toUpperCase());
    }
}
