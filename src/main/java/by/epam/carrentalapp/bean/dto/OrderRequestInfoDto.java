package by.epam.carrentalapp.bean.dto;

import by.epam.carrentalapp.bean.entity.CustomerUserDetails;
import by.epam.carrentalapp.bean.entity.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This bean is used for HTTP request/response {@link OrderRequest} data reduction with addition fields
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestInfoDto {
    private Long orderRequestId;
    private Integer customerRate;
    private String expectedCarModel;
    private LocalDateTime expectedStartTime;
    private LocalDateTime expectedEndTime;
    private Double totalCost;

    public OrderRequestInfoDto(OrderRequest orderRequest,
                               CustomerUserDetails customerUserDetails,
                               String expectedCarModel,
                               Double totalCost) {
        this.orderRequestId = orderRequest.getOrderRequestId();
        this.customerRate = customerUserDetails.getRate();
        this.expectedCarModel = expectedCarModel;
        this.expectedStartTime = orderRequest.getExpectedStartTime();
        this.expectedEndTime = orderRequest.getExpectedEndTime();
        this.totalCost = totalCost;
    }

    /**
     * Parses array of OrderRequestInfoDto(converted to String with {@link #toString()} method) instances
     * into ArrayList of OrderRequestInfoDto objects using {@link #valueOf(String)}, which parses single String value
     * @param orderRequestInfoStringArray String array with toString() instances
     * @return parsed OrderRequestInfoDto ArrayList
     */
    public static List<OrderRequestInfoDto> valueOf(String[] orderRequestInfoStringArray) {
        List<OrderRequestInfoDto> orderRequestInfoDtos = new ArrayList<>(orderRequestInfoStringArray.length);

        for (String infoString : orderRequestInfoStringArray) {
            orderRequestInfoDtos.add(OrderRequestInfoDto.valueOf(infoString));
        }

        return orderRequestInfoDtos;
    }

    /**
     * Parses OrderRequestInfoDto(converted to String with {@link #toString()} method) instance
     * into OrderRequestInfoDto object
     * @param orderRequestInfoString single OrderRequestInfoDto converted to String value
     * @return parsed OrderRequestInfoDto single object
     */
    public static OrderRequestInfoDto valueOf(String orderRequestInfoString) {
        String[] fields = orderRequestInfoString.split(",");

        Long orderRequestId = Long.valueOf(fields[0].substring(indexNumberAfterEqualsChar(fields[0])));
        Integer customerRate = Integer.valueOf(fields[1].substring(indexNumberAfterEqualsChar(fields[1])));
        String expectedCarModel = fields[2].substring(indexNumberAfterEqualsChar(fields[2]));
        LocalDateTime expectedStartTime = LocalDateTime.parse(fields[3].substring(indexNumberAfterEqualsChar(fields[3])));
        LocalDateTime expectedEndTime = LocalDateTime.parse(fields[4].substring(indexNumberAfterEqualsChar(fields[4])));
        Double totalCost = Double.valueOf(fields[5].substring(
                indexNumberAfterEqualsChar(fields[5]), fields[5].length() - 1
        ));

        return new OrderRequestInfoDto(
                orderRequestId, customerRate, expectedCarModel, expectedStartTime, expectedEndTime, totalCost
        );
    }

    /**
     * Finds begin index of single String OrderRequestInfoDto object field
     * @param fieldKeyAndValue field title = field value parameter
     * @return value begin index
     */
    private static int indexNumberAfterEqualsChar(String fieldKeyAndValue) {
        return fieldKeyAndValue.indexOf('=') + 1;
    }
}
