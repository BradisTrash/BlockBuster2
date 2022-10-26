package sample;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController {
    public Button save;
    public TextField username;
    public PasswordField password;
    public Button cancel;
    public AnchorPane rootPane;

    public void handleSave(){
        ConnectionClass connectionClass = ConnectionClass.getInstance();

    }

    public void handleCancel(){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
