package az.code.tourappapi.utils;

import az.code.tourappapi.daos.interfaces.OrderDAO;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.OrderDTO;
import az.code.tourappapi.services.interfaces.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelMapperUtilTest {
    @Autowired
    private ModelMapperUtil mapperUtil;


    @Test
    void mapList() {
        //given
        Order order = Order.builder().language("english").build();
        //when
        List<OrderDTO> orderDTO = mapperUtil.mapList(Arrays.asList(order), OrderDTO.class);
        //expected
        assertThat(orderDTO).isNotEmpty();
        assertThat(orderDTO.stream().findAny().get().getLanguage()).isEqualTo(order.getLanguage());
    }

    @Test
    void map() {
        //given
        Order order = Order.builder().language("english").build();
        //when
        OrderDTO orderDTO = mapperUtil.map(order, OrderDTO.class);
        //expected
        assertThat(order.getLanguage()).isEqualTo(orderDTO.getLanguage());
    }
}