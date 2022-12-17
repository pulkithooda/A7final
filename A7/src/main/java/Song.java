import java.sql.ResultSet;
import java.sql.SQLException;

public class Song extends Entity
{
    protected Album album;
    protected Artist performer;
    protected SongInterval duration;
    protected String genre;
    protected String name;
    protected int likes;

    protected String mood;


    public Song(String name)
    {
        super(name);
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        likes=0;
        mood="";
    }
    public Song(String name, int length)
    {
        super(name);
        duration = new SongInterval(length);
        genre = "";
        likes=0;
        mood="";
    }

    public Song()
    {
        name=getName();
        album = new Album("");
        performer = new Artist("");
        duration = new SongInterval(0);
        genre = "";
        likes=0;
        mood="";
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        duration = new SongInterval(length);
    }

    public String showLength() {
        return duration.toString();
    }

    public int getLikes(){return likes;}

    public void setLikes(int likes){this.likes=likes;}

    public String getMood(){return mood;}

    public void setMood(String mood){this.mood=mood;}

    protected Album getAlbum() {
        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getPerformer() {
        return performer;
    }

    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    public String toString()
    {
        return super.toString() + " " + this.performer + " " + this.album + " " + this.duration;
    }

    /**
     * created SQL command
     * @return SQL command to add to DB
     */
    public String toSQL()
    {
        return "insert into song (id, name, album, artist) values (" + this.entityID + ", " + this.name + ", " + album.entityID + ", "
                + performer.entityID  + ");";
    }

    /**
     * gets info from SQL database and stores in ResultSet
     * @param rs
     */
    public void fromSQL(ResultSet rs) {
        try
        {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
        }
        catch(SQLException e)
        {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }
}

