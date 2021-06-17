package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.admin.AcceptOrderCommand;
import by.epam.carrentalapp.controller.command.admin.OrderRequestListCommand;
import by.epam.carrentalapp.controller.command.admin.RejectOrderCommand;
import by.epam.carrentalapp.controller.command.customer.CarOccupationCommand;
import by.epam.carrentalapp.controller.command.customer.ChooseCarCommand;
import by.epam.carrentalapp.controller.command.customer.LogoutCommand;
import by.epam.carrentalapp.controller.command.customer.MakeOrderRequestCommand;
import by.epam.carrentalapp.controller.command.error.ErrorCommand;
import by.epam.carrentalapp.controller.command.guest.*;
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
        nameToCommand.put(CommandTitle.ORDER_REQUEST_LIST.name(), new OrderRequestListCommand());
        nameToCommand.put(CommandTitle.CHOOSE_CAR.name(), new ChooseCarCommand());
        nameToCommand.put(CommandTitle.LOGOUT.name(), new LogoutCommand());
        nameToCommand.put(CommandTitle.ACCEPT_ORDER.name(), new AcceptOrderCommand());
        nameToCommand.put(CommandTitle.REJECT_ORDER.name(), new RejectOrderCommand());
        nameToCommand.put(CommandTitle.CAR_OCCUPATION.name(), new CarOccupationCommand());
        nameToCommand.put(CommandTitle.LOGIN_AND_MAKE_ORDER.name(), new LoginAndMakeOrderCommand());
        nameToCommand.put(CommandTitle.MAKE_ORDER_REQUEST.name(), new MakeOrderRequestCommand());
    }

    public static Command getCommand(String commandTitle) {
        return nameToCommand.get(commandTitle.toUpperCase());
    }
}
