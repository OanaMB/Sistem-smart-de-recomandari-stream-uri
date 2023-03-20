import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Streamers extends Client{
    private Integer streamerType;
    private Integer id;
    private String name;

    public Streamers() {
    }

    public Streamers(Integer streamerType, Integer id, String name) {
        this.streamerType = streamerType;
        this.id = id;
        this.name = name;
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

    /* adaugam un stream in lista de stream-uri folosindu-ne de builder
     de asemenea adaugam streamul la data de 13.01.2023 de aceea calculam diferenta dintre cele doua date */
    public void addStream(String[] arguments) {
        String[] argument = this.toOneString(arguments);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 01, 01);
        Date date1 = calendar.getTime();
        calendar.set(2023, 01, 13);
        Date date2 = calendar.getTime();

        Streams streams = new StreamsBuilder()
               .withStreamType(Integer.parseInt(argument[2]))
               .withId(Integer.parseInt(argument[3]))
               .withStreamGenre(Integer.parseInt(argument[4]))
               .withStreamerId(Integer.parseInt(argument[0]))
               .withLength(Long.parseLong(argument[5]))
               .withName(argument[6])
                .withDateAdded((date2.getTime() - date1.getTime())/1000)
                .withNoOfStreams(0L)
               .build();
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        proiectPOO.getStreams().add(streams);
    }

    // pentru titlul streamului care poate avea spatii, le adaugam in acelasi string
    private String[] toOneString(String[] arguments){
        for(int i = 7; i < arguments.length; i++) {
            arguments[6] = arguments[6] + " " + arguments[i];
        }
        return arguments;
    }

    // stergem un stream din lista de streamuri si din lista de streamuri ale utilizatorilor
    public void deleteStream(String[] arguments) {
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        List<Streams> streams = proiectPOO.getStreams();
        Streams stream = streams.stream().filter(stream1 -> stream1.getId().intValue() == Integer.parseInt(arguments[2])).findFirst().get();
        streams.remove(stream);

        // cautam streamul in lista de streamuri ale utilizatorilor si il stergem
        List<User> utilizatori = proiectPOO.getUsers();
        for (User utilizator : utilizatori) {
            utilizator.getStreams().remove(stream);
        }
    }

    // afisam stream-urile unui streamer in format json
    @Override
    public void list(ProiectPOO proiectPOO, String streamerId) {
        List<Streams> streams = proiectPOO.getStreams();
        // selectam streamurile unui streamer
        List<Streams> streamsOfStreamer = Arrays.asList(streams.stream().filter(stream -> stream.getStreamerId().intValue() == this.getId().intValue()).toArray(Streams[]::new));
        String streamerName = proiectPOO.getStreamers().stream().filter(streamer -> streamer.getId().intValue() == this.getId().intValue()).findFirst().get().getName();

        System.out.print("[");
        for (Streams stream : streamsOfStreamer) {
             System.out.print("{\"id\":\"" + stream.getId() + "\",\"name\":\"" + stream.getName() + "\",\"streamerName\":\"" + streamerName + "\",\"noOfListenings\":\"" + stream.getNoOfStreams() + "\",\"length\":\"" + DurationConverter.convertDuration(stream.getLength())+ "\",\"dateAdded\":\"" +DurationConverter.convertDate(stream.getDateAdded())+ "\"}");
            if (streamsOfStreamer.indexOf(stream) != streamsOfStreamer.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }

}
