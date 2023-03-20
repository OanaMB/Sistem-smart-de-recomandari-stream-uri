import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DurationConverter {
    // conversia din milisecunde in ore, minute si secunde pentru campul length din clasa Streams
    public static String convertDuration(Long duration) {
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;

        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
           return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }

    // conversia din milisecunde in zile, luni si ani pentru campul dateAdded din clasa Streams
    public static String convertDate(long duration) {

        duration *= 1000;
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
        simpleDateFormat.setTimeZone(gmtTime);
        String dateformatted = simpleDateFormat.format(new Date(duration));
        return dateformatted;

    }
}
