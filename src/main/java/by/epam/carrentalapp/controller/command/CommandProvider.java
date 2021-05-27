package by.epam.carrentalapp.controller.command;

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
    }

    public Command getCommand(String commandTitle) {
        Command command = titleToCommand.get(commandTitle.toUpperCase());
        return command;
    }
}
