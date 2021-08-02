package az.code.tourappapi.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Serializable {
    Long id;
    String UUID;
    String message;
    MultipartFile file;
    byte[] fileAsBytes;
}
