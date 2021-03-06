package by.epam.carrentalapp.service.impl.notifier.email.template;

import by.epam.carrentalapp.service.impl.notifier.email.Email;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RejectedOrderEmail implements Email {
    private String rejectionReason;

    @Override
    public String getMessageText(String customerName) {
        return "Dear "
                + customerName
                + "! Unfortunately, your order has been rejected by the reason: \""
                + rejectionReason
                + "\"";
    }
}
