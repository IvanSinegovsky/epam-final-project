package by.epam.carrentalapp.service;

public interface PaginationService {
    int getOffset(int currentPage);
    int getLastPageNumber();
    int getPagesQuantity();
    int getLimit();
    void setElementsOnPage(int elementsOnPage);
    void setElementsTotal(int elementsTotal);
}
