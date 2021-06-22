package by.epam.carrentalapp.service.impl.rate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RateEvent {
    COMPLETE_ORDER(1.2),
    UNDO_ORDER_REQUEST(0.95),
    CAR_ACCIDENT(0.7);

    private final double coefficient;
}
