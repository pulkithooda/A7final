import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MusicApp
{
    static Library mainLibrary=new Library();
    int artistID=1;
    int albumID=1;
    int songID=1;

    ArrayList<Playlist> userPlaylists;

    /**
     * this method takes the user inputted artist and adds them to the database
     * @param a
     */
    public void addArtistToDB(Artist a) {
        String SQLcommand = "";
        a.entityID = artistID;
        artistID++;
        SQLcommand = a.toSQL();
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:final.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(SQLcommand);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * From the user inputted artist name this method will first get the mbid of the artist and then use
     * that mbid to display the artist's albums for the user to input
     * @param artist
     */
    public static Artist artistAlbumList(String artist)
    {
        String initialURL = "https://musicbrainz.org/ws/2/artist?query="+artist+"&fmt=xml";
        Artist a = new Artist(artist);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            u.setRequestProperty("User-Agent", "A7/1.0 (phooda@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            NodeList artists = doc.getElementsByTagName("artist-list");
            Node artistNode = artists.item(0).getFirstChild();
            Node artistIDNode = artistNode.getAttributes().getNamedItem("id");
            String mbid = artistIDNode.getNodeValue();

            String lookupURL ="https://musicbrainz.org/ws/2/release-group?artist="+mbid+"&type=album|ep";
            URLConnection u2 = new URL(lookupURL).openConnection();
            u2.setRequestProperty("User-Agent", "A7/1.0 (phooda@dons.usfca.edu");

            db = dbf.newDocumentBuilder();
            doc = db.parse(u2.getInputStream());

            NodeList albumList = doc.getElementsByTagName("title");

            ArrayList<Album> albumCollection=new ArrayList<>();
            System.out.println("Here is a list of albums by "+artist);
            for (int i = 0; i < albumList.getLength(); i++)
            {
                Album albums=new Album(albumList.item(i).getFirstChild().getNodeValue());
                albumCollection.add(albums);
                System.out.println(albumList.item(i).getFirstChild().getNodeValue());
            }
            a.setAlbums(albumCollection);


        }
        catch (Exception ex)
        {
            System.out.println("XML parsing error " + ex);
        }
        return a;
    }

    /**
     * This method will create the album object that the user wants,add it to the database and return the object
     * that was created
     * @param album
     * @return al - album to associate with artist and song
     */
    public void addAlbumToDB(Album album)
    {
        String SQLcommand = "";
        album.entityID=albumID;
        albumID++;
        SQLcommand=album.toSQL();
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:final.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(SQLcommand);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * This method will take the song name input from the user along with the objects for the Artist and Album that
     * were previously created from user input and make a song object and add all that information to the database
     * @param artist
     * @param album
     * @param song
     * @return Song
     */
    public Song createSongToAdd(Artist artist, Album album, String song)
    {
        Song newSong=new Song("");
        newSong.setName(song);
        newSong.setAlbum(album);
        newSong.setPerformer(artist);

        String SQLcommand = "";

        newSong.entityID=songID;
        songID++;
        SQLcommand=newSong.toSQL();
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:final.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(SQLcommand);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return newSong;
    }

    /**
     * Runs the music library and general UI
     */
    public void runApp()
    {
        Song newSong;
        Artist newArtist;
        boolean addSongToPlaylist=true;
        boolean addMoreSongs=true;
        Scanner sc=new Scanner(System.in);
        String input,artist,album,song;
        //Song testSong=new Song("Dior");
        //mainLibrary.addSong(testSong);
        System.out.println("Welcome to your music library!");
        System.out.println("If you want to access to your music library type library, if you want to create a playlist type playlist");
        input=sc.nextLine();
        if(input.equalsIgnoreCase("library"))
        {
            System.out.println("Do you want to add to the library or view it?(add/view)");
            input=sc.nextLine();
            if (input.equalsIgnoreCase("add"))
            {
                while (addMoreSongs==true)
                {
                    System.out.println("Which artist performed the song you want to add?");
                    artist = sc.nextLine();
                    newArtist = artistAlbumList(artist);
                    addArtistToDB(newArtist);

                    System.out.println("Which album would you like to add?");
                    album = sc.nextLine();
                    Album tempAlbum = new Album("");
                    for (Album a : newArtist.getAlbums()) {
                        if (a.getName().equalsIgnoreCase(album)) {
                            addAlbumToDB(a);
                            tempAlbum = a;
                        }
                    }

                    //if can get song list from xml would like to display that as well
                    System.out.println("Which song?");
                    song = sc.nextLine();
                    newSong = createSongToAdd(newArtist, tempAlbum, song);
                    mainLibrary.addSong(newSong);
                    System.out.println("Would you like to add another song?(Y/N)");
                    input=sc.nextLine();
                    if (input.equalsIgnoreCase("N"))
                    {
                        addMoreSongs=false;
                    }
                }
            }
            else if(input.equalsIgnoreCase("view"))
            {
                System.out.println("Here are the songs currently present in your music library");
                for (Song s:mainLibrary.getSongs())
                {
                    System.out.println(s.getName());
                }
            }
            else
            {
                System.out.println("error");
            }

        }
        else if (input.equalsIgnoreCase("playlist"))
        {
            System.out.println("What would you like to name your playlist?");
            input=sc.nextLine();
            Playlist p=new Playlist();
            p.setPlaylistTitle(input);
            String songToAddToPlaylist="";
            String addAnotherSong;
            System.out.println("Now let's add some songs!");
            while(addSongToPlaylist==true)
            {
                System.out.println("Which song from your library do you want to add, here is a list:");
                for(Song s:mainLibrary.songs)
                {
                    System.out.println(s.getName());
                }
                songToAddToPlaylist=sc.nextLine();
                for (Song s:mainLibrary.songs)
                {
                    if(s.getName().equalsIgnoreCase(songToAddToPlaylist))
                    {
                        p.addSong(s);
                    }
                }
                System.out.println("Do you want to add another song? (Y/N)");
                addAnotherSong=sc.nextLine();
                if (addAnotherSong.equalsIgnoreCase("N"))
                {
                    addSongToPlaylist=false;
                }
            }
            userPlaylists.add(p);
            System.out.println("Here is your new playlist in XML format");
            p.stringToXMLFile(p);
        }
        else
        {
            System.out.println("Incorrect input");
        }

    }

    /**
     * calls runApp()
     * @param args
     */
    public static void main(String[] args)
    {
        MusicApp musicApp=new MusicApp();
        musicApp.runApp();
    }
}
