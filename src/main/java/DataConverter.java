import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// folosita pentru sortarea dupa data in metoda surprise si sorting
public class DataConverter {
    public static Date getDate(String data_string) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date data = null;
        try {
            data = formatter.parse(data_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
}
