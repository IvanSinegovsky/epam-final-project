package by.epam.carrentalapp.dao.impl.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PromoCodeQuery {
    SELECT_ALL_FROM_PROMO_CODES("SELECT * FROM promo_codes;"),
    INSERT_INTO_PROMO_CODES("INSERT INTO promo_codes(promo_code, discount, is_active) VALUES (?,?,?);"),
    UPDATE_PROMO_CODES_SET_IS_ACTIVE_FALSE_WHERE_PROMO_CODE("UPDATE promo_codes SET is_active=0 WHERE promo_code=?;"),
    SELECT_ALL_FROM_PROMO_CODES_WHERE_PROMO_CODE_EQUALS("SELECT * FROM promo_codes WHERE promo_code = ?;");

    private final String query;
}
