package az.code.tourappapi.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long offerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String chatId;
    private String userId;
    private String uuid;
}
