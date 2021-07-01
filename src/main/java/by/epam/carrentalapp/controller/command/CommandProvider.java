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
        nameToCommand.put(CommandTitle.LOGIN.name(), ApplicationContext.getObject(LoginCommand.class));
        nameToCommand.put(CommandTitle.REGISTER.name(), ApplicationContext.getObject(RegisterCommand.class));
        nameToCommand.put(CommandTitle.HOME.name(),  ApplicationContext.getObject(HomeCommand.class));
        nameToCommand.put(CommandTitle.CAR_CATALOG.name(), ApplicationContext.getObject(CarCatalogCommand.class));
        nameToCommand.put(CommandTitle.ERROR.name(), ApplicationContext.getObject(ErrorCommand.class));
        nameToCommand.put(CommandTitle.ORDER_REQUEST_LIST.name(), ApplicationContext.getObject(OrderRequestListCommand.class));
        nameToCommand.put(CommandTitle.LOGOUT.name(), ApplicationContext.getObject(LogoutCommand.class));
        nameToCommand.put(CommandTitle.ACCEPT_ORDER.name(), ApplicationContext.getObject(AcceptOrderCommand.class));
        nameToCommand.put(CommandTitle.REJECT_ORDER.name(), ApplicationContext.getObject(RejectOrderCommand.class));
        nameToCommand.put(CommandTitle.CAR_OCCUPATION.name(), ApplicationContext.getObject(CarOccupationCommand.class));
        nameToCommand.put(CommandTitle.MAKE_ORDER_REQUEST.name(), ApplicationContext.getObject(MakeOrderRequestCommand.class));
        nameToCommand.put(CommandTitle.GO_TO_REGISTER.name(), ApplicationContext.getObject(GoToRegisterCommand.class));
        nameToCommand.put(CommandTitle.ADD_CAR.name(), ApplicationContext.getObject(AddCarCommand.class));
        nameToCommand.put(CommandTitle.PROMO_CODE_LIST.name(), ApplicationContext.getObject(PromoCodeListCommand.class));
        nameToCommand.put(CommandTitle.UNDO_ORDER_REQUEST.name(), ApplicationContext.getObject(UndoOrderRequestCommand.class));
        nameToCommand.put(CommandTitle.CHECK_CUSTOMER_STATISTICS.name(), ApplicationContext.getObject(CheckCustomerStatisticsCommand.class));
        nameToCommand.put(CommandTitle.DISABLE_PROMO_CODE.name(), ApplicationContext.getObject(DisablePromoCodeCommand.class));
        nameToCommand.put(CommandTitle.ADD_PROMO_CODE.name(), ApplicationContext.getObject(AddPromoCodeCommand.class));
        nameToCommand.put(CommandTitle.ACTIVE_ORDER_REQUEST_LIST.name(), ApplicationContext.getObject(ActiveOrderRequestListCommand.class));
        nameToCommand.put(CommandTitle.ACTIVE_ACCEPTED_ORDER_LIST.name(), ApplicationContext.getObject(ActiveAcceptedOrderListCommand.class));
        nameToCommand.put(CommandTitle.COMPLETE_ACCEPTED_ORDER.name(), ApplicationContext.getObject(CompleteAcceptedOrderCommand.class));
        nameToCommand.put(CommandTitle.MAKE_REPAIR_BILL.name(), ApplicationContext.getObject(MakeRepairBillCommand.class));
    }

    /**
     * @param commandTitle title converting to {@link CommandTitle} key
     * @return {@link Command} implementation values
     */
    public static Command getCommand(String commandTitle) {
        return nameToCommand.get(commandTitle.toUpperCase());
    }
}
