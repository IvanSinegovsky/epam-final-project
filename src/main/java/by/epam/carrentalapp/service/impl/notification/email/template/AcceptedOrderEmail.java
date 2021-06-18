package by.epam.carrentalapp.service.impl.notification.email.template;

import by.epam.carrentalapp.service.impl.notification.email.Email;

public class AcceptedOrderEmail implements Email {
    @Override
    public String getMessageText(String customerName) {
        return "Dear "
                + customerName
                + "! Your order has been approved.\n Now you need to pay for order and enjoy the ride.";
    }
}
