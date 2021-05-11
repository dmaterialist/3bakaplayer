package player;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class BakaPlayerStart extends Application {
    private static ObservableList<Playlist> youtube = FXCollections.observableArrayList();
    private static ObservableList<Playlist> spotify = FXCollections.observableArrayList();
    private static ObservableList<Playlist> yandex = FXCollections.observableArrayList();


    public BakaPlayerStart() {
    }

    public static ObservableList<Playlist> getSpotify() {
        return spotify;
    }

    public static ObservableList<Playlist> getYandex() {
        return yandex;
    }

    public static ObservableList<Playlist> getYoutube() {
        return youtube;
    }

    public static void addYoutube (Playlist playlist) {
        youtube.add(playlist);
        System.out.println(youtube.size());
    }

    public static void addSpotify (Playlist playlist) {
        spotify.add(playlist);
        System.out.println(spotify.size());
    }

    public static void addYandex (Playlist playlist) {
        spotify.add(playlist);
        System.out.println(yandex.size());
    }

    public static void deleteYoutube (Playlist playlist) {
        youtube.remove(playlist);
        System.out.println(youtube.size());
    }

    public static void deleteYandex (Playlist playlist) {
        yandex.remove(playlist);
        System.out.println(yandex.size());
    }

    public static void deleteSpotify (Playlist playlist) {
        spotify.remove(playlist);
        System.out.println(spotify.size());
        playlist.delete();
    }


    public static void filterYandex (Playlist playlist, String filter) {
        yandex= yandex.filtered(playlist1 -> playlist1.getNameString().toLowerCase().startsWith(filter.toLowerCase()));
    }

    public static void filterYoutube (Playlist playlist, String filter) {
        youtube= youtube.filtered(playlist1 -> playlist1.getNameString().toLowerCase().startsWith(filter.toLowerCase()));
    }

    public static void filterSpotify (Playlist playlist, String filter) {
        spotify= spotify.filtered(playlist1 -> playlist1.getNameString().toLowerCase().startsWith(filter.toLowerCase()));
    }



    public static boolean findYoutube(Playlist player) {
        for (int i = 0; i < getYoutube().size(); i++) {
            if (getYoutube().get(i).getIdString().equals(player.getIdString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean findSpotify(Playlist player) {
        for (int i = 0; i < getSpotify().size(); i++) {
            if (getSpotify().get(i).getIdString().equals(player.getIdString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean findYandex(Playlist player) {
        for (int i = 0; i < getYandex().size(); i++) {
            if (getYandex().get(i).getIdString().equals(player.getIdString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BakaPlayerStart.class.getResource("/bakaplayer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3baka! music player");
        primaryStage.show();
        BakaController controller = loader.getController();
        controller.setMainApp(this);
    }

    public static void Link(Playlist playlist) {
        final Stage stage = new Stage();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();

        stage.setX(sSize.width - playlist.getWidth());
        stage.setY(sSize.height - 55 - playlist.getHeight());
        WebView root = new WebView();
        root.setPrefWidth(playlist.getWidth());
        root.setPrefHeight(playlist.getHeight());
        final WebEngine webEngine = root.getEngine();
        webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        webEngine.loadContent(playlist.getHTML());

        Scene scene = new Scene(root);

        stage.setTitle("Плеер");
        stage.setScene(scene);

        stage.show();
    }

    public static void NewPlaylist(Playlist playlist) {
        try {
            final Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BakaPlayerStart.class.getResource("/addPlaylist.fxml"));
            Parent root = loader.load();
            stage.setTitle("Добавить плейлист");
            Scene scene = new Scene(root, 500, 200);
            stage.setScene(scene);
            AddPlaylistController controller = loader.getController();
            controller.setStage(stage);
            controller.setPlaylist(playlist);
            stage.showAndWait();
            if (controller.isOkClicked()) {
                String source = controller.getSource();
                if (source.equals("youtube")) {
                    if (!findYoutube(playlist))
                        addYoutube(playlist);
                    else clonesAreRestricted();
                }
                if (source.equals("spotify"))
                    if (!findSpotify(playlist))
                        addSpotify(playlist);
                    else clonesAreRestricted();
                if (source.equals("yandex"))
                    if (!findYandex(playlist))
                        addYandex(playlist);
                    else clonesAreRestricted();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    public static void EditPlaylist(Playlist playlist) {
        try {
            final Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BakaPlayerStart.class.getResource("/editPlaylist.fxml"));
            Parent root = loader.load();
            stage.setTitle("Изменить плейлист");
            Scene scene = new Scene(root, 500, 120);
            stage.setScene(scene);
            AddPlaylistController controller = loader.getController();
            controller.setStage(stage);
            controller.setPlaylist(playlist);
            stage.showAndWait();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static void clonesAreRestricted() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Запрещено добавление одинаковых плейлистов");
        alert.setContentText("Может, Вас удовлетворит редактирование имени этого плейлиста?");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}