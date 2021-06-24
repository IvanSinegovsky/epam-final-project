package by.epam.carrentalapp.service.impl.notifier.email;

public interface Email {
    String MESSAGE_SUBJECT = "PICKUP request";

    default String getMessageSubject() {
        return MESSAGE_SUBJECT;
    }
    String getMessageText(String customerName);
}
