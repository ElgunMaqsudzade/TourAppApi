package az.code.tourappapi.utils;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.exceptions.DataNotFound;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUtil {
    private final AppConfig conf;

    @SneakyThrows
    public byte[] toByteArray(BufferedImage bi) {
        String extension = conf.getImage().getExtension();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, extension, baos);
        return baos.toByteArray();

    }

    @SneakyThrows
    public BufferedImage getDefaultBI() {
        String root = conf.getImage().getDefaultFile();
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(root);
        if (inputStream == null) throw new DataNotFound("Input stream not found");
        return ImageIO.read(inputStream);
    }

    @SneakyThrows
    public BufferedImage addText(BufferedImage image, String text, Color color, Integer x, Integer y) {
        return addText(image, text, color, x, y, conf.getImage().getStyle(), conf.getImage().getSize());
    }

    @SneakyThrows
    public BufferedImage addText(BufferedImage image, String text, Color color, Integer x, Integer y, Integer style, Integer size) {
        Font font = new Font("Arial", style, size);

        Graphics g = image.getGraphics();
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
        return image;
    }

    /**
     * THis method used to Capitalize strings
     *
     * @param string string which will be capitalized
     * @return capitalized string
     */
    public static String toCapitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String toFormat(LocalDateTime date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(format);
    }

}