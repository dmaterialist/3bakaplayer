package player;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.Optional;

import static player.BakaPlayerStart.*;

public class BakaController {
    @FXML
    private ListView<Playlist> youtube_list;
    @FXML
    private ListView<Playlist> spotify_list;
    @FXML
    private ListView<Playlist> yandex_list;
    @FXML
    private VBox vbox;
    private BakaPlayerStart main;
    @FXML
    private TextField search;


    @FXML
    private void addPlaylist() {
        Playlist playlist = new Playlist();
        BakaPlayerStart.NewPlaylist(playlist);
    }

    @FXML
    private void clear() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Продолжить?");
        alert.setContentText("Вы собираетесь удалить все плейлисты?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            BakaPlayerStart.clearAll();
        } else {
            alert.close();
        }
    }

    @FXML
    private void click() {
        vbox.heightProperty().addListener((observableValue, number, t1) -> {
            youtube_list.setPrefHeight(t1.doubleValue());
            System.out.println("высота"+t1);
        });
    }


    @FXML
    private void help() {

    }

    @FXML
    private void initialize() {
        youtube_list.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    setGraphic(setButtons(item));
                } catch (NullPointerException e) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
        yandex_list.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    setGraphic(setButtons(item));
                } catch (NullPointerException e) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
        spotify_list.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                try {
                    setGraphic(setButtons(item));
                } catch (NullPointerException e) {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    public void setMainApp(BakaPlayerStart mainApp) {
        this.main = mainApp;
        youtube_list.setItems(getYoutube());
        yandex_list.setItems(getYandex());
        spotify_list.setItems(getSpotify());
        getWindowHeight().addListener((observableValue, number, t1) -> {
            youtube_list.setPrefHeight(t1.doubleValue()-80);
            yandex_list.setPrefHeight(t1.doubleValue()-80);
            spotify_list.setPrefHeight(t1.doubleValue()-80);
            System.out.println("высота"+t1.doubleValue());
        });
        getWindowWidth().addListener((observableValue, number, t1) -> initialize());
    }

    public AnchorPane setButtons(Playlist item) {
        AnchorPane scene = new AnchorPane();
        Label name = new Label();
        String source = item.getSource();
        String button="-fx-font-family: PT Sans; -fx-text-fill: #131313; -fx-font-size:9pt; -fx-padding: 1 1 1 1; -fx-border-color: #131313; -fx-border-width: 1; -fx-background-insets: 0 0 0 0, 0, 1, 2;";
        if (source.equals("yandex")) {
            scene.setStyle("-fx-background-color: #ffcc00;");
        }
        else if (source.equals("youtube")) {
            scene.setStyle("-fx-background-color: #c4302b;");
        }
        else {
            scene.setStyle("-fx-background-color: #1db954;");
        }
        name.setStyle("-fx-font-family: PT Sans; -fx-text-fill: #131313; -fx-font-size:11pt;");
        name.setPrefWidth(getWindowWidth().doubleValue()-180);
        System.out.println("ширина"+name.getPrefWidth());
        StringProperty string = new SimpleStringProperty(item.getName());
        name.setText(string.get());
        string.bind(item.getNameProperty());
        string.addListener((observableValue, number, t1) -> {
            name.setText(t1);
            System.out.println("нужное изменение" + t1);
        });
        Button edit = new Button("изменить");
        Button delete = new Button("удалить");
        Button play = new Button("старт");
        edit.setPrefHeight(20);
        edit.setPrefWidth(65);
        edit.setStyle("-fx-font-size:10");
        delete.setPrefWidth(50);
        delete.setPrefHeight(20);
        delete.setStyle("-fx-font-size:10");
        play.setPrefWidth(40);
        play.setPrefHeight(20);
        play.setStyle("-fx-font-size:10");
        play.setOnAction(actionEvent -> BakaPlayerStart.Link(item));
        delete.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Продолжить?");
            alert.setContentText("Вы собираетесь удалить плейлист " + item.getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (source.equals("youtube"))
                    deleteYoutube(item);
                if (source.equals("spotify"))
                    deleteSpotify(item);
                if (source.equals("yandex"))
                    deleteYandex(item);
            } else {
                alert.close();
            }
        });
        edit.setOnAction(actionEvent -> {
            BakaPlayerStart.EditPlaylist(item);
        });
        edit.setStyle(button);
        delete.setStyle(button);
        play.setStyle(button);
        AnchorPane.setRightAnchor(delete, 72d);
        AnchorPane.setRightAnchor(edit, 1d);
        AnchorPane.setRightAnchor(play, 126d);
        AnchorPane.setTopAnchor(delete, 1d);
        AnchorPane.setTopAnchor(play, 1d);
        AnchorPane.setTopAnchor(edit, 1d);
        AnchorPane.setLeftAnchor(name, 1d);
        AnchorPane.setTopAnchor(name, 3d);
        scene.getChildren().setAll(name, edit, delete, play);
        return (scene);
    }

    @FXML
    private void searchstart() {
        if (search.getText().isEmpty()) {
            youtube_list.setItems(getYoutube());
            yandex_list.setItems(getYandex());
            spotify_list.setItems(getSpotify());
        } else {
            ObservableList<Playlist> youtube = getYoutube().filtered(playlist -> playlist.getName().toLowerCase().contains(search.getText().toLowerCase()));
            ObservableList<Playlist> yandex = getYandex().filtered(playlist -> playlist.getName().toLowerCase().contains(search.getText().toLowerCase()));
            ObservableList<Playlist> spotify = getSpotify().filtered(playlist -> playlist.getName().toLowerCase().contains(search.getText().toLowerCase()));
            youtube_list.setItems(youtube);
            yandex_list.setItems(yandex);
            spotify_list.setItems(spotify);
            for (int i = 0; i < getYoutube().size(); i++) {
                System.out.println(getYoutube().get(i).getName());
            }
            for (int i = 0; i < getYandex().size(); i++) {
                System.out.println(getYandex().get(i).getName());
            }
            for (int i = 0; i < getSpotify().size(); i++) {
                System.out.println(getSpotify().get(i).getName());
            }
        }
    }

}
