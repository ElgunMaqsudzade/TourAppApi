package az.code.tourappapi.jobs;

import az.code.tourappapi.configs.AppConfig;
import az.code.tourappapi.configs.production.RabbitMQConfig;
import az.code.tourappapi.models.Offer;
import az.code.tourappapi.models.Offer_;
import az.code.tourappapi.models.dtos.MessageDTO;
import az.code.tourappapi.models.dtos.OfferDTO;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static az.code.tourappapi.utils.ImageUtil.toCapitalize;
import static az.code.tourappapi.utils.ImageUtil.toFormat;

@Component
@RequiredArgsConstructor
public class SendToSubscriberJob implements Job {
    private final RabbitTemplate temp;
    private final ImageUtil imageUtil;
    private final AppConfig conf;


    private static final String messages = "messages.properties";
    @SneakyThrows
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<Offer> infoDTO = (TimerInfoDTO<Offer>) ctx.getJobDetail().getJobDataMap().get(SendToSubscriberJob.class.getSimpleName());
        Offer offer = infoDTO.getData();

        BufferedImage image = imageUtil.getDefaultBI();
        imageUtil.addText(image, toCapitalize(Offer_.DESCRIPTION), Color.BLACK, 670, 600);
        imageUtil.addText(image, toCapitalize(Offer_.LOCATIONS), Color.BLACK, 670, 800);
        imageUtil.addText(image, "Travel dates", Color.BLACK, 670, 1000);
        imageUtil.addText(image, toCapitalize(Offer_.PRICE), Color.BLACK, 670, 1200);

        imageUtil.addText(image, offer.getDescription(), Color.BLACK, 670, 650, Font.PLAIN, 24);
        imageUtil.addText(image, offer.getLocations(), Color.BLACK, 670, 850, Font.PLAIN, 24);
        imageUtil.addText(image, toFormat(offer.getTravelStartDate()), Color.BLACK, 670, 1050, Font.PLAIN, 24);
        imageUtil.addText(image, toFormat(offer.getTravelEndDate()), Color.BLACK, 900, 1050, Font.PLAIN, 24);
        imageUtil.addText(image, String.valueOf(Math.round(offer.getPrice())), Color.BLACK, 670, 1250, Font.PLAIN, 24);

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(messages);
        Properties props = new Properties();
        props.load(inputStream);

        MessageDTO messageDTO = MessageDTO
                .builder()
                .id(offer.getId())
                .message(props.getProperty("offerCaption") + offer.getId())
                .UUID(offer.getOrder().getTelegramIdentifier())
                .fileAsBytes(imageUtil.toByteArray(image))
                .build();

        temp.convertAndSend(RabbitMQConfig.offer, messageDTO);
    }
}