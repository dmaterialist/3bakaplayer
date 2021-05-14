package player;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BakaPlayerStart extends Application {
    private static final ObservableList<Playlist> youtube = FXCollections.observableArrayList();
    private static final ObservableList<Playlist> spotify = FXCollections.observableArrayList();
    private static final ObservableList<Playlist> yandex = FXCollections.observableArrayList();
    private static final DoubleProperty windowWidth = new SimpleDoubleProperty(400);
    private static final DoubleProperty windowHeight = new SimpleDoubleProperty(600);


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


    public static void addYoutube(Playlist playlist) {
        youtube.add(playlist);
    }

    public static void addSpotify(Playlist playlist) {
        spotify.add(playlist);
    }

    public static void addYandex(Playlist playlist) {
        yandex.add(playlist);
    }

    public static void deleteYoutube(Playlist playlist) {
        youtube.remove(playlist);
    }

    public static void deleteYandex(Playlist playlist) {
        yandex.remove(playlist);
    }

    public static void deleteSpotify(Playlist playlist) {
        spotify.remove(playlist);
    }

    public static void clearAll() {
        spotify.clear();
        youtube.clear();
        yandex.clear();
    }

    public static DoubleProperty getWindowWidth() {
        return windowWidth;
    }

    public static DoubleProperty getWindowHeight() {
        return windowHeight;
    }

    @Override
    public void start(Stage primaryStage) throws IOException, JAXBException {
        load();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BakaPlayerStart.class.getResource("/bakaplayer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 600);
        scene.heightProperty().addListener((observableValue, number, t1) -> windowHeight.setValue(t1));
        scene.widthProperty().addListener((observableValue, number, t1) -> windowWidth.setValue(t1));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
        primaryStage.setTitle("3baka! music player");
        primaryStage.show();
        BakaController controller = loader.getController();
        controller.setting(primaryStage);
        primaryStage.setOnCloseRequest(we -> {
            try {
                save();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });
    }

    public static void Link(Playlist playlist) {
        final Stage stage = new Stage();
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
        stage.setX(sSize.width - playlist.getWidth());
        stage.setY(sSize.height - 55 - playlist.getHeight());
        WebView root = new WebView();
        root.setPrefWidth(playlist.getWidth());
        root.setPrefHeight(playlist.getHeight());
        final WebEngine webEngine = root.getEngine();
        webEngine.loadContent(playlist.getHTML());

        Scene scene = new Scene(root);

        stage.setTitle("Плеер");
        stage.setScene(scene);

        stage.show();
        stage.setOnCloseRequest(windowEvent -> webEngine.load(null));
    }

    public static void NewPlaylist() throws IOException {
        Playlist playlist = new Playlist();
        final Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BakaPlayerStart.class.getResource("/addPlaylist.fxml"));
        Parent root = loader.load();
        stage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
        stage.setResizable(false);
        stage.setTitle("Добавить плейлист");
        Scene scene = new Scene(root, 400, 120);
        stage.setScene(scene);
        AddPlaylistController controller = loader.getController();
        controller.setStage(stage);
        controller.setPlaylist(playlist);
        stage.show();
    }

    public static void EditPlaylist(Playlist playlist) {
        try {
            final Stage stage = new Stage();
            stage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
            FXMLLoader loader = new FXMLLoader();
            stage.setResizable(false);
            loader.setLocation(AddPlaylistController.class.getResource("/editPlaylist.fxml"));
            Parent root = loader.load();
            stage.setTitle("Изменить плейлист");
            Scene scene = new Scene(root, 400, 80);
            stage.setScene(scene);
            AddPlaylistController controller = loader.getController();
            controller.setPlaylist(playlist);
            controller.setStage(stage);
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void load() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PlaylistList.class);
        Unmarshaller um = context.createUnmarshaller();
        File file = new File("src/main/resources/save.xml");
        PlaylistList list = (PlaylistList) um.unmarshal(file);
        if (!youtube.isEmpty())
            youtube.addAll(list.getYoutube());
        if (!spotify.isEmpty())
            spotify.addAll(list.getSpotify());
        if (!yandex.isEmpty())
            yandex.addAll(list.getYandex());
    }

    public void save() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PlaylistList.class);
        Marshaller m = context.createMarshaller();
        PlaylistList list = new PlaylistList();
        list.setSpotify(spotify);
        list.setYandex(yandex);
        list.setYoutube(youtube);
        m.marshal(list, new File("src/main/resources/save.xml"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}