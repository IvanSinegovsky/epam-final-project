package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.ioc.Autowired;
import by.epam.carrentalapp.service.PromoCodeService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DisablePromoCodeCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(DisablePromoCodeCommand.class);

    @Autowired
    private PromoCodeService promoCodeService;

    private final String SELECTED_PROMO_CODES_REQUEST_PARAMETER_NAME = "selected_promo_codes";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            String[] selectedPromoCodesStrings = request.getParameterValues(SELECTED_PROMO_CODES_REQUEST_PARAMETER_NAME);
            List<String> promoCodes = Arrays.asList(selectedPromoCodesStrings);

            try {
                promoCodeService.disablePromoCodes(promoCodes);

                redirect(Router.PROMO_CODE_LIST_REDIRECT_PATH.getPath(), response);
            } catch (ServiceException e) {
                LOGGER.error("DisablePromoCodeCommand execute(...): service crashed");
                request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Cannot disable promo code, please try again later");
                redirect(Router.ERROR_REDIRECT_PATH.getPath(), response);
            }
        }
    }
}
