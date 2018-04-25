import java.sql.Connection;
import java.sql.DriverManager;

public class DBSingleton {
    private static Connection connection = null;
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String db_name = "postgres";
    private static String db_pass = "postgres";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, db_name, db_pass);
                System.out.println("Соединение установлено");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return connection;
    }
}
