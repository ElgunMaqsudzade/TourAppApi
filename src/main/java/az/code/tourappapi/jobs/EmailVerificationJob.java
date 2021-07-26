package az.code.tourappapi.jobs;

import az.code.tourappapi.components.EmailSender;
import az.code.tourappapi.daos.interfaces.TokenDAO;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Token;
import az.code.tourappapi.models.dtos.TimerInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.*;

import java.util.Locale;
import java.util.ResourceBundle;

@RequiredArgsConstructor
@PersistJobDataAfterExecution
public class EmailVerificationJob implements Job {
    private final EmailSender sender;
    private static final String messages = "messages";
    private static final String verifyToken = "verifyToken";
    private static final String subject = "subject";

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        TimerInfoDTO<Token> infoDTO = (TimerInfoDTO<Token>) ctx.getJobDetail().getJobDataMap().get(EmailVerificationJob.class.getSimpleName());
        Token token = infoDTO.getData();
        AppUser user = token.getAppUser();
        ResourceBundle rb = ResourceBundle.getBundle(messages, Locale.ENGLISH);

        sender.sendEmail(user.getEmail(),
                rb.getString(subject) + " " + user.getFirstName(),
                rb.getString(verifyToken)+ " " + token.getToken());
    }
}
