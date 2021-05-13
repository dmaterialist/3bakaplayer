package player;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BakaPlayerStart extends Application {
    private static ObservableList<Playlist> youtube = FXCollections.observableArrayList();
    private static ObservableList<Playlist> spotify = FXCollections.observableArrayList();
    private static ObservableList<Playlist> yandex = FXCollections.observableArrayList();
    private static DoubleProperty windowWidth = new SimpleDoubleProperty(400);
    private static DoubleProperty windowHeight = new SimpleDoubleProperty(600);


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


    public static boolean findYoutube(Playlist player) {
        for (int i = 0; i < getYoutube().size(); i++) {
            if (getYoutube().get(i).getId().equals(player.getId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean findSpotify(Playlist player) {
        for (int i = 0; i < getSpotify().size(); i++) {
            if (getSpotify().get(i).getId().equals(player.getId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean findYandex(Playlist player) {
        for (int i = 0; i < getYandex().size(); i++) {
            if (getYandex().get(i).getId().equals(player.getId())) {
                return true;
            }
        }
        return false;
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
        primaryStage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
        Scene scene = new Scene(root, 400, 600);
        scene.heightProperty().addListener((observableValue, number, t1) -> windowHeight.setValue(t1));
        scene.widthProperty().addListener((observableValue, number, t1) -> {
            windowWidth.setValue(t1);
        });
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("3baka! music player");
        primaryStage.show();
        BakaController controller = loader.getController();
        controller.setMainApp(this);
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
        stage.setOnCloseRequest(windowEvent -> webEngine.load(null)); //похоже на костыль, но больше я способов не видел
    }

    public static void NewPlaylist(Playlist playlist) {
        try {
            final Stage stage = new Stage();
            stage.getIcons().add(new javafx.scene.image.Image("/3baka.jpg"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BakaPlayerStart.class.getResource("/addPlaylist.fxml"));
            Parent root = loader.load();
            stage.setResizable(false);
            stage.setTitle("Добавить плейлист");
            Scene scene = new Scene(root, 400, 120);
            stage.setScene(scene);
            AddPlaylistController controller = loader.getController();
            controller.setStage(stage);
            controller.setPlaylist(playlist);
            stage.showAndWait();
            if (controller.isOkClicked()) {
                String source = controller.getSource();
                if (source.equals("youtube")) {
                    if (!findYoutube(playlist)) {
                        addYoutube(playlist);
                    } else clonesAreRestricted();
                }
                if (source.equals("spotify")) {
                    if (!findSpotify(playlist)) {
                        addSpotify(playlist);
                    } else clonesAreRestricted();
                }
                if (source.equals("yandex")) {
                    if (!findYandex(playlist))
                        addYandex(playlist);
                    else clonesAreRestricted();
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

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
            Scene scene = new Scene(root, 400, 120);
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
        alert.setHeaderText("Запрещено добавление одинаковых плейлистов");
        alert.setTitle("Добавление не выполнено");
        alert.setContentText("Но Вы можете отредактировать название плейлиста!");
        alert.showAndWait();
    }

    public void load() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PlaylistList.class);
        Unmarshaller um = context.createUnmarshaller();
        File file = new File("src/main/resources/save.xml");
        PlaylistList list = (PlaylistList) um.unmarshal(file);
        try {
            youtube.addAll(list.getYoutube());
        } catch (NullPointerException ignored) {
        }
        try {
        spotify.addAll(list.getSpotify());
        } catch (NullPointerException ignored) {
        }
        try {
        yandex.addAll(list.getYandex());
        } catch (NullPointerException ignored) {
        }
    }

    public void save() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PlaylistList.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        PlaylistList list = new PlaylistList();
        list.setSpotify(spotify);
        list.setYandex(yandex);
        list.setYoutube(youtube);
        for (int i=0; i<list.getSpotify().size();i++)
        System.out.println(list.getSpotify().get(i).getName());
        try {
            m.marshal(list, new File("src/main/resources/save.xml"));
        } catch (JAXBException e) {
            System.out.println("error");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}