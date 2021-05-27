package by.epam.carrentalapp.controller.command;

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
    private final Map<String, Command> titleToCommand = new HashMap<>();

    {
        titleToCommand.put(CommandTitle.LOGIN.name(), new LoginCommand());
        titleToCommand.put(CommandTitle.REGISTER.name(), new RegisterCommand());
        titleToCommand.put(CommandTitle.HOME.name(), new HomeCommand());
        titleToCommand.put(CommandTitle.CAR_CATALOG.name(), new CarCatalogCommand());
        titleToCommand.put(CommandTitle.ERROR.name(), new ErrorCommand());
    }

    public Command getCommand(String commandTitle) {
        Command command = titleToCommand.get(commandTitle.toUpperCase());
        return command;
    }
}
