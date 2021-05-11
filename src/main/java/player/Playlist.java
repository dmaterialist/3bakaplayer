package player;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class Playlist {
    private final StringProperty id;
    private final StringProperty source;
    private StringProperty name;

    public Playlist() {
        this.id = new SimpleStringProperty("");
        this.source = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
    }

    public Playlist(String id, String source, String name) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.source = new SimpleStringProperty(source);
    }


    public StringProperty getId() {
        return id;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getSource() {
        return source;
    }

    public String getNameString() {
        return name.get();
    }

    public String getIdString() {
        return id.get();
    }

    public String getSourceString() {
        return source.get();
    }

    public int getWidth() {
        switch (this.getSourceString()) {
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
        switch (this.getSourceString()) {
            case "spotify":
                return 380;
            case "youtube":
                return 225;
            case "yandex":
                return 450;
        }
        return 0;
    }

    public void delete() {
        this.name=new SimpleStringProperty("призрак");
    }

    public void setName(String newName) {
        this.name.set(newName);
    }

    public void setId(String newId) {
        this.id.set(newId);
    }

    public void setSource(String newSource) {
        this.source.set(newSource);
    }

    public void setByLink(String link) {
        try {
            if (link.contains("youtube") && link.contains("list")) {
                this.source.set("youtube");
                Pattern ytb = Pattern.compile("list=([^&]+)[&]?");
                Matcher y = ytb.matcher(link);
                y.find();
                this.id.set(y.group(1));
            } else if (link.contains("spotify")) {
                this.source.set("spotify");
                if (link.contains("album")) {
                    Pattern spt = Pattern.compile("(album/[^?]+)[?]?");
                    Matcher s = spt.matcher(link);
                    s.find();
                    this.id.set(s.group(1));
                } else if (link.contains("playlist")) {
                    Pattern spt = Pattern.compile("(playlist/[^?]+)[?]?");
                    Matcher s = spt.matcher(link);
                    s.find();
                    this.id.set(s.group(1));
                }
            }
         else if (link.contains("yandex") && (link.contains("album") || link.contains("playlists"))) {
            this.source.set("yandex");
            Pattern spt = Pattern.compile("users/.+|album/.+");
            Matcher s = spt.matcher(link);
            s.find();
            this.id.set(s.group(0));
        } else {
            throw new NullPointerException("Некорректный ввод");
        }
        } catch (IllegalStateException e) {
            throw new NullPointerException();
        }
        Document doc;
        String title = "";
        try {
            try {
                doc = Jsoup.connect(link).get();
                title = doc.title();
            } catch (MalformedURLException e) {
                throw new NullPointerException("Некорректный ввод");
            }
        } catch (IOException e) {
            throw new NullPointerException("Некорректный ввод");
        }
        if (this.getSourceString().equals("youtube") && !title.isEmpty())
            this.name.set(title.substring(0, title.length() - 10));
        else if (this.getSourceString().equals("spotify") && !title.contains("Web Player")) {
            if (title.contains("Album"))
                this.name.set(title.substring(0, title.length() - 10));
            if (title.contains("Spotify Playlist"))
                this.name.set(title.substring(0, title.length() - 19));
        } else if (this.getSourceString().equals("yandex")) {
            if (title.contains("плейлист на Яндекс.Музыке"))
                this.name.set(title.substring(0, title.length() - 28));
            if (title.contains("Слушать онлайн на Яндекс.Музыке"))
                this.name.set(title.substring(0, title.length() - 32));
        } else {
            throw new NullPointerException("Некорректный ввод");
        }
    }

    public String getHTML() {
        if (this.getSourceString().equals("youtube")) {
            return ("<html><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/videoseries?list=" + this.getIdString() + "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe></html>");

        }
        if (this.getSourceString().equals("spotify")) {
            return ("<html><iframe src=\"https://open.spotify.com/embed/" + this.getIdString() + "\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe></html>");

        } else {
            if (this.getIdString().contains("playlists"))
                return ("<html><iframe frameborder=\"0\"style=\"border:none;width:100%;height:100%;\"width=\"100%\"height=\"100%\"src=\"https://music.yandex.ru/iframe/#playlist" + this.getIdString().replaceAll("/playlists/", "/").substring(5) + "\"></iframe></html>");
            else
                return ("<html><iframe frameborder=\"0\" style=\"border:none;width:100%;height:100%;\" width=\"100%\" height=\"100%\" src=\"https://music.yandex.ru/iframe/#album" + this.getIdString().replaceAll("album/", "/") + "\"></iframe></html>");
        }
    }
}