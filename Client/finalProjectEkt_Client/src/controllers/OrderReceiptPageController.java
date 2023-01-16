package controllers;

import client.ClientController;
import common.WindowStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.Properties;

public class OrderReceiptPageController {
	@FXML
	private Button btnBack;

	@FXML
	private Button btnLogout;

	@FXML
	private Text txtCustomerEmail;

	@FXML
	private Text txtOrderNumber;

	@FXML
	private Text txtOrderTotal;

	@FXML
	private Text txtBillingDate;

	@FXML
	private Text txtDeliveryAddress;

	private static String EktEmailUsername = "EkrutShop@gmail.com";
	private static String EktEmailPassword = "dimarotemmaxim";

	public void initialize() {
		txtCustomerEmail
				.setText("Order information was sent to\n" + ClientController.getCurrentSystemUser().getEmailAddress()
						+ "\n and " + ClientController.getCurrentSystemUser().getPhoneNumber());
		txtCustomerEmail.setLayoutX(200 - (txtCustomerEmail.minWidth(0) / 2));

		txtOrderNumber.setText("Order Number: " + ClientController.orderNumber);
		txtOrderNumber.setLayoutX(200 - (txtOrderNumber.minWidth(0) / 2));

		txtOrderTotal
				.setText("Order total: " + (new DecimalFormat("##.##").format(ClientController.orderTotalPrice)) + "$");
		txtOrderTotal.setLayoutX(200 - (txtOrderTotal.minWidth(0) / 2));

		System.out.println("ClientController.orderType = " + ClientController.orderType);
		if (ClientController.orderType.equals("Delivery")) {
			txtBillingDate.setText("Delivery Date: " + ClientController.orderDeliveryTime);
			txtBillingDate.setLayoutX(200 - (txtBillingDate.minWidth(0) / 2));
		}

		if (ClientController.orderType.equals("Pickup")) {
			txtBillingDate.setText("Pickup Place: " + ClientController.pickupPlace);
			txtBillingDate.setLayoutX(200 - (txtBillingDate.minWidth(0) / 2));
		}

		////////// IF WE HAVE TIME WE CAN DO IT. I THINK WE CAN DO IT BUT NOT USING A
		////////// GOOGLE ACCOUNT!
		String CustomerEmail = "dimakislitsyn96@gmail.com";
		SendEmail(CustomerEmail);

		if (!ClientController.deliveryAddress.equals("")) {
			txtDeliveryAddress.setText("Delivery Address: " + ClientController.deliveryAddress);
		}

	}

	@FXML
	void getBtnBack(ActionEvent event) {
		Stage primaryStage = new Stage();
		WindowStarter.createWindow(primaryStage, ClientController.getCurrentSystemUser(), "/gui/EktCatalogForm.fxml",
				null, "Ekt Catalog", true);
		primaryStage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // hiding primary window
	}

	@FXML
	void getBtnLogout(ActionEvent event) {
		// log the user out:
		ClientController.sendLogoutRequest();

		Stage primaryStage = new Stage();
		WindowStarter.createWindow(primaryStage, ClientController.getCurrentSystemUser(),
				"/gui/EktSystemUserLoginForm.fxml", null, "Login", true);
		primaryStage.show();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close(); // hiding primary window
	}

	private void SendEmail(String CustomerEmail) {
		final String username = EktEmailUsername;
		final String password = EktEmailPassword;

		Properties prop = new Properties();

		prop.put("mail.smtp.host", "smtp.google.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS
	}

}
