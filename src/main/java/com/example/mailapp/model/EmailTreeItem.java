package com.example.mailapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadedMessagesCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public ObservableList<EmailMessage> getEmailMessages(){
        return emailMessages;
    }

    public void addEmail(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(emailMessage);
        System.out.println("added to: " + name + " " + message.getSubject());
    }

    public void addEmailToTop(Message message) throws MessagingException {
        EmailMessage emailMessage = fetchMessage(message);
        emailMessages.add(0, emailMessage);
        System.out.println("added to: " + name + " " + message.getSubject());
    }

    private EmailMessage fetchMessage(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        Address[] recipients = message.getAllRecipients();
        java.lang.String recipient;
        if(recipients.length == 0) {
            recipient = (java.lang.String) this.name;
        } else {
            recipient = recipients[0].toString();
        }
        java.lang.String sender = message.getFrom()[0].toString();
        java.lang.String[] senderSplit = sender.split("\\s+");
        sender = senderSplit[1];
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                sender, //message.getFrom()[0].toString(),
                recipient, //message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        if(!messageIsRead){
            incrementMessagesCount();
        }
        return emailMessage;
    }

    public void incrementMessagesCount(){
        unreadedMessagesCount++;
        updateName();
    }
    private void updateName() {
        if(unreadedMessagesCount > 0){
            this.setValue((String)(name + "(" + unreadedMessagesCount + ")"));
        } else {
            this.setValue(name);
        }
    }
}

