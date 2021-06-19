package by.epam.carrentalapp.service;

import by.epam.carrentalapp.bean.entity.PromoCode;

import java.util.List;

public interface PromoCodeService {
    void addPromoCode(PromoCode promoCode);
    void disablePromoCodes(List<String> promoCodes);
    List<PromoCode> getAllPromoCodes();
}
