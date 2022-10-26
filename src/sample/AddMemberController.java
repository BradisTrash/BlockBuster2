package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    public TextField name;
    public TextField id;
    public TextField phone;
    public TextField email;
    public AnchorPane rootPane;
    public Button create;
    public Button cancel;
    ConnectionClass connectionClass;

    public void initialize(URL url, ResourceBundle rb){
        BooleanBinding Id = Bindings.createBooleanBinding(() -> {
            String ID2 = id.getText();
            return !ID2.isEmpty();
        }, id.textProperty());
        BooleanBinding Name = Bindings.createBooleanBinding(() -> {
            String Name2 = name.getText();
            return !Name2.isEmpty();
        }, name.textProperty());
        BooleanBinding Phone = Bindings.createBooleanBinding(() -> {
            String Phone2 = phone.getText();
            return !Phone2.isEmpty();
        }, phone.textProperty());
        BooleanBinding Email = Bindings.createBooleanBinding(() -> {
            String Email2 = email.getText();
            return !Email2.isEmpty();
        }, email.textProperty());
        create.disableProperty().bind(Id.not().or(Name.not()).or(Phone.not()).or(Email.not()));
        connectionClass = ConnectionClass.getInstance();

    }

    public void addMember(ActionEvent e){
        String Name = name.getText();
        String ID = id.getText();
        String Phone = phone.getText();
        String Email = email.getText();

        String st = "INSERT INTO Members VALUES (" +
                "'" + ID + "',"
                + "'" + Name + "',"
                + "'" + Phone + "',"
                + "'" + Email + "',"
                + "'" + 0 + "'"
                + ")";
         if(connectionClass.execAction(st)){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setHeaderText("Success");
             alert.setContentText("Member Created");
             alert.show();
         }
         else{
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText("Unsuccessful");
             alert.setContentText("Member Was Not Created");
             alert.show();
         }

    }
    public void checkData() throws SQLException {
        ResultSet rs = connectionClass.execQuery("SELECT Name FROM Members");
        while(rs.next()){
            try {
                String title = rs.getString("name");
                System.out.println(title);
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    public void cancel(ActionEvent e){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
