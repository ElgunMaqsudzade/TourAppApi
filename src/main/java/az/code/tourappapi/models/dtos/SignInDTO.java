package az.code.tourappapi.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {
    @Email()
    @NotBlank(message = "Shouldn't be blank")
    @Size(max = 30)
    private String email;
    @NotBlank
    @Pattern(regexp = "^[\\w@#$%^&]{6,15}$",message = "Should be correct format")
    private String password;
}
