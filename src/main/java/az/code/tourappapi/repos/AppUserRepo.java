package az.code.tourappapi.repos;

import az.code.tourappapi.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {
}
