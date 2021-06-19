package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.service.PromoCodeService;
import by.epam.carrentalapp.service.ServiceException;
import by.epam.carrentalapp.service.impl.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PromoCodeListCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(PromoCodeListCommand.class);
    private final PromoCodeService promoCodeService;

    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";
    private final String ALL_PROMO_CODES_REQUEST_PARAMETER_NAME = "all_promo_codes";

    public PromoCodeListCommand() {
        promoCodeService = ServiceProvider.getPromoCodeService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            try {
                List<PromoCode> allPromoCodes = promoCodeService.getAllPromoCodes();

                request.setAttribute(ALL_PROMO_CODES_REQUEST_PARAMETER_NAME, allPromoCodes);
                forward(Router.PROMO_CODE_LIST_FORWARD_PATH.getPath(), request, response);
            } catch (ServiceException e) {
                LOGGER.error("PromoCodeListCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot get promo codes list, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
