package az.code.tourappapi.daos;

import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.repos.AppUserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserDAOImplTest {
    @Autowired
    AppUserDAO userDAO;

    @Test
    void ifStatementFindById() {
        Long id = 1L;
        //given
        AppUserRepo repo = Mockito.mock(AppUserRepo.class);
        when(repo.findById(id)).thenReturn(Optional.empty());
        //expected
        assertThatThrownBy(() -> userDAO.find(id))
                .isInstanceOf(DataNotFound.class);
    }

    @Test
    void findById() {
        //given
        Long id = 1L;
        String email = "emaqsudzade@mail.ru";
        String firstname = "firstname";
        String lastname = "lastname";
        String company = "company";
        AppUser user = AppUser.builder()
                .id(id)
                .company(company)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .build();
        userDAO.save(user);
        //when
        AppUser appUser = userDAO.find(id);
        //expected
        assertThat(appUser.getId()).isEqualTo(id);
    }


    @Test
    void ifStatementFindByEmail() {
        String email = "emaqsudzade@mail.ru";
        //given
        AppUserRepo repo = Mockito.mock(AppUserRepo.class);
        when(repo.findByEmail(email)).thenReturn(Optional.empty());
        //expected
        assertThatThrownBy(() -> userDAO.find(email))
                .isInstanceOf(DataNotFound.class);
    }
}