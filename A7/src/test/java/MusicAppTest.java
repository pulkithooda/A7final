import junit.framework.TestCase;

public class MusicAppTest extends TestCase
{
    MusicApp ma;
    Artist gunna;
    Album DS3;
    String ds3song;

    public void setUp() throws Exception {
        super.setUp();
        ma=new MusicApp();
        gunna=new Artist("gunna");
        DS3=new Album("Drip Season 3");
        ds3song="Car Sick";
    }


    public void testAddArtistToDB() {
    }

    public void testArtistAlbumList()
    {
        //this method only works occasionally not sure why
        ma.artistAlbumList("gunna");
    }

    public void testAddAlbumToDB()
    {

    }

    public void testCreateSongToAdd()
    {
        Song s=ma.createSongToAdd(gunna,DS3,ds3song);
        System.out.println(s.getName());
    }

    public void testRunApp() {
    }


}