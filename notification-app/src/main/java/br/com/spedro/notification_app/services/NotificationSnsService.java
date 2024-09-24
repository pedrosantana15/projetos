package br.com.spedro.notification_app.services;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationSnsService {

    @Autowired
    private AmazonSNS sns;

    public void notify(String telephone, String message) {
        try {
            PublishRequest publishRequest = new PublishRequest().withMessage(message).withPhoneNumber(telephone);
            sns.publish(publishRequest);
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
        }
    }

}
