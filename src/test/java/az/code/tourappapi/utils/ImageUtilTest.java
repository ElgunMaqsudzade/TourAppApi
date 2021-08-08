package az.code.tourappapi.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageUtilTest {

    @Test
    void addText() {
    }

    @Test
    void toCapitalize() {
        //given
        String str = "hey";
        String capitalized = "Hey";
        //when
        String result = ImageUtil.toCapitalize(str);
        //expected
        assertThat(result).isEqualTo(capitalized);
    }

    @Test
    void toFormat() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2000, 6, 20, 0, 0);
        //when
        String result = ImageUtil.toFormat(localDateTime);
        //expected
        assertThat(result).isEqualTo("2000-06-20");
    }
}