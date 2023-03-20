import java.util.List;

// clasa de strategie pentru streamers
public class ConvertInStreamers implements Converter{
    @Override
    public void convertStringArrayToObjectList(List<String[]> streamer) {
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        for (String[] strings : streamer) {
            Streamers streamer1 = new Streamers(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), strings[2]);
            proiectPOO.getStreamers().add(streamer1);
        }
    }
}
