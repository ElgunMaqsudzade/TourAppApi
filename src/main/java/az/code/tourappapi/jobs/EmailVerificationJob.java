package az.code.tourappapi.jobs;

import az.code.tourappapi.components.EmailSender;
import az.code.tourappapi.daos.interfaces.TokenDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.*;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

@RequiredArgsConstructor
@PersistJobDataAfterExecution
public class EmailVerificationJob implements Job {
    private final EmailSender sender;
    private static final String messages = "messages.properties";
    private static final String verifyToken = "verifyToken";
    private static final String subject = "subject";

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<Token> infoDTO = (TimerInfoDTO<Token>) ctx.getJobDetail().getJobDataMap().get(EmailVerificationJob.class.getSimpleName());
        Token token = infoDTO.getData();
        AppUser user = token.getAppUser();


        ClassLoader cl = this.getClass().getClassLoader();
        InputStream inputStream = cl.getResourceAsStream(messages);
        Properties props = new Properties();
        props.load(inputStream);

        sender.sendEmail(user.getEmail(),
                props.getProperty(subject) + " " + user.getFirstName(),
                props.getProperty(verifyToken)+ " " + token.getToken());
    }
}
