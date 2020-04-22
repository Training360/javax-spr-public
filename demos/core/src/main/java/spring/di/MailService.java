package spring.di;

import org.springframework.stereotype.Service;

@Service
public class MailService {

    public void sendMail() {
        System.out.println("sending mail");
    }
}
