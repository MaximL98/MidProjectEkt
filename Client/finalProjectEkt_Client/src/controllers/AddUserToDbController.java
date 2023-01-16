package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import client.ClientController;
import client.ClientUI;
import common.SCCP;
import common.ServerClientRequestTypes;
import common.WindowStarter;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Role;
import logic.SystemUser;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;

public class AddUserToDbController {

    @FXML
    private Button btnConnect;

    @FXML
    private TextField txtCreditCard;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtUsername;

	@FXML Text lblStatus;

	@FXML TextField txtRole;

	@FXML Button btnAdd;
	
	@FXML Button btnBack;

    @FXML
    private Label lblCreditCard;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhone;

    @FXML
    private Label lblUsername;
	
    @FXML
    private Label lblRole;

	@FXML ComboBox<Role> cmbRole;
    

	@FXML
	private void initialize() {
		Role[] roles = new Role[] {Role.CEO, Role.CUSTOMER, Role.DELIVERY_WORKER, Role.DIVISION_MANAGER, Role.INVENTORY_WORKER, 
				Role.LOGISTICS_EMPLOYEE, Role.LOGISTICS_MANAGER, Role.REGIONAL_MANAGER, Role.SALES_MANAGER, 
				Role.SALES_WORKER, Role.SERVICE_REPRESENTATIVE, Role.SUBSCRIBER, Role.SUBSCRIBER_20DISCOUNT, 
				Role.UNAPPROVED_CUSTOMER, Role.UNAPPROVED_SUBSCRIBER};
		cmbRole.getItems().addAll(Arrays.asList(roles));
		cmbRole.setValue(Role.UNAPPROVED_CUSTOMER);
		
	}
	
	@FXML
    void getAddUserToDB(ActionEvent event) {
    	Integer id;
		lblStatus.setText("Checking input");
    	SCCP preparedMessage = new SCCP();
    	if(validFieldInput()) {
    		// Rotem added: check if username already exists!
    		ClientUI.clientController.accept(new SCCP(ServerClientRequestTypes.SELECT, 
    				new Object[] {"systemuser", 
    								true, "username",
    								true, "username='"+txtUsername.getText()+"'",
    								false, null}));
    		@SuppressWarnings("unchecked")
			ArrayList<ArrayList<Object>> res = (ArrayList<ArrayList<Object>>) ClientController.responseFromServer.getMessageSent();
    		if(res.size() != 0) {
        		// show a pop up that lets the user know he has no open orders. return user to previous page!
        		Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setTitle("User name taken!");
                alert.setHeaderText("User name="+txtUsername.getText()+" is already taken, use another one.");
                alert.setContentText("Return to form.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                	System.out.println("Returning to form (add system user)");
                }
            	txtUsername.setText("");
            	return;
    		}
    		
    		try {
    		id = Integer.valueOf(txtID.getText());
    		}catch(NumberFormatException ex) {
    			lblStatus.setText("Invalid ID input (too long).");
    			return;
    		}
    		// set message accordingly
    		preparedMessage.setRequestType(ServerClientRequestTypes.ADD);
    		// first field is table name - users here
    		Object[] fill = new Object[3];
    		fill[0] = "systemuser"; // add to table "systemusers" (hard code it elsewhere)
    		fill[1] = false; // add only 1
    		fill[2] = new Object[] {new SystemUser(id, txtFirstName.getText(), txtLastName.getText(), 
    				txtPhoneNumber.getText(), txtEmail.getText(), txtCreditCard.getText(), txtUsername.getText(), txtPassword.getText(), cmbRole.getValue())};
    		preparedMessage.setMessageSent(fill);
    		
    		// send to server
    		ClientUI.clientController.accept(preparedMessage);
    		
    		// check comm for answer:
    		if(ClientController.responseFromServer.getRequestType().equals(ServerClientRequestTypes.ACK)) {
    			// add test that response.messageSent is the array we had in fill[2] (SAME OBJECT)
    			lblStatus.setText("Successfully added a " + cmbRole.getValue().toString().toLowerCase()+"!");
    			// clean all fields:
    			txtID.setText("");
    			txtFirstName.setText("");
    			txtLastName.setText("");
    			txtPhoneNumber.setText("");
    			txtEmail.setText("");
    			txtCreditCard.setText("");
    			txtUsername.setText("");
    			txtPassword.setText("");
    			//txtRole.setText("");
    			System.out.println("haveBalance = " + haveBalance());
    			if(haveBalance()) {
    				System.out.println("Adding balance to " + id);
	    			SCCP addBalance = new SCCP();
	    			addBalance.setRequestType(ServerClientRequestTypes.ADD);
		    		// first field is table name - users here
		    		Object[] fillBalance = new Object[3];
		    		fillBalance[0] = "customer_balance"; // add to table "customer_balance" (hard code it elsewhere)
		    		fillBalance[1] = false; // add only 1
		    		fillBalance[2] = new Object[] {"(" + id + ", " + 0 + ")"};
		    		addBalance.setMessageSent(fillBalance);
		    		// send to server
		    		ClientUI.clientController.accept(addBalance);
    			}

    		}
    		else {
    			lblStatus.setText("ERROR!"); // add specifics
    		}
    		
    	}
    	else {
    		lblStatus.setText("Status: Invalid input");
    	}
    }
	
