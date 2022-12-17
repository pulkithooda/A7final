import java.util.*;
import java.util.Comparator;
import java.util.Collection;
import java.util.stream.Collectors;
import java.io.FileWriter;

public class Playlist
{
    //make songlist public to access in test class
    public ArrayList<Song> songlist;
    public String playlistTitle;


    public String getPlaylistTitle()
    {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle)
    {
        this.playlistTitle = playlistTitle;
    }

    public Playlist()
    {
        songlist=new ArrayList<Song>();
        playlistTitle="";
    }


    public void addSong(Song s)
    {
        songlist.add(s);
    }

    public void deleteSong(Song s)
    {
        if(songlist.contains(s))
        {
            songlist.remove(s);
        }
        else
        {
            System.out.printf("%s is not in the playlist\n",s.toString());
        }
    }

    /**
     * Given 2 playlists it will combine them and remove duplicates
     * @param playlist1
     * @param playlist2
     * @return combined Playlist
     */
    public static Playlist mergePlaylists(Playlist playlist1, Playlist playlist2)
    {
        Playlist comboPlaylist=new Playlist();
        for(Song s:playlist1.songlist)
        {
            comboPlaylist.addSong(s);
        }
        for(Song g: playlist2.songlist)
        {
            if(!(comboPlaylist.songlist.contains(g)))
            {
                comboPlaylist.addSong(g);
            }
        }
        return comboPlaylist;
    }

    /**
     * Will sort the playlist by likes from highest to lowest
     * @param pl
     * @return sorted playlist
     */
    public static Playlist sortByMostLiked(Playlist pl)
    {
        ArrayList<Song> tempList=new ArrayList<Song>(pl.songlist);

        //https://www.cloudhadoop.com/java-sort-objects-properties/ - this link used for the following block of code
        List<Song> sortedPlaylist=(ArrayList<Song>) tempList
                .stream().sorted(Comparator.comparing(Song::getLikes))
                .collect(Collectors.toList());

        //this puts the list is descending order
        Collections.reverse(sortedPlaylist);

        Playlist sortedPlaylistObject=new Playlist();
        sortedPlaylistObject.songlist=(ArrayList<Song>) sortedPlaylist;

        return sortedPlaylistObject;
    }

    /**
     * Will randomize order of given playlist
     * @param pl
     * @return shuffled playlist
     */
    public static Playlist shufflePlaylist(Playlist pl)
    {
        ArrayList<Song> tempList=new ArrayList<Song>(pl.songlist);
        Collections.shuffle(tempList);
        Playlist shuffledPlaylist=new Playlist();
        shuffledPlaylist.songlist=tempList;
        return shuffledPlaylist;
    }

    /**
     * From a library of songs this method can create a playlist based on the mood of each song
     * @param mood
     * @param size
     * @param l
     * @return playlist dependent on the mood
     */
    public static Playlist generateRandomPlaylist(String mood, int size, Library l)
    {
        Playlist randomMoodPlaylist=new Playlist();
        ArrayList<Song> allSongs=l.getSongs();
        while (randomMoodPlaylist.songlist.size()<size)
        {
            Collections.shuffle(allSongs);
            Song songToCheckMood=allSongs.get(0);
            if(songToCheckMood.getMood().equalsIgnoreCase(mood)&&!(randomMoodPlaylist.songlist.contains(songToCheckMood)))
            {
                randomMoodPlaylist.addSong(songToCheckMood);
            }
        }
        return randomMoodPlaylist;
    }

    /**
     * Will take a playlist and write in XML format to a file
     * @param pl
     */
    public void stringToXMLFile(Playlist pl)
    {
        String start="<library>"+"<songs>";
        String end="</library>"+"</songs>";
        String result;

        //https://www.geeksforgeeks.org/java-program-to-write-into-a-file/ - this article used to help write to file
        try
        {
            FileWriter fw = new FileWriter("playlist.xml");
            result=start;
            for(Song s: pl.songlist)
            {
                result=result+"<song><title>"+s.getName()+"</title><artist>"+s.getPerformer()+"</artist><album>"
                        +s.getAlbum()+"</album></song>";
            }
            result=result+end;
            fw.write(result);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("something wrong");
        }
    }

    /**
     * Will take a playlist and write in JSON format to a file
     * @param pl
     */
    public void stringToJSONFile(Playlist pl)
    {
        String start="{\"songs\": [";
        String end="]}";
        String result;
        //https://www.geeksforgeeks.org/java-program-to-write-into-a-file/ - this article used to help write to file
        try
        {
            FileWriter fw = new FileWriter("playlist.json");
            result=start;
            for(Song s: pl.songlist)
            {
                result=result+"{\"title\":\""+s.getName()+"<\",\"artist\":\""+s.getPerformer()+"\",\"album\":\""
                        +s.getAlbum()+"\"},";
            }
            result=result+end;
            fw.write(result);
            fw.close();
        }
        catch (Exception e)
        {
            System.out.println("something wrong");
        }
    }
}
