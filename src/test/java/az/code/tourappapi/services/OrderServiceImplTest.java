package az.code.tourappapi.services;

import az.code.tourappapi.components.SchedulerExecutor;
import az.code.tourappapi.components.specs.interfaces.OrderSpec;
import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.services.interfaces.OrderService;
import az.code.tourappapi.utils.ModelMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private OrderSpec orderSpec;
    @Mock
    private ModelMapperUtil mapperUtil;
    @Mock
    private AppConfig config;
    @Mock
    private SchedulerExecutor sch;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderDAO, orderSpec, mapperUtil, config, sch);
    }

    @Test
    void create() {
        Long id = 1L;
        //given
        Order order = Order.builder().id(id).build();
        //when
        orderDAO.save(order);
        //then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderDAO).save(orderArgumentCaptor.capture());
        Order captured = orderArgumentCaptor.getValue();
        assertThat(captured.getId()).isEqualTo(order.getId());
    }

}