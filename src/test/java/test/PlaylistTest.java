package test;

import org.junit.Test;
import player.Playlist;

import static org.junit.Assert.*;

public class PlaylistTest {
    @Test
    public void easyTest() {
        Playlist test = new Playlist("PLxB9p8WAdj0L5LT_UYU_fTmtGmqj8NdVW", "youtube", "higurashi");
        test.setName("MASA hardcore");
        test.setSource("spotify");
        test.setLink("album/69uC7FeKhctSuVlfz3eAay");
        assertEquals("album/69uC7FeKhctSuVlfz3eAay", test.getLink());
        assertEquals("spotify", test.getSource());
        assertEquals("MASA hardcore", test.getName());
        assertEquals(380, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void YoutubeTest() {
        Playlist test = new Playlist();
        test.setByLink("https://www.youtube.com/playlist?list=PLxB9p8WAdj0L5LT_UYU_fTmtGmqj8NdVW");
        assertEquals("https://www.youtube.com/embed/videoseries?list=PLxB9p8WAdj0L5LT_UYU_fTmtGmqj8NdVW", test.getLink());
        assertEquals("youtube", test.getSource());
        assertEquals("ひぐらしのなく頃に奉 挿入ムービー", test.getName());
        assertEquals(225, test.getHeight());
        assertEquals(400, test.getWidth());
    }

    @Test
    public void SpotifyAlbumTest() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/album/6fwF7V2ZL4jo7yJMIWeR8n?si=385ccff8d076412a&nd=1");
        assertEquals("https://open.spotify.com/embed/album/6fwF7V2ZL4jo7yJMIWeR8n?si=385ccff8d076412a&nd=1", test.getLink());
        assertEquals("spotify", test.getSource());
        assertEquals("ADULT - Album by -MASA Works DESIGEN-", test.getName());
        assertEquals(380, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void HardSpotifyAlbumTest() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/album/7wHEtuvZ9gQrLAgNGcFFp3?si=978551b9b7f448fd");
        assertEquals("https://open.spotify.com/embed/album/7wHEtuvZ9gQrLAgNGcFFp3?si=978551b9b7f448fd", test.getLink());
        assertEquals("spotify", test.getSource());
        assertEquals("ADULTRAZ - Album by -MASA Works DESIGEN-", test.getName());
        assertEquals(380, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void SpotifySingleTest() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/album/0c7bLm2cqhAWQ4BhovOUdm?_ga=2.189488744.237721332.1620998840-427048651.1620480118");
        assertEquals("https://open.spotify.com/embed/album/0c7bLm2cqhAWQ4BhovOUdm?_ga=2.189488744.237721332.1620998840-427048651.1620480118", test.getLink());
        assertEquals("spotify", test.getSource());
        assertEquals("Too Bizarre (with Swae Lee & Siiickbrain) - Single by Skrillex, Swae Lee, Siiickbrain", test.getName());
        assertEquals(380, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void YandexAlbumTest() {
        Playlist test = new Playlist();
        test.setByLink("https://music.yandex.ru/album/12493873");
        assertEquals("https://music.yandex.ru/iframe/#album/12493873", test.getLink());
        assertEquals("yandex", test.getSource());
        assertEquals("Внутри секты.", test.getName());
        assertEquals(450, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void YandexPlaylistTest() {
        Playlist test = new Playlist();
        test.setByLink("https://music.yandex.ru/users/yamusic-premiere/playlists/109950238");
        assertEquals("https://music.yandex.ru/iframe/#playlist/yamusic-premiere/109950238", test.getLink());
        assertEquals("yandex", test.getSource());
        assertEquals("Премьера", test.getName());
        assertEquals(450, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test
    public void SpotifyPlaylistTest() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/playlist/37i9dQZF1DX1bREA0Izw7V");
        assertEquals("https://open.spotify.com/embed/playlist/37i9dQZF1DX1bREA0Izw7V", test.getLink());
        assertEquals("spotify", test.getSource());
        assertEquals("Жизнь прекрасна", test.getName());
        assertEquals(380, test.getHeight());
        assertEquals(300, test.getWidth());
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest1() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/playlist/37i9dQZF1DX1bREA0Iz");
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest2() {
        Playlist test = new Playlist();
        test.setByLink("https://www.youtube.com/playlist?list=PLxB9p8WAdj0L5LT_UYU_fTmtGmqj8");
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest3() {
        Playlist test = new Playlist();
        test.setByLink("https://music.yandex.ru/users/yamusic-premiere/playlists/10995023834");
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest4() {
        Playlist test = new Playlist();
        test.setByLink("https://music.yandex.ru/album/1249387343");
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest5() {
        Playlist test = new Playlist();
        test.setByLink("https://open.spotify.com/album/7wHEtuvZ9gQrLAgNGcFFp354");
    }

    @Test(expected = NullPointerException.class)
    public void FalseLinkTest6() {
        Playlist test = new Playlist();
        test.setByLink("https://10th.madokamagicausa.com/");
    }

    @Test(expected = NullPointerException.class)
    public void BrokenLinkTest() {
        Playlist test = new Playlist();
        test.setByLink("https://10th.madokamagicausa.co/");
    }

    @Test(expected = NullPointerException.class)
    public void NotLinkTest6() {
        Playlist test = new Playlist();
        test.setByLink("magic");
    }
}
