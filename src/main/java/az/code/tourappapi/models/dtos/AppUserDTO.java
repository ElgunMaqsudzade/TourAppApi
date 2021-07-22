package az.code.tourappapi.models.dtos;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDTO {
    @NotBlank
    @Size(max = 30)
    private String company;
    @NotBlank
    @Size(max = 30)
    private String firstName;
    @NotBlank
    @Size(max = 30)
    private String lastName;
    @Max(value = 10)
    private Integer tin;
    @Email()
    @NotBlank(message = "Shouldn't be blank")
    @Size(max = 30)
    private String email;
    @Pattern(regexp = "^[\\w@#$%^&]{6,15}$")
    private String password;
}
