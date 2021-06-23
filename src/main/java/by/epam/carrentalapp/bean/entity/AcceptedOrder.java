package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptedOrder implements Serializable {
    private Long orderId;
    private Double bill;
    private Long orderRequestId;
    private Long carId;
    private Boolean isPaid;
    private Long adminUserAcceptedId;
    private Long userDetailsId;

    public AcceptedOrder(Double bill, Long orderRequestId, Long carId, Long adminUserAcceptedId, Long userDetailsId) {
        this.bill = bill;
        this.orderRequestId = orderRequestId;
        this.carId = carId;
        this.adminUserAcceptedId = adminUserAcceptedId;
        this.userDetailsId = userDetailsId;
    }

    /**
     * Parses array of AcceptedOrder(converted to String with {@link #toString()} method) instances
     * into ArrayList of AcceptedOrder objects using {@link #valueOf(String)}, which parses single String value
     * @param acceptedOrderStrings String array with toString() instances
     * @return parsed AcceptedOrder ArrayList
     */
    public static List<AcceptedOrder> valueOf(String[] acceptedOrderStrings) {
        List<AcceptedOrder> completedOrders = new ArrayList<>(acceptedOrderStrings.length);

        for (String completedOrderString : acceptedOrderStrings) {
            completedOrders.add(AcceptedOrder.valueOf(completedOrderString));
        }

        return completedOrders;
    }

    /**
     * Parses AcceptedOrder(converted to String with {@link #toString()} method) instance
     * into AcceptedOrder object
     * @param acceptedOrderString single AcceptedOrder converted to String value
     * @return parsed AcceptedOrder single object
     */
    public static AcceptedOrder valueOf(String acceptedOrderString) {
        String[] fields = acceptedOrderString.split(",");

        Long orderId = Long.valueOf(fields[0].substring(indexNumberAfterEqualsChar(fields[0])));
        Double bill = Double.valueOf(fields[1].substring(indexNumberAfterEqualsChar(fields[1])));
        Long orderRequestId = Long.valueOf(fields[2].substring(indexNumberAfterEqualsChar(fields[2])));
        Long carId = Long.valueOf(fields[3].substring(indexNumberAfterEqualsChar(fields[3])));
        Boolean isPaid = Boolean.valueOf(fields[4].substring(indexNumberAfterEqualsChar(fields[4])));
        Long adminUserAcceptedId = Long.valueOf(fields[5].substring(indexNumberAfterEqualsChar(fields[5])));
        Long userDetailsId = Long.valueOf(fields[6].substring(
                indexNumberAfterEqualsChar(fields[6]), fields[6].length() - 1
        ));

        return new AcceptedOrder(
                orderId, bill, orderRequestId, carId, isPaid, adminUserAcceptedId, userDetailsId
        );
    }
    /**
     * Finds begin index of single String AcceptedOrder object field
     * @param fieldKeyAndValue field title = field value parameter
     * @return value begin index
     */
    private static int indexNumberAfterEqualsChar(String fieldKeyAndValue) {
        return fieldKeyAndValue.indexOf('=') + 1;
    }
}
