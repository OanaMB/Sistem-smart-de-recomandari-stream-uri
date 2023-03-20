import java.util.List;
// clasa Factory
public class Factory {
    // metoda care returneaza tipul de utilizator in functie de id-ul utilizatorului
    public String findTipClient(List<Streamers> streamers, List<User> users, String idClient) {
        for (Streamers streamer : streamers) {
            int id = Integer.parseInt(idClient);
            if (streamer.getId().equals(id)) {
                return "Streamer";
            }
        }
        for (User user : users) {
            int id = Integer.parseInt(idClient);
            if (user.getId().equals(id)) {
                return "User";
            }
        }
        return null;
    }

    // metodele care returneaza streamer-ul sau user-ul in functie de id-ul utilizatorului
    private Streamers returneazaStreamer(List<Streamers> streamers, String idUtilizator) {
        for (Streamers streamer : streamers) {
            int id = Integer.parseInt(idUtilizator);
            if (streamer.getId().equals(id)) {
                return streamer;
            }
        }
        return null;
    }

    private User returneazaUser(List<User> users, String idUtilizator) {
        for (User user : users) {
            int id = Integer.parseInt(idUtilizator);
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // metoda care creeaza utilizatorul
    public Client creeazaClient(String tipClient, String idClient){
        switch (tipClient) {
            case "Streamer":
                return returneazaStreamer(ProiectPOO.getInstance().getStreamers(), idClient);
            case "User":
                return returneazaUser(ProiectPOO.getInstance().getUsers(), idClient);
            default:
                return null;
        }
    }
}
