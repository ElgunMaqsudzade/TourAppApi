package az.code.tourappapi.models.dtos;

import az.code.tourappapi.annotations.Matches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Matches(
        field = "repeatPassword",
        fieldMatch = "newPassword",
        message = "Passwords do not match!"
)
public class UpdatePasswordDTO {
    @NotBlank
    private String oldPassword;
    @Pattern(regexp = "^[\\w@#$%^&]{6,15}$", message = "Should be correct format")
    private String newPassword;
    @NotBlank
    private String repeatPassword;
}
