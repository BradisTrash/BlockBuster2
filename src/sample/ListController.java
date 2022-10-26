package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListController implements Initializable {
    public ObservableList<Movie> movieList = FXCollections.observableArrayList();
    public TableView<Movie> movies;
    public TableColumn<Movie, String> title;
    public TableColumn<Movie, String> year;
    public TableColumn<Movie, String> id;
    public TableColumn<Movie, String> genre;
    public TableColumn<Movie, Boolean> available;

    public void initialize(URL url, ResourceBundle rb){
        initCol();
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        movies.setItems(movieList);
    }
    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        year.setCellValueFactory(new PropertyValueFactory<>("year"));
        available.setCellValueFactory(new PropertyValueFactory<>("available"));

    }
    private void loadData() throws SQLException {
        ConnectionClass connectionClass = ConnectionClass.getInstance();
        ResultSet rs = connectionClass.execQuery("SELECT * FROM Movie");
        while(rs.next()){
            try {
                String ID = rs.getString("id");
                String Title = rs.getString("title");
                String Genre = rs.getString("genre");
                String Year = rs.getString("year");
                Boolean Available = rs.getBoolean("isAvail");
                movieList.add(new Movie(ID,Title,Genre,Year,Available));
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
    public static class Movie{
        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty genre;
        private final SimpleStringProperty year;
        private final SimpleBooleanProperty available;

        Movie(String Id, String Title,String Genre, String Year,Boolean Available){
            this.id = new SimpleStringProperty(Id);
            this.title = new SimpleStringProperty(Title);
            this.genre = new SimpleStringProperty(Genre);
            this.year = new SimpleStringProperty(Year);
            this.available = new SimpleBooleanProperty(Available);
        }

        public String getId() {
            return id.get();
        }



        public String getTitle() {
            return title.get();
        }



        public String getGenre() {
            return genre.get();
        }



        public String getYear() {
            return year.get();
        }



        public Boolean getAvailable() {
            return available.get();
        }


    }
}
