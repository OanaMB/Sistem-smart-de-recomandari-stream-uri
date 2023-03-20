import java.util.*;
import java.util.stream.Collectors;

public class User extends Client {
    private Integer id;
    private String name;
    private List<Integer> streams;

    public User() {
    }
    public User(Integer id, String name, List<Integer> streams) {
        this.id = id;
        this.name = name;
        this.streams = streams;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Integer> getStreams() {
        return streams;
    }

    // afisam lista de stream-uri pe care le-a ascultat user-ul in format json
    @Override
    public void list(ProiectPOO proiectPOO, String arguments) {
        List<Streams> streams = proiectPOO.getStreams();
        // cautam streamurile user-ului in lista de streamuri
        List<Streams> streams1 = new ArrayList<>();
        for(Integer streamId : this.getStreams()){
            streams1.add(streams.stream().filter(stream1 -> stream1.getId().equals(streamId)).findFirst().get());
        }

        System.out.print("[");
        for (Streams stream : streams1) {
            String streamerName = proiectPOO.getStreamers().stream().filter(streamer -> streamer.getId().equals(stream.getStreamerId())).findFirst().get().getName();
             System.out.print("{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + streamerName + "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"" + DurationConverter.convertDuration(stream.getLength())+ "\",\"dateAdded\":\"" +DurationConverter.convertDate(stream.getDateAdded())+ "\"}");
            if (streams1.indexOf(stream) != streams1.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    // adaugam stream-ul in lista de stream-uri pe care le-a ascultat user-ul si incrementam numarul de ascultari al stream-ului
    public void listen(ProiectPOO proiectPOO, String[] arguments) {
        Integer userId = Integer.parseInt(arguments[0]);
        Integer streamId = Integer.parseInt(arguments[2]);
        List<User> users = proiectPOO.getUsers();

        for (User user : users) {
            if(user.getId().equals(userId)){
                user.getStreams().add(streamId);
            }
        }

        List<Streams> streams = proiectPOO.getStreams();
        for (Streams stream : streams) {
            if(stream.getId().equals(streamId)){
                Long noOfStreams = stream.getNoOfStreams();
                noOfStreams++;
                stream.setNoOfStreams(noOfStreams);
            }
        }

    }

    // afisam stream-urile recomandate in format json in functie de tipul stream-ului
    // sortam stream-urile in functie de numarul de ascultari si afisam primele 5 stream-uri ale streamer-ului daca le are
    public void recommend(ProiectPOO proiectPOO, String[] arguments) {
        List<Streams> streams = proiectPOO.getStreams();
        Collections.sort(streams, new ComparatorCustom());
        List<Streams> streams1 = null;

        int type = 0;
        switch (arguments[2]) {
            case "SONG":
                type = 1;
                streams1 = getStreams(streams, type);
                break;
            case "PODCAST":
                type = 2;
                streams1 = getStreams(streams, type);
                break;
            case "AUDIOBOOK":
                type = 3;
                streams1 = getStreams(streams, type);
                break;
        }
        Collections.sort(streams1, new ComparatorCustom());

        int index = 0;
        System.out.print("[");
        for (Streams stream : streams1) {
            if (index == 5) {
                break;
            }
            String streamerName = proiectPOO.getStreamers().stream().filter(streamer -> streamer.getId().equals(stream.getStreamerId())).findFirst().get().getName();
            System.out.print("{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + streamerName + "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"" + DurationConverter.convertDuration(stream.getLength())+ "\",\"dateAdded\":\"" +DurationConverter.convertDate(stream.getDateAdded())+ "\"}");
            if (index != 4) {
                System.out.print(",");
            }
            index++;
        }
        System.out.println("]");

    }

    // creeaza o sublista a unei liste de stream-uri in functie de tipul stream-ului si de streamer-ul care a creat stream-ul
    private List<Streams> getStreams(List<Streams> streams, int type) {
        List<Streams> streams1 = new ArrayList<>();
        for(Integer streamId : this.getStreams()) {
         Integer streamerId = streams.stream().filter(stream1 -> stream1.getId().intValue()==streamId.intValue()).findFirst().get().getStreamerId();
            for (Streams stream : streams) {
                if((stream.getId().intValue() != streamId.intValue()) && (stream.getStreamType() == type) && (stream.getStreamerId() == streamerId)) {
                    streams1.add(stream);
                }
            }
        }
        return streams1;
    }

    // creeaza o sublista a unei liste de stream-uri in functie de tipul stream-ului
    private List<Streams> getSubList(List<Streams> streams, int type){

        List<Streams> filteredList = streams.stream()
                .filter(s -> s.getStreamType().equals(type))
                .collect(Collectors.toList());
        return filteredList;
    }

    // sorteaza o lista de stream-uri in functie de numarul de ascultari si de data adaugarii
    // si recomanda primele 5 stream-uri din lista
    public void surprise (ProiectPOO proiectPOO, String[] arguments) {
        List<Streams> streams = proiectPOO.getStreams();
        List<Streams> streams1 = null;
        int type = 0;
        switch (arguments[2]) {
            case "SONG":
                type = 1;
                streams1 = this.getSubList(streams, type);
                break;
            case "PODCAST":
                type = 2;
                streams1 = this.getSubList(streams, type);
                break;
            case "AUDIOBOOK":
                type = 3;
                streams1 = this.getSubList(streams, type);
                break;
        }
        sorting(streams1);

        List<Streams> streams2 = new ArrayList<>();
        for(Integer streamId : this.getStreams()){
            Integer streamerId = streams1.stream().filter(stream2 -> stream2.getId().equals(streamId)).findFirst().get().getStreamerId();
            for (Streams stream : streams1) {
                if(stream.getId().intValue() != streamId.intValue() && stream.getStreamerId() != streamerId){
                    streams2.add(stream);
                }
            }
        }
        sorting(streams2);

        System.out.print("[");
        for (Streams stream : streams2) {
            String streamerName = proiectPOO.getStreamers().stream().filter(streamer -> streamer.getId().equals(stream.getStreamerId())).findFirst().get().getName();
            System.out.print("{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + streamerName + "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"" + DurationConverter.convertDuration(stream.getLength())+ "\",\"dateAdded\":\"" +DurationConverter.convertDate(stream.getDateAdded())+ "\"}");
            if (streams2.indexOf(stream) != streams2.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

    // sorteaza o lista de stream-uri in functie de numarul de ascultari si de data adaugarii
    private static void sorting(List<Streams> streams1) {
        Collections.sort(streams1, new Comparator<Streams>() {
                public int compare(Streams stream1, Streams stream2) {

            if(DataConverter.getDate(DurationConverter.convertDate(stream1.getDateAdded())).before(DataConverter.getDate(DurationConverter.convertDate(stream2.getDateAdded())))) {
                return -1;
            } else if(DataConverter.getDate(DurationConverter.convertDate(stream1.getDateAdded())).after(DataConverter.getDate(DurationConverter.convertDate(stream2.getDateAdded())))) {
                return 1;
            } else if (stream1.getNoOfStreams() > stream2.getNoOfStreams()) {
                return -1;
            } else {
                return 1;
            }
        }
        });
    }

}
