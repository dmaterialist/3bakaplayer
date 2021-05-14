package player;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;

public class AddPlaylistController {
    @FXML
    private TextField new_link;
    @FXML
    private TextField new_name;

    @FXML
    private TextField edit_name;

    private Playlist playlist;
    private String source;
    private Stage stage;
    private ImageView imageView;


    @FXML
    private void initialize() {
        File file= new File("src/main/resources/cantfind.png");
        Image image=new Image(file.toURI().toString());
        this.imageView=new ImageView(image);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }


    public String getSource() {
        return source;
    }

    @FXML
    private void ok() {
        try {
            playlist.setByLink(new_link.getText());
            source = playlist.getSource();
            if (!new_name.getText().isEmpty()) {
                playlist.setName(new_name.getText());
            }
                if (source.equals("youtube")) {
                    BakaPlayerStart.addYoutube(playlist);
                }
                if (source.equals("spotify")) {
                    BakaPlayerStart.addSpotify(playlist);
                }
                if (source.equals("yandex")) {
                    BakaPlayerStart.addYandex(playlist);
                }
            stage.close();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(imageView);
            alert.initOwner(stage);
            alert.setTitle("Ошибочный ввод");
            alert.setHeaderText("Пожалуйста, введите правильную ссылку");
            alert.showAndWait();
        }
    }

    @FXML
    private void ok_boomer() {
        playlist.setName(edit_name.getText());
        stage.close();
    }


    @FXML
    private void cancel() {
        stage.close();
    }

}
