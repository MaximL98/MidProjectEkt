package controllers;

import java.io.IOException;

import client.ClientController;
import client.ClientUI;
import common.InactivityChecker;
import common.WindowStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SalesManagerController {

    @FXML
    private Button btnAddNewPromotion;

    @FXML
    private Button btnEditActivePromotions;

    @FXML
    private Button btnLogout;
    @FXML
    private Text txtSalesManager;


    @FXML
    private void addNewPromotionHandler(ActionEvent event) throws IOException {
    	InactivityChecker inactivityChecker = new InactivityChecker(300000);//importent
    	inactivityChecker.updateActivityTime();
        // Get the current stage
        Stage stage = new Stage();
        WindowStarter.createWindow(stage, new Object(), "/gui/PromotionEditing.fxml", null, "Promotion Editing");
        stage.setOnCloseRequest(action -> {
        	System.out.println("Pressed the X button.");
        	System.exit(0);
        });
        stage.show();
        ((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
        Thread inactivityCheckerThread = new Thread(inactivityChecker);//importent
        inactivityCheckerThread.start();
        

    }
    

    @FXML
    private void editActivePromotionsHandler(ActionEvent event) {
    	InactivityChecker inactivityChecker = new InactivityChecker(300000);//importent
    	inactivityChecker.updateActivityTime();
        // Get the current stage
        Stage stage = new Stage();
        WindowStarter.createWindow(stage, new Object(), "/gui/EditActivePromotions.fxml", null, "Promotion Editing");
        stage.setOnCloseRequest(action -> {
        	System.out.println("Pressed the X button.");
        	System.exit(0);
        });
        stage.show();
        ((Stage) ((Node)event.getSource()).getScene().getWindow()).close();
        Thread inactivityCheckerThread = new Thread(inactivityChecker);//importent
        inactivityCheckerThread.start();
        
    }

    @FXML
    private void logoutHandler(ActionEvent event) throws Exception {
    	System.out.println("Sales manager Has Exited The Academic Tool");
    	ClientUI.clientController.client.closeConnection();
    	System.exit(0);
}
    
    @FXML
    public void initialize() {
    	txtSalesManager.setText("Hello, " + ClientController.getCurrentSystemUser().getFirstName() + "!");
    	txtSalesManager.setLayoutX(400-(txtSalesManager.minWidth(0)/2));
    }
}

