package by.epam.carrentalapp.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairBill implements Serializable {
    private Long approvedOrderId;
    private Double bill;
    private String comment;
}
