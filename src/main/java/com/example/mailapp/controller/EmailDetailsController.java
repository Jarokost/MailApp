package com.example.mailapp.controller;

import com.example.mailapp.EmailManager;
import com.example.mailapp.controller.services.MessageRendererService;
import com.example.mailapp.model.EmailMessage;
import com.example.mailapp.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsController extends BaseController implements Initializable {


    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "\\Downloads\\";

    @FXML
    private HBox downloads;

    @FXML
    private Label senderLabel;

    @FXML
    private Label subjectLabel;

    @FXML
    private WebView webView;

    @FXML
    private Label attachmentLabel;

    public EmailDetailsController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachements(emailMessage);

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }

    private void loadAttachements(EmailMessage emailMessage) {
        if(emailMessage.hasAttachments()){
            for (MimeBodyPart mimeBodyPart: emailMessage.getAttachementList()){
                AttachmentButton button = null;
                try {
                    button = new AttachmentButton(mimeBodyPart);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                downloads.getChildren().add(button);
            }
        } else {
            attachmentLabel.setText("");
        }
    }

    private class AttachmentButton extends Button {

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentButton (MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            this.setOnAction( e -> downloadAttachment());
        }

        private void downloadAttachment(){
            Service service = new Service() {
                @Override
                protected Task createTask() {
                    return new Task() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };
            service.restart();
        }

    }
}
