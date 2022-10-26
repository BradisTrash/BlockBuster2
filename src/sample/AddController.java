package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    public AnchorPane rootPane;
    public Button save;
    public Button cancel;
    public TextField id;
    public TextField title;
    public TextField year;
    public TextField genre;
    ConnectionClass connectionClass;
    public void initialize(URL url, ResourceBundle rb){
        BooleanBinding Id = Bindings.createBooleanBinding(() -> {
            String ID2 = id.getText();
            return !ID2.isEmpty();
        }, id.textProperty());
        BooleanBinding Title = Bindings.createBooleanBinding(() -> {
            String Title2 = title.getText();
            return !Title2.isEmpty();
        }, title.textProperty());
        BooleanBinding Year = Bindings.createBooleanBinding(() -> {
            String Year2 = year.getText();
            return !Year2.isEmpty();
        }, year.textProperty());
        BooleanBinding Genre = Bindings.createBooleanBinding(() -> {
            String Genre2 = genre.getText();
            return !Genre2.isEmpty();
        }, genre.textProperty());
        save.disableProperty().bind(Id.not().or(Title.not()).or(Year.not()).or(Genre.not()));
        connectionClass = ConnectionClass.getInstance();
    }
    public void saveBtn(ActionEvent e) {
        String ID = id.getText();
        String Title = title.getText();
        String Year = year.getText();
        String Genre = genre.getText();

        String query = "INSERT INTO Movie VALUES ("
                + "'" + ID + "',"
                + "'" + Title + "',"
                + "'" + Year + "',"
                + "'" + Genre + "',"
                + "'" + 0 + "'"
                + ")";
        if(connectionClass.execAction(query)) {
            Alert alrt = new Alert(Alert.AlertType.CONFIRMATION);
            alrt.setTitle("Confirm");
            alrt.setContentText("Are you sure you want to rent " + Title);
            Optional<ButtonType> response = alrt.showAndWait();
            if(response.get()==ButtonType.OK){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText(Title + " rented!");
                alert.show();
            }
        }
        else{
            Alert alrt = new Alert(Alert.AlertType.ERROR);
            alrt.setHeaderText("Unsuccessful");
            alrt.setContentText("Movie is already being rented!");
            alrt.show();
        }



    }
    public void checkData() throws SQLException {
        ResultSet rs = connectionClass.execQuery("SELECT Title FROM Movie");
        while(rs.next()){
            try {
                String title = rs.getString("Title");
                System.out.println(title);
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    public void cancelBtn(ActionEvent e){
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
