import java.io.*;
import java.util.*;

public class ProiectPOO {
    private List<Streamers> streamers;
    private List<Streams> streams;
    private List<User> users;
    private static ProiectPOO instance;
    private ProiectPOO() {
        streamers = new ArrayList<>();
        streams = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<Streamers> getStreamers() {
        return streamers;
    }
    public List<Streams> getStreams() {
        return streams;
    }
    public List<User> getUsers() {
        return users;
    }

    // singleton pattern
    public static ProiectPOO getInstance() {
        if (instance == null) {
            instance = new ProiectPOO();
        }
        return instance;
    }

    /* initalizam variabilele de tipul file, initializam cele 3 arraylist-uri cu functia initializeData,
      arraylist-urile se vor comporta precum o baza de date,
      citim cele 3 fisiere csv si fisierul text de comenzi, si eliberam cele  3 list-uri la final */
    public static void main(String[] args) {
        if(args == null) {
            System.out.println("Nothing to read here");
            return;
        }
        File streamers = new File("src/main/resources/" + args[0]);
        File streams = new File("src/main/resources/" + args[1]);
        File users = new File( "src/main/resources/" +args[2]);
        File fileCommands = new File("src/main/resources/" + args[3]);

        // am folosit singleton pattern
        ProiectPOO proiect = getInstance();

        initializeData(streamers, streams, users, proiect);
        try {
            FilesFunctions.readTextFiles(fileCommands);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        proiect.getUsers().removeAll(proiect.getUsers());
        proiect.getStreamers().removeAll(proiect.getStreamers());
        proiect.getStreams().removeAll(proiect.getStreams());
    }

    /* citim fiecare fisier csv si cream un context, care va executa strategia corespunzatoare fiecarui fisier/ fiecarei
    de a converti un list de string intr-un list de obiecte Streamers, Streams, User, folosim design pattern-ul strategy */
    private static void initializeData(File streamers, File streams, File users, ProiectPOO proiect) {
        try {
            // streamers
             List<String[]> streamerAux = FilesFunctions.readCSVFiles(streamers);
             // strategy pattern
             Context context = new Context(new ConvertInStreamers());
             context.executeStrategyToConvert(streamerAux);
             Collections.reverse(proiect.streamers);
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
        try {
            // streams
            List<String[]> streamAux = FilesFunctions.readCSVFiles(streams);
            // strategy pattern
            Context context = new Context(new ConvertInStreams());
            context.executeStrategyToConvert(streamAux);
            Collections.reverse(proiect.streams);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            // users
            List<String[]> userAux = FilesFunctions.readCSVFiles(users);
            // strategy pattern
            Context context = new Context(new ConvertInUser());
            context.executeStrategyToConvert(userAux);
            Collections.reverse(proiect.users);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /* executam functiile */
    public void functions(String[] arguments) throws IOException {
        ProiectPOO proiectPOO = ProiectPOO.getInstance();
        switch (arguments[1]) {
            case "ADD":
                Streamers streamer = new Streamers();
                streamer.addStream(arguments);
                break;
            case "LIST":
                Factory factory = new Factory();
                String tipUtilizator = factory.findTipClient(proiectPOO.getStreamers(),proiectPOO.getUsers(),arguments[0]);
                Client utilizator = factory.creeazaClient(tipUtilizator, arguments[0]);
                utilizator.list(proiectPOO, arguments[0]);
                break;
            case "DELETE":
                Streamers streamer1 = new Streamers();
                streamer1.deleteStream(arguments);
                break;
            case "LISTEN":
                User users = new User();
                users.listen(proiectPOO,arguments);
                break;
            case "RECOMMEND":
                List<User> user = proiectPOO.getUsers();
                String idUser = arguments[0];
                // cautam user-ul cu id-ul respectiv
                for (User user2 : user) {
                    if (user2.getId().equals(Integer.valueOf(idUser))) {
                        user2.recommend(proiectPOO,arguments);
                        break;
                    }
                }
                break;
            case "SURPRISE":
             /* List<User> user5 = proiectPOO.getUsers();
                String idUser2 = arguments[0];
                // look for the user with the id
                for (User user3 : user5) {
                    if (user3.getId().equals(Integer.valueOf(idUser2))) {
                        user3.surprise(proiectPOO,arguments);
                        break;
                    }
                }
                */
                break;
    }
    }

}
