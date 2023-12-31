package project;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class extends the {@code SceneController} and manages the GUI events for 
 * the login scene.
 */
public final class LoginScene extends SceneController implements IAccountScenes {
	
	@FXML private VBox loginPane;
	@FXML private TextField usernameField;	
	@FXML private PasswordField passwordField;
	@FXML private Text errorLabel;
	
	@FXML
	private void initialize() {
		errorLabel.setText("");
		if (Player.exists()) addBackButton(loginPane, this);
	}
	
	// Scene control methods
	
	@FXML
	protected MainMenuScene switchToMainMenuScene() {
		super.switchToMainMenuScene();
		return null;
	}
	
	@FXML
	protected RegistrationScene switchToRegistrationScene() {
		super.switchToRegistrationScene();
		return null;
	}
	
	@FXML
	protected DeleteScene switchToDeleteScene() {
		super.switchToDeleteScene();
		return null;
	}
	
	// Button Methods	
	@FXML
	private void onGuestLoginButtonPressed() {
		Player.setInstance(Player.START_BALANCE);
		switchToMainMenuScene();
	}
	
	@FXML
	private void onLoginButtonPressed() {
		if (!credentialsValid()) {
			errorLabel.setText("Incorrect username or password.");
			return;
		}
		String username = usernameField.getText();	
		Double balance = DatabaseManager.getInstance().getUserBankBalance(username);
		Player.setInstance(username, balance);
		switchToMainMenuScene();
	}
	
	private boolean credentialsValid() {
		DatabaseManager DBM = DatabaseManager.getInstance();
		errorLabel.setText("");

		if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) return false;		
		else if (!DBM.usernameExist(usernameField.getText())) return false;
		
		String encryptedPassword = DBM.getUserPassword(usernameField.getText());
		String decryptedPassword = PasswordHandler.decrypt(encryptedPassword);
		if (!passwordField.getText().equals(decryptedPassword)) return false;
		
		return true;
	}
}
