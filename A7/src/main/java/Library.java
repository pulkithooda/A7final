import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library
{
    //make songs public for test methods to work
    public ArrayList<Song> songs;

    public Library() {
        songs = new ArrayList<Song>();
    }
    public boolean findSong(Song s) {
        return songs.contains(s);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public static String getContent(Node node)
    {
        StringBuilder stringBuilder=new StringBuilder();
        Node child=node.getFirstChild();
        stringBuilder.append(child.getNodeValue());
        return stringBuilder.toString();
    }

    /**
     * Given an XML file in the format of the XML file provided in assignment 6 it can parse and get each song name
     * and create the song object with its respective artist and album
     * @param filename
     */
    public void readFromXML(String filename)
    {
        try
        {
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(new File(filename));

            Element root=doc.getDocumentElement();
            NodeList song=root.getElementsByTagName("song");
            Node currentNode;
            Node subNode;
            Song currentSong;
            for (int i=0;i<song.getLength();i++)
            {
                currentNode=song.item(i);
                NodeList children=currentNode.getChildNodes();
                currentSong=new Song("");
                for(int j=0;j<children.getLength();j++)
                {
                    subNode=children.item(j);
                    if(subNode.getNodeType()==Node.ELEMENT_NODE)
                    {
                        if(subNode.getNodeName().equals("title"))
                        {
                            currentSong.setName(getContent(subNode));
                            //System.out.println(currentSong.getName());
                        }
                        else if (subNode.getNodeName().equals("artist"))
                        {
                            Artist currentArtist=new Artist(getContent(subNode));
                            currentSong.setPerformer(currentArtist);
                            //System.out.println(currentSong.getPerformer());
                        }
                        else if (subNode.getNodeName().equals("album"))
                        {
                            Album currentAlbum=new Album(getContent(subNode));
                            currentSong.setAlbum(currentAlbum);
                            //System.out.println(currentSong.getAlbum());
                        }
                    }
                }
                songs.add(currentSong);
                //System.out.println(songs.get(i));
            }
        }
        catch (SAXException | IOException | ParserConfigurationException e)
        {
            System.out.println("error");
        }
    }

    /**
     * Given an JSON file in the format of the JSON file provided in assignment 6 it can parse and get each song name
     * and create the song object with its respective artist and album
     * @param filename
     */
    public void readFromJSON(String filename)
    {
        String s;
        try
        {
            Scanner scanner=new Scanner(new File(filename));
            scanner.useDelimiter("\\Z");
            s=scanner.next();
            JSONParser parser=new JSONParser();
            Object obj=parser.parse(s);
            JSONObject jsonObject=(JSONObject) obj;

            JSONArray songArray=(JSONArray)jsonObject.get("songs");
            for(Object song:songArray)
            {
                Song sonG=new Song("");
                JSONObject jsong=(JSONObject)song;
                sonG.setName((String) jsong.get("title"));
                String artistName=jsong.get("artist").toString();
                Artist artisT=new Artist(artistName);
                sonG.setPerformer(artisT);
                String albumTitle=jsong.get("album").toString();
                Album albuM=new Album(albumTitle);
                sonG.setAlbum(albuM);
                songs.add(sonG);
            }
        }
        catch (FileNotFoundException | ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * This can method can detect if there are duplicate songs in the library and remove if the user wants to do so
     */
    public void findDuplicates()
    {
        Scanner scanner=new Scanner(System.in);
        String shouldDelete;
        for(int i=0;i<songs.size()-1;i++)
        {
            Song songBeingChecked;
            songBeingChecked=songs.get(i);
            //System.out.println(i+songBeingChecked.getName());
            boolean sameName=false;
            boolean sameArtist=false;
            boolean sameAlbum=false;
            for(int j=i+1;j<songs.size();j++)
            {
                Song songBeingCompared;
                songBeingCompared=songs.get(j);
                //System.out.println(j+songBeingCompared.getName());
                if(songBeingChecked.getName().equals(songBeingCompared.getName()))
                {
                    sameName=true;
                    System.out.println("sameName val changed");
                }
                if(songBeingChecked.getPerformer().equals(songBeingCompared.getPerformer()))
                {
                    sameArtist=true;
                    System.out.println("sameArtist val changed");
                }
                if(songBeingChecked.getAlbum().equals(songBeingCompared.getAlbum()))
                {
                    sameAlbum=true;
                    System.out.println("sameAlbum val changed");
                }
                if (sameName==true&&sameArtist==true&&sameAlbum==true)
                {
                    System.out.println("this song is definetly a duplicate, do you want to delete it?(Y/N)");
                    shouldDelete=scanner.nextLine();
                    if(shouldDelete.equalsIgnoreCase("Y"))
                    {
                        songs.remove(songBeingChecked);
                        break;
                    }
                    break;
                }
                if(sameName==true&&(sameArtist==true||sameAlbum==true))
                {
                    System.out.println("this song is possibly a duplicate,do you want to delete it?(Y/N)");
                    shouldDelete=scanner.nextLine();
                    if(shouldDelete.equals("Y"))
                    {
                        songs.remove(songBeingChecked);
                        break;
                    }
                    break;
                }
                if (sameArtist==true&&sameAlbum==true&&(songBeingChecked.getName().equalsIgnoreCase(songBeingCompared.getName())))
                {
                    System.out.println("this song is possibly a duplicate,do you want to delete it?(Y/N)");
                    shouldDelete=scanner.nextLine();
                    if(shouldDelete.equals("Y"))
                    {
                        songs.remove(songBeingChecked);
                        break;
                    }
                    break;
                }
            }
        }
    }

    /**
     * Will take the Library and write its info to an XML file
     * @param l
     */
    public void stringToXMLFile(Library l)
    {
        String start="<library>"+"<songs>";
        String end="</library>"+"</songs>";
        String result;

        //https://www.geeksforgeeks.org/java-program-to-write-into-a-file/ - this article used to help write to file
        try
        {
            FileWriter fw = new FileWriter("XML.xml");
            result=start;
            for(Song s: l.songs)
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
     * Will take the Library and write its info to a JSON file
     * @param l
     */
    public void stringToJSONFile(Library l)
    {
        String start="{\"songs\": [";
        String end="]}";
        String result;
        //https://www.geeksforgeeks.org/java-program-to-write-into-a-file/ - this article used to help write to file
        try
        {
            FileWriter fw = new FileWriter("JSON.json");
            result=start;
            for(Song s: l.songs)
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