package player;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "playlists")
public class PlaylistList {
    private List<Playlist> youtubeList;
    private List<Playlist> yandexList;
    private List<Playlist> spotifyList;

    @XmlElement(name = "youtube_playlist")
    public List<Playlist> getYoutube() {
        return youtubeList;
    }

    @XmlElement(name = "yandex_playlist")
    public List<Playlist> getYandex() {
        return yandexList;
    }

    @XmlElement(name = "spotify_playlist")
    public List<Playlist> getSpotify() {
        return spotifyList;
    }

    public void setYoutube(List<Playlist> youtubeList){
        this.youtubeList=youtubeList;
    }
    public void setYandex(List<Playlist> yandexList){
        this.yandexList=yandexList;
    }
    public void setSpotify(List<Playlist> spotifyList){
        this.spotifyList=spotifyList;
    }
}
