import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;

    public Album(String name) {
        super(name);
    }

    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }

    public boolean equalsAl(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * created SQL command
     * @return SQL command to add to DB
     */
    public String toSQL() {
        return "insert into album (id, name, artist, nSong) values (" + this.entityID + ", " + this.name + ", " + this.artist.entityID + ", "
                + this.songs.size()  + ");";
    }
    /**
     * gets info from SQL database and stores in ResultSet
     * @param rs
     */
    public void fromSQL(ResultSet rs) {
        try {
            this.entityID = rs.getInt("id");
            this.name = rs.getString("name");
            this.artist.entityID=rs.getInt("artist");
        } catch (SQLException e) {
            System.out.println("SQL Exception" + e.getMessage());
        }
    }
}
