package by.epam.carrentalapp.controller.command.guest;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.factory.CommandFactory;

import java.util.Optional;

public class CarCatalogCommandFactory  implements CommandFactory {
    @Override
    public Optional<Command> createCommand() {
        return null;
    }
}
