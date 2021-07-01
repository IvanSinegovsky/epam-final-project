package by.epam.carrentalapp.dao;

import by.epam.carrentalapp.bean.entity.PromoCode;

import java.util.List;
import java.util.Optional;

public interface PromoCodeDao {
    String PROMO_CODE_ID_COLUMN_NAME = "promo_code_id";
    String PROMO_CODE_COLUMN_NAME = "promo_code";
    String DISCOUNT_COLUMN_NAME = "discount";
    String IS_ACTIVE_COLUMN_NAME = "is_active";

    Optional<PromoCode> findByPromoCode(String promoCode);
    Optional<Long> save(PromoCode promoCodeToSave);
    void setValuesNonActiveByPromoCodes(List<String> promoCodes);
    List<PromoCode> findAll();
    Optional<PromoCode> findByPromoCodeId(Long promoCodeId);
}
