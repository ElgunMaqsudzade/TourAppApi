package az.code.tourappapi.daos;

import az.code.tourappapi.components.specs.interfaces.ClientSpec;
import az.code.tourappapi.daos.interfaces.ClientDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.repos.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientDAOImplTest {
    @Mock
    private ClientRepo clientRepo;
    @Mock
    private ClientSpec clientSpec;
    private ClientDAO clientDAO;


    @BeforeEach
    void init() {
        clientDAO = new ClientDAOImpl(clientRepo, clientSpec);
    }

    @Test
    void findIfStatement() {;
        //when
        given(clientRepo.findAll(any(Specification.class))).willReturn(Collections.emptyList());
        //expected
        assertThatThrownBy(() -> clientDAO.find(1L, 2L))
                .isInstanceOf(DataNotFound.class);
    }
}