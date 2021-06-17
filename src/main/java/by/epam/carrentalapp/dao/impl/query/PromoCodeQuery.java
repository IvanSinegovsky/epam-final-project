package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PromoCodeQuery {
    SELECT_ALL_FROM_PROMO_CODES_WHERE_PROMO_CODE_EQUALS("SELECT * FROM promo_codes WHERE promo_code = ?;");

    private final String query;
}
