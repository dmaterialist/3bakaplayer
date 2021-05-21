package player;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlType(name = "playlist")
public final class Playlist {
    private final StringProperty link;
    private final StringProperty source;
    private StringProperty name;

    public Playlist() {
        this.link = new SimpleStringProperty("");
        this.source = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
    }

    public Playlist(String link, String source, String name) {
        this.link = new SimpleStringProperty(link);
        this.name = new SimpleStringProperty(name);
        this.source = new SimpleStringProperty(source);
    }


    public StringProperty getIdProperty() {
        return link;
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public StringProperty getSourcePropety() {
        return source;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name.get();
    }

    @XmlElement(name = "link")
    public String getLink() {
        return link.get();
    }

    @XmlElement(name = "source")
    public String getSource() {
        return source.get();
    }

    public int getWidth() {
        switch (this.getSource()) {
            case "spotify":
                return 300;
            case "youtube":
                return 400;
            case "yandex":
                return 300;
        }
        return 0;
    }

    public int getHeight() {
        switch (this.getSource()) {
            case "spotify":
                return 380;
            case "youtube":
                return 225;
            case "yandex":
                return 450;
        }
        return 0;
    }


    public void setName(String newName) {
        this.name.set(newName);
    }

    public void setLink(String newId) {
        this.link.set(newId);
    }

    public void setSource(String newSource) {
        this.source.set(newSource);
    }

    public void setByLink(String link) {
        try {
            if (link.contains("youtube")) {
                this.source.set("youtube");
                Pattern yt = Pattern.compile(".+(list=.+)");
                Matcher s = yt.matcher(link);
                s.find();
                this.link.set("https://www.youtube.com/embed/videoseries?" + s.group(1));
            } else if (link.contains("spotify")) {
                this.source.set("spotify");
                this.link.set(link.replace("https://open.spotify.com/", "https://open.spotify.com/embed/"));
            } else if (link.contains("yandex")) {
                this.source.set("yandex");
                if (link.contains("album")) {
                    this.link.set(link.replace("/album/", "/iframe/#album/"));
                }
                if (link.contains("playlists"))
                    this.link.set(link.replace("/users/", "/iframe/#playlist/").replace("/playlists/", "/"));
            } else {
                throw new NullPointerException("Некорректный ввод");
            }
        } catch (IllegalStateException ignored) {
        }
        String newlink = link;
        if (this.getSource().equals("youtube") && link.contains("watch")) {
            newlink = this.getLink().replace("embed/videoseries", "playlist");
        }
        if (this.getSource().equals("spotify") && link.contains("?")) {
            newlink = link.replaceAll("\\?.*", "");
        }
        Document doc;
        String title = "";
        try {
            doc = Jsoup.connect(newlink).get();
            title = doc.title();
        } catch (Exception ignored) {
        }
        if (this.getSource().equals("youtube") && !title.isEmpty())
            this.name.set(title.substring(0, title.length() - 10));
        else if (this.getSource().equals("spotify") && !title.contains("Web Player")) {
            if (title.contains("Album") || title.contains("Single"))
                this.name.set(title.substring(0, title.length() - 10));
            if (title.contains("Spotify Playlist"))
                this.name.set(title.substring(0, title.length() - 19));
        } else if (this.getSource().equals("yandex")) {
            if (title.contains("плейлист"))
                this.name.set(title.substring(0, title.length() - 28));
            if (title.contains("Слушать онлайн"))
                this.name.set(title.substring(0, title.length() - 32));
        } else {
            throw new NullPointerException("Некорректный ввод");
        }
    }
}