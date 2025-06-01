import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseService {
    private static DatabaseService instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/calisthenics_training_program";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dlmvm";

    private Connection connection;

    private DatabaseService() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to PostgreSQL database successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public void executeUpdate(String sql, Consumer<PreparedStatement> parameterSetter) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            parameterSetter.accept(pstmt);
            pstmt.executeUpdate();
        }
    }

    // SELECT
    public <T> T executeQuery(String sql, Consumer<PreparedStatement> parameterSetter, Function<ResultSet, T> resultProcessor) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (parameterSetter != null) {
                parameterSetter.accept(pstmt);
            }
            try (ResultSet rs = pstmt.executeQuery()) {
                return resultProcessor.apply(rs);
            }
        }
    }
}
