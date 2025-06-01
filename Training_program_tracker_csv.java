import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Training_program_tracker_csv {
    private static Training_program_tracker_csv instance;
    private static final String CSV_FILE = "actiuni_user_log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Training_program_tracker_csv() {}

    public static synchronized Training_program_tracker_csv getInstance() {
        if (instance == null) {
            instance = new Training_program_tracker_csv();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (FileWriter fw = new FileWriter(CSV_FILE, true)) {
            String timestamp = LocalDateTime.now().format(formatter);
            fw.write(actionName + "," + timestamp + "\n");
            System.out.println("Actiunea: " + actionName);
        } catch (IOException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
    }
}
