package by.epam.carrentalapp.service.impl;

import by.epam.carrentalapp.service.PaginationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class PaginationServiceImpl implements PaginationService {
    private int elementsOnPage;
    private int elementsTotal;

    public int getOffset(int currentPage) {
        return elementsOnPage * (currentPage - 1);
    }

    public int getLastPageNumber() {
        int pages = elementsTotal / elementsOnPage;
        return pages * elementsOnPage < elementsTotal ? pages + 1 : pages;
    }

    public int getPagesQuantity() {
        return elementsTotal / elementsOnPage;
    }

    public int getLimit() {
        return elementsOnPage;
    }
}
