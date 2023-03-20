import java.util.Comparator;

// comparator pentru sortarea dupa tipul de stream si numarul de streamuri
public class ComparatorCustom implements Comparator<Streams> {
    @Override
    public int compare(Streams a, Streams b) {
         if(a.getStreamType() != b.getStreamType()) {
            return a.getStreamType().compareTo(b.getStreamType());
        } else if(a.getStreamType() == b.getStreamType()) {
            return b.getNoOfStreams().compareTo(a.getNoOfStreams());
        }
        return 0;
    }
}
