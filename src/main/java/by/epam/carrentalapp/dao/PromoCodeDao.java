package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.PromoCode;

import java.util.Optional;

public interface PromoCodeDao {
    String PROMO_CODE_ID_COLUMN_NAME = "promo_code_id";
    String PROMO_CODE_COLUMN_NAME = "promo_code";
    String DISCOUNT_COLUMN_NAME = "discount";
    String IS_ACTIVE_COLUMN_NAME = "is_active";

    Optional<PromoCode> findByPromoCode(String promoCode);
}
