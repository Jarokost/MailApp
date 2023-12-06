package com.example.mailapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadedMessagesCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public void addEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        emailMessages.add(emailMessage);
        if(!messageIsRead){
            incrementMessagesCount();
        }
        System.out.println("added to: " + name + " " + message.getSubject());
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

