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
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
        System.out.println("3,empty" + playlist.getName());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public String getSource() {
        return source;
    }

    @FXML
    private void ok() {
        System.out.println("4,empty" + playlist.getName());
        try {
            playlist.setByLink(new_link.getText());
            source = playlist.getSource();
            System.out.println("5,name" + playlist.getName());
            if (!new_name.getText().isEmpty()) {
                playlist.setName(new_name.getText());
            }
            okClicked = true;
            stage.close();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            File editFile = new File("src/main/resources/cantfind.png");
            Image editImage = new Image(editFile.toURI().toString());
            ImageView editView = new ImageView(editImage);
            alert.setGraphic(editView);
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
