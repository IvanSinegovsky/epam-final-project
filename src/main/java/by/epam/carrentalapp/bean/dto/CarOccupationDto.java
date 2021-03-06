package by.epam.carrentalapp.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarOccupationDto {
    private Long carId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
