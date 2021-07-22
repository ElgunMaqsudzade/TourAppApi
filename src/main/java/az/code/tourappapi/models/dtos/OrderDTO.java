package az.code.tourappapi.models.dtos;


import az.code.tourappapi.models.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String telegramIdentifier;
    private String language;
    private String tourType;
    private String addressFrom;
    private String addressTo;
    private LocalTime travelStartDate;
    private LocalTime travelEndDate;
    private String travellerCount;
    private Double budget;
    private LocalDateTime expireTime;
    private OrderStatus orderStatus = OrderStatus.NEW;
}
