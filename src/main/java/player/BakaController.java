package player;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
        File delFile = new File("src/main/resources/deleting.png");
        Image delImage = new Image(delFile.toURI().toString());
        ImageView delView = new ImageView(delImage);
        alert.setGraphic(delView);
        alert.setTitle("Продолжить?");
        alert.setHeaderText ("Вы собираетесь удалить все плейлисты?");
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
            System.out.println("высота" + t1);
        });
    }


    @FXML
    private void help() throws MalformedURLException {
        final Stage stage = new Stage();
        stage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
        WebView root = new WebView();
        final WebEngine webEngine = root.getEngine();
        File file = new File("src/main/resources/tutorial.html");
        URL url = file.toURI().toURL();
        webEngine.load(url.toString());
        Scene scene = new Scene(root);
        stage.setTitle("Помощь");
        stage.setScene(scene);
        stage.show();
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
            youtube_list.setPrefHeight(t1.doubleValue() - 80);
            yandex_list.setPrefHeight(t1.doubleValue() - 80);
            spotify_list.setPrefHeight(t1.doubleValue() - 80);
            System.out.println("высота" + t1.doubleValue());
        });
        getWindowWidth().addListener((observableValue, number, t1) -> initialize());
    }

    public AnchorPane setButtons(Playlist item) {
        AnchorPane scene = new AnchorPane();
        Label name = new Label();
        String source = item.getSource();
        if (source.equals("yandex")) {
            scene.setStyle("-fx-background-color: #ffcc00;");
        } else if (source.equals("youtube")) {
            scene.setStyle("-fx-background-color: #c4302b;");
        } else {
            scene.setStyle("-fx-background-color: #1db954;");
        }
        name.setStyle("-fx-font-family: PT Sans; -fx-text-fill: #131313; -fx-font-size:11pt;");
        name.setPrefWidth(getWindowWidth().doubleValue() - 110);
        System.out.println("ширина" + name.getPrefWidth());
        StringProperty string = new SimpleStringProperty(item.getName());
        name.setText(string.get());
        string.bind(item.getNameProperty());
        string.addListener((observableValue, number, t1) -> {
            name.setText(t1);
            System.out.println("нужное изменение" + t1);
        });
        File editFile = new File("src/main/resources/edit.png");
        Image editImage = new Image(editFile.toURI().toString());
        ImageView editView = new ImageView(editImage);
        File delFile = new File("src/main/resources/deleting.png");
        Image delImage = new Image(delFile.toURI().toString());
        ImageView delView = new ImageView(delImage);
        File deleteFile = new File("src/main/resources/trash (1).png");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        ImageView deleteView = new ImageView(deleteImage);
        File playFile = new File("src/main/resources/play-button.png");
        Image playImage = new Image(playFile.toURI().toString());
        ImageView playView = new ImageView(playImage);
        Button edit = new Button();
        edit.setStyle("-fx-background-color: transparent;");
        edit.setGraphic(editView);
        Button delete = new Button();
        delete.setStyle("-fx-background-color: transparent;");
        delete.setGraphic(deleteView);
        Button play = new Button();
        play.setStyle("-fx-background-color: transparent;");
        play.setGraphic(playView);
        edit.setMaxHeight(20);
        edit.setMaxWidth(20);
        delete.setMaxWidth(20);
        delete.setMaxHeight(20);
        play.setMaxWidth(20);
        play.setMaxHeight(20);
        play.setOnAction(actionEvent -> BakaPlayerStart.Link(item));
        delete.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Продолжить?");
            alert.setHeaderText ("Вы собираетесь удалить плейлист " + item.getName());
            alert.setGraphic(delView);
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
        AnchorPane.setRightAnchor(delete, 10d);
        AnchorPane.setRightAnchor(edit, 32d);
        AnchorPane.setRightAnchor(play, 56d);
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