	// lblId, lblUsername, lblName, lblEmail, lblCreditCard, lblPhone
		private boolean validFieldInput() {
			boolean flag = true;
			// I forgot to check ID
			if(txtID.getText().length() < 1 || (!txtID.getText().matches("^[0-9]*$"))) {
				lblId.setText("ID must be numeric and not empty");
				flag = false;
			}
			// check username and password not empty:
			if(txtUsername.getText().length() < 1 || txtPassword.getText().length() < 1) {
				lblUsername.setText("User name and password must be non-empty");
				flag = false;
			}
			// check name is letters
			if(txtFirstName.getText().length() < 2 || !(txtFirstName.getText().matches("^[a-zA-Z]*$")) || 
					txtLastName.getText().length() < 2 || !(txtLastName.getText().matches("^[a-zA-Z]*$"))) {
				lblName.setText("First and last name must be alphabetic, non empty ");
				flag = false;
			}
			// check email is not empty
			if(!(txtEmail.getText().contains("@")) || !(txtEmail.getText().contains(".")) ||  txtEmail.getText().length() < 5) {
				lblEmail.setText("Email must be of the format x@y.z");
				flag = false;
			}
			// check credit-card is legit (why?! we need to remove credit card from user (user needs ONLY user,pass, id, role!))
			if (!(txtCreditCard.getText().matches("^[0-9.-]+$"))) {
				lblCreditCard.setText("Credit card must be numeric and non empty");
				flag = false;
			}
			// proper check (for valid companies)
			/*if(!(txtCreditCard.getText().matches("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])"
					+ "[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$")))
				return false;*/
			
			// check phone is a number (currently not letters)
			if(!(txtPhoneNumber.getText().matches("^[0-9.-]+$"))) {
				lblPhone.setText("Phone number must be numeric and non empty");
				flag = false;
			}
			/*try {
				Role.valueOf(txtRole.getText());
			}catch(Exception ex) {
				lblRole.setText("Role must be a valid role");
				flag = false;
			}*/
			
			return flag;
		}	

	@FXML
	public void getBtnBack(ActionEvent event) {
		// 
		Stage primaryStage = new Stage();
		WindowStarter.createWindow(primaryStage, this, 
				"/gui/EktDivisionManagerHomePage.fxml", null, "Ekt Division Manager", true);
		primaryStage.show();
		((Stage) ((Node)event.getSource()).getScene().getWindow()).close(); //hiding primary window
	}

	@FXML public void getCmbRole(ActionEvent event) {}
	
	private boolean haveBalance() {
		if(cmbRole.getValue() == Role.SUBSCRIBER ||
				cmbRole.getValue() == Role.SUBSCRIBER_20DISCOUNT ||
				cmbRole.getValue() == Role.UNAPPROVED_SUBSCRIBER ||
				cmbRole.getValue() == Role.UNAPPROVED_CUSTOMER ||
				cmbRole.getValue() == Role.CUSTOMER)
			return true;
		return false;
		
	}

}
