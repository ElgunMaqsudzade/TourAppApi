package az.code.tourappapi.models.dtos;

import az.code.tourappapi.annotations.Length;
import az.code.tourappapi.models.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTO implements Serializable {
    private Long id;
    @NotBlank
    @Size(max = 1000)
    private String description;
    @NotBlank
    @Size(max = 1000)
    private String locations;
    @NotNull
    private LocalDateTime travelStartDate;
    @NotNull
    private LocalDateTime travelEndDate;
    @Length(max = 9)
    @Positive
    @NotNull
    private Double price;
}