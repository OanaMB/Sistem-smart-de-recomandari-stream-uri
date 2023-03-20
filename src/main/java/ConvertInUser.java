import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// clasa de strategie pentru user
public class ConvertInUser implements Converter{
    @Override
    public void convertStringArrayToObjectList(List<String[]> user) {
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        for (String[] strings : user) {
            // create an integer list from the string array left
            List<Integer> userStreams = getIntegers(strings);
            User user1 = new User(Integer.parseInt(strings[0]), strings[1], userStreams);
            proiectPOO.getUsers().add(user1);
        }
    }

    private static List<Integer> getIntegers(String[] strings) {
        String[] userStreamsAux = Arrays.copyOfRange(strings, 2, strings.length);
        String[] userStreamsAux2 = userStreamsAux[0].split(" ");
        int[] userStreams = Arrays.stream(userStreamsAux2).mapToInt(Integer::parseInt).toArray();
        List<Integer> stream  = Arrays.stream( userStreams ).boxed().collect( Collectors.toList() );
        return stream;
    }
}
