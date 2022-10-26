package sample;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button rent;
    public StackPane stackPane;
    public Button movies;
    public Button favorites;
    public Button amountDue;
    public Button settings;
    public MenuItem close;
    ConnectionClass connectionClass;


    public void initialize(URL url, ResourceBundle rb) {

    }

    public void loadAddMovie(ActionEvent e) throws IOException {
        loadWindow("AddMovie.fxml");
    }
    public void loadMovieList(ActionEvent e) throws IOException {
        loadWindow("movies.fxml");
    }
    public void loadFavorites(ActionEvent e){

    }
    public void loadAmountDue(ActionEvent e){

    }
    public void loadSettings(ActionEvent e) throws IOException {
        loadWindow("settings.fxml");
    }
    public void loadWindow(String loc) throws IOException {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void quit(ActionEvent e){
        System.exit(0);
    }
}
