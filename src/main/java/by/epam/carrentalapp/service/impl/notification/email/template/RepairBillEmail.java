package by.epam.carrentalapp.service.impl.notification.email.template;

import by.epam.carrentalapp.service.impl.notification.email.Email;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RepairBillEmail implements Email {
    private String accidentComment;
    private Double bill;

    @Override
    public String getMessageText(String customerName) {
        return "Dear "
                + customerName
                + "! PICKUP notifies you about the accident during the order: \""
                + accidentComment
                + "\". \n You have to pay "
                + bill
                + "BYN for repair.";
    }
}
