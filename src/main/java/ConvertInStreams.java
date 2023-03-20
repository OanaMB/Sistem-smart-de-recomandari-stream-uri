import java.util.List;

// clasa de strategie pentru streams
public class ConvertInStreams implements Converter{
    @Override
    public void convertStringArrayToObjectList(List<String[]> stream) {
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        for (String[] strings : stream) {
            Streams stream1 = new Streams(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Long.parseLong(strings[3]), Integer.parseInt(strings[4]), Long.parseLong(strings[5]), Long.parseLong(strings[6]), strings[7]);
            proiectPOO.getStreams().add(stream1);
        }
    }
}
