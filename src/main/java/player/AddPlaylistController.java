package player;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public String getSource() {
        return source;
    }

    @FXML
    private void ok() {
        try {
            playlist.setByLink(new_link.getText());
            source=playlist.getSourceString();
            if (!new_name.getText().isEmpty()) {
                playlist.setName(new_name.getText());
            }
            okClicked=true;
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Ошибочный ввод");
            alert.setContentText("Пожалуйста, введите правильную ссылку");
            alert.showAndWait();
        }
        stage.close();
    }

    @FXML
    private void ok_boomer() {
        playlist.setName(edit_name.getText());
        playlist.delete();
        stage.close();
    }


    @FXML
    private void close() {
        stage.close();
        System.out.println("закрыть");
    }

}
