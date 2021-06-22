package by.epam.carrentalapp.service;

import lombok.Getter;

@Getter
public class PaginationService {
    private int elementsOnPage;

    public PaginationService(int elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    public int offset(int currentPage) {
        return elementsOnPage * (currentPage - 1);
    }

    public int lastPage(int pages, int totalRecords) {
        return pages * elementsOnPage < totalRecords ? pages + 1 : pages;
    }

    public int pages(int totalRecords) {
        return totalRecords / elementsOnPage;
    }

    public int beginListIndex(int currentPage) {
        return (currentPage - 1) * elementsOnPage;
    }

    public int lastListIndex(int currentPage) {
        return currentPage  * elementsOnPage;
    }
}
