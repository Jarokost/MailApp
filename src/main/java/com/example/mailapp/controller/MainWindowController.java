package com.example.mailapp.controller;


import com.example.mailapp.EmailManager;
import com.example.mailapp.controller.services.MessageRendererService;
import com.example.mailapp.model.EmailMessage;
import com.example.mailapp.model.EmailTreeItem;
import com.example.mailapp.model.SizeInteger;
import com.example.mailapp.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private WebView emailWebView;

    @FXML
    private TableView<EmailMessage> emailsTableView;

    @FXML
    private TableColumn<EmailMessage, String> senderCol;

    @FXML
    private TableColumn<EmailMessage, String> subjectCol;

    @FXML
    private TableColumn<EmailMessage, String> recipientCol;

    @FXML
    private TableColumn<EmailMessage, SizeInteger> sizeCal;

    @FXML
    private TableColumn<EmailMessage, Date> dateCal;

    @FXML
    private TreeView<String> emailsTreeView;

    private MessageRendererService messageRendererService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void optionAction(ActionEvent event) {
        viewFactory.showOptionsWindow();
    }

    @FXML
    void addAccountAction(ActionEvent event) {
        viewFactory.showLoginWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmailsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpBoldRows();
        setUpMessageRendererService();
        setUpMessageSelection();
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.getFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }
    private void setUpEmailsTableView() {
        senderCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        recipientCol.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        sizeCal.setCellValueFactory(new PropertyValueFactory<EmailMessage, SizeInteger>("size"));
        dateCal.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e->{
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null) {
                emailsTableView.setItems(item.getEmailMessages());
            }
        });
    }

    private void setUpBoldRows() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if(item != null){
                            if(item.isRead()){
                                setStyle("");
                            } else {
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpMessageRendererService() {
        messageRendererService = new MessageRendererService(emailWebView.getEngine());
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(e->{
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if(emailMessage != null) {
                messageRendererService.setEmailMessage(emailMessage);
                messageRendererService.restart();
            }
        });
    }
}