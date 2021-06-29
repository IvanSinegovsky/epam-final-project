package by.epam.carrentalapp.controller.command.admin;

import by.epam.carrentalapp.bean.entity.PromoCode;
import by.epam.carrentalapp.controller.command.Command;
import by.epam.carrentalapp.controller.command.Router;
import by.epam.carrentalapp.controller.command.security.AccessManager;
import by.epam.carrentalapp.controller.command.security.RoleName;
import by.epam.carrentalapp.ioc.ApplicationContext;
import by.epam.carrentalapp.service.PromoCodeService;
import by.epam.carrentalapp.service.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPromoCodeCommand implements Command {
    private final Logger LOGGER = Logger.getLogger(AddPromoCodeCommand.class);

    private final PromoCodeService promoCodeService;

    private final String PROMO_CODE_REQUEST_PARAMETER_NAME = "promo_code";
    private final String DISCOUNT_REQUEST_PARAMETER_NAME = "discount";
    private final String EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME = "exception_message";

    public AddPromoCodeCommand() {
        promoCodeService = ApplicationContext.getObject(PromoCodeService.class);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!AccessManager.checkPermission(request, RoleName.ADMIN)) {
            request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "403");
            forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
        } else {
            String promoCode = request.getParameter(PROMO_CODE_REQUEST_PARAMETER_NAME);
            Integer discount = Integer.valueOf(request.getParameter(DISCOUNT_REQUEST_PARAMETER_NAME));

            if (promoCode == null || "".equals(promoCode)) {
                forward(Router.PROMO_CODE_LIST_FORWARD_PATH.getPath(), request, response);
            } else {
                try {
                    promoCodeService.addPromoCode(new PromoCode(
                            promoCode,
                            discount,
                            true
                    ));

                    redirect(Router.PROMO_CODE_LIST_REDIRECT_PATH.getPath(), response);
                }  catch (ServiceException e) {
                    LOGGER.error("AddPromoCodeCommand execute(...): service crashed");
                    request.setAttribute(EXCEPTION_MESSAGE_REQUEST_PARAMETER_NAME, "Invalid promo code values");
                    forward(Router.ERROR_FORWARD_PATH.getPath(), request, response);
                }
            }
        }
    }
}
