import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class FilesFunctions {

    /* citim fisierul text cu comenzi*/
    public static void readTextFiles(File fileInput) throws FileNotFoundException {
        try {
            Scanner myReader = new Scanner( fileInput);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] arguments = line.split(" ");

                /* alegem ce functie trebuie indeplinita acum bazandu-ne pe arguments[0] */
                ProiectPOO proiect = ProiectPOO.getInstance();
                proiect.functions(arguments);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* citim fisierele CSV cu biblioteca openCSV, apoi returnam o lista de array de strings */
    public static List<String[]> readCSVFiles(File fileInput) throws FileNotFoundException {
        FileReader filereader = null;
        CSVReader csvReader = null;
        List<String[]> myEntries = null;
        try {
            filereader = new FileReader(fileInput);
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(false)
                    .build();

             csvReader = new CSVReaderHeaderAwareBuilder(filereader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();
            myEntries = csvReader.readAll();
            csvReader.close();
            filereader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return myEntries;
    }

}
