package by.epam.carrentalapp.controller.command;

import by.epam.carrentalapp.controller.command.admin.*;
import by.epam.carrentalapp.controller.command.customer.*;
import by.epam.carrentalapp.controller.command.error.ErrorCommand;
import by.epam.carrentalapp.controller.command.guest.*;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.JavaConfig;
import by.epam.carrentalapp.ioc.ObjectFactory;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains {@link Command} implementation values by {@link CommandTitle} key
 */
@NoArgsConstructor
public class CommandProvider {
    private static final Map<String, Command> nameToCommand = new HashMap<>();

    static {
        JavaConfig applicationConfig = new JavaConfig("by.epam.carrentalapp");
        ApplicationContext context = new ApplicationContext(applicationConfig);
        ObjectFactory objectFactory = new ObjectFactory();
        ApplicationContext.setFactory(objectFactory);

        nameToCommand.put(CommandTitle.LOGIN.name(), new LoginCommand());
        nameToCommand.put(CommandTitle.REGISTER.name(), new RegisterCommand());
        nameToCommand.put(CommandTitle.HOME.name(), new HomeCommand());
        nameToCommand.put(CommandTitle.CAR_CATALOG.name(), new CarCatalogCommand());
        nameToCommand.put(CommandTitle.ERROR.name(), new ErrorCommand());
        nameToCommand.put(CommandTitle.ORDER_REQUEST_LIST.name(), new OrderRequestListCommand());
        nameToCommand.put(CommandTitle.LOGOUT.name(), new LogoutCommand());
        nameToCommand.put(CommandTitle.ACCEPT_ORDER.name(), new AcceptOrderCommand());
        nameToCommand.put(CommandTitle.REJECT_ORDER.name(), new RejectOrderCommand());
        nameToCommand.put(CommandTitle.CAR_OCCUPATION.name(), new CarOccupationCommand());
        nameToCommand.put(CommandTitle.LOGIN_AND_MAKE_ORDER.name(), new LoginAndMakeOrderCommand());
        nameToCommand.put(CommandTitle.MAKE_ORDER_REQUEST.name(), new MakeOrderRequestCommand());
        nameToCommand.put(CommandTitle.GO_TO_REGISTER.name(), new GoToRegisterCommand());
        nameToCommand.put(CommandTitle.ADD_CAR.name(), new AddCarCommand());
        nameToCommand.put(CommandTitle.PROMO_CODE_LIST.name(), new PromoCodeListCommand());
        nameToCommand.put(CommandTitle.UNDO_ORDER_REQUEST.name(), new UndoOrderRequestCommand());
        nameToCommand.put(CommandTitle.CHECK_CUSTOMER_STATISTICS.name(), new CheckCustomerStatisticsCommand());
        nameToCommand.put(CommandTitle.DISABLE_PROMO_CODE.name(), new DisablePromoCodeCommand());
        nameToCommand.put(CommandTitle.ADD_PROMO_CODE.name(), new AddPromoCodeCommand());
        nameToCommand.put(CommandTitle.ACTIVE_ORDER_REQUEST_LIST.name(), new ActiveOrderRequestListCommand());
        nameToCommand.put(CommandTitle.ACTIVE_ACCEPTED_ORDER_LIST.name(), new ActiveAcceptedOrderListCommand());
        nameToCommand.put(CommandTitle.COMPLETE_ACCEPTED_ORDER.name(), new CompleteAcceptedOrderCommand());
        nameToCommand.put(CommandTitle.MAKE_REPAIR_BILL.name(), new MakeRepairBillCommand());
    }

    /**
     * @param commandTitle title converting to {@link CommandTitle} key
     * @return {@link Command} implementation values
     */
    public static Command getCommand(String commandTitle) {
        return nameToCommand.get(commandTitle.toUpperCase());
    }
}
