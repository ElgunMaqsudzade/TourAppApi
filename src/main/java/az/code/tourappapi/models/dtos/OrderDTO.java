package az.code.tourappapi.models.dtos;


import az.code.tourappapi.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private Long id;
    private String telegramIdentifier;
    private String language;
    private String tourType;
    private String addressFrom;
    private String addressTo;
    private LocalDate travelStartDate;
    private LocalDate travelEndDate;
    private String travellerCount;
    private Double budget;
    private LocalDateTime expireTime;
    private OrderStatus orderStatus;
}
