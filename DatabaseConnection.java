import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/manajemen_nilai_mahasiswa";
    private static final String USER = "root"; // ganti sesuai user MySQL kamu
    private static final String PASSWORD = ""; // ganti sesuai password MySQL kamu

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
