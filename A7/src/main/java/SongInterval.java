public class SongInterval
{
    private int length;

    public SongInterval(int length)
    {
        this.length=length;
    }

    public String toString()
    {
        int minutes=length/60;
        int seconds=length%60;
        return String.format("%d:%d",minutes,seconds);
    }
}
