package player;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import player.BakaPlayerStart;
import javafx.fxml.FXML;

import javax.security.auth.callback.Callback;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static player.BakaPlayerStart.*;

public class BakaController {
    @FXML
    private ListView<Playlist> youtube_list;
    @FXML
    private ListView<Playlist> spotify_list;
    @FXML
    private ListView<Playlist> yandex_list;
    private BakaPlayerStart main;
    @FXML
    private TextField search;
    @FXML
    private Boolean search_continue = false;


    @FXML
    private void addPlaylist() {
        Playlist playlist = new Playlist();
        BakaPlayerStart.NewPlaylist(playlist);
    }

    @FXML
   private void initialize() {
        spotify_list.setCellFactory(param -> new ListCell<>() {
    @Override
           protected void updateItem(Playlist item, boolean empty) {
               super.updateItem(item, empty);
               if (empty || item == null || item.getNameString() == null) {
               } else {
                   setGraphic(setButtons(item));
               }
            }
        });
     youtube_list.setCellFactory(param -> new ListCell<>() {
           @Override
           protected void updateItem(Playlist item, boolean empty) {
               super.updateItem(item, empty);
               if (empty || item == null || item.getNameString() == null) {
                   return;
               } else {
                   setGraphic(setButtons(item));
               }
            }
        });
        yandex_list.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
               if (empty || item == null || item.getNameString() == null) {
                    return;
               } else {
                    setGraphic(setButtons(item));
               }
            }
        });
    }

    public void setMainApp(BakaPlayerStart mainApp) {
        this.main = mainApp;
        youtube_list.setItems(BakaPlayerStart.getYoutube());
        yandex_list.setItems(BakaPlayerStart.getYandex());
        spotify_list.setItems(BakaPlayerStart.getSpotify());
    }

    public AnchorPane setButtons(Playlist item) {
        AnchorPane scene = new AnchorPane();
        Label name = new Label(item.getNameString());
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
            alert.setContentText("Вы собираетесь удалить плейлист " + item.getNameString());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                String source = item.getSourceString();
                if (source.equals("youtube"))
                    deleteYoutube (item);
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
        AnchorPane.setRightAnchor(delete, 72d);
        AnchorPane.setRightAnchor(edit, 1d);
        AnchorPane.setRightAnchor(play, 126d);
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
            search_continue = true;
            System.out.println("поиск " + search.getText());
            ObservableList<Playlist> youtube= getYoutube().filtered(playlist -> playlist.getNameString().toLowerCase().startsWith(search.getText().toLowerCase()));
            ObservableList<Playlist> yandex= getYandex().filtered(playlist -> playlist.getNameString().toLowerCase().startsWith(search.getText().toLowerCase()));
            ObservableList<Playlist> spotify= getSpotify().filtered(playlist -> playlist.getNameString().toLowerCase().startsWith(search.getText().toLowerCase()));
            youtube_list.setItems(youtube);
            yandex_list.setItems(yandex);
            spotify_list.setItems(spotify);
            System.out.println(yandex.size());
            System.out.println(getSpotify().size());
            System.out.println(getYoutube().size());
            System.out.println(getYandex().size());
            for (int i = 0; i < getYoutube().size(); i++) {
                System.out.println(getYoutube().get(i).getNameString());
            }
            for (int i = 0; i < getYandex().size(); i++) {
                System.out.println(getYandex().get(i).getNameString());
            }
            for (int i = 0; i < getSpotify().size(); i++) {
                System.out.println(getSpotify().get(i).getNameString());
            }
        }
    }

}
