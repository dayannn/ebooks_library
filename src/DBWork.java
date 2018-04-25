import javax.swing.*;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
//import org.gjt.mm.mysql.Driver;


public class DBWork {
    public static List<Book> getAllBooks() {
        Connection connection = DBSingleton.getConnection();
        List<Book> booksList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT book_id, name, autor FROM ebooks");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book b = new Book();

                b.setBook_id(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setAutor(rs.getString(3));
                booksList.add(b);
                //rs = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public static List<Book> getUserBooks(int user_id) {
        Connection connection = DBSingleton.getConnection();
        List<Book> booksList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement("SELECT e.book_id, e.name, e.autor FROM ebooks e INNER JOIN (SELECT book_id FROM user_books WHERE user_id = ?) d ON d.book_id = e.book_id");
            preparedStatement.setInt(1, user_id);

            System.out.println(preparedStatement.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Book b = new Book();

                b.setBook_id(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setAutor(rs.getString(3));
                booksList.add(b);
                //rs = rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return booksList;
    }

    public static boolean registerUser(String username, String hashpass) {
        boolean st = false;
        Connection connection = DBSingleton.getConnection();
        try {

            //2.PreparedStatement: предварительно компилирует запросы,
            //которые могут содержать входные параметры
            PreparedStatement preparedStatement = null;
            // ? - место вставки нашего значеня
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, pass) VALUES (?, ?)");
            preparedStatement.setString(1, "'" + username + "'");
            preparedStatement.setString(2, "'" + hashpass + "'");
            //Устанавливаем в нужную позицию значения определённого типа
            //выполняем запрос
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO user_roles (user_id) VALUES ( (SELECT user_id FROM users WHERE username = ?),  0)");
            preparedStatement.setString(1, "'" + username + "'");
            preparedStatement.executeUpdate();

            st = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

    public static void logoutUser(int uid) {
        Connection connection = DBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = null;
            // Проверяем, залогинен ли такой пользователь где либо еще
            preparedStatement = connection.prepareStatement("DELETE FROM sessions WHERE user_id = ?");
            preparedStatement.setInt(1, uid);

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSID(int uid) {
        Connection connection = DBSingleton.getConnection();
        PreparedStatement preparedStatement = null;
        String SID = "";
        try {
            ResultSet result2;
            preparedStatement = connection.prepareStatement("SELECT session_id FROM sessions WHERE uid = ?");
            preparedStatement.setInt(1, uid);
            result2 = preparedStatement.executeQuery();
            if (result2.next()) {
                SID = result2.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SID;
    }

    public static int loginUser(String username, String hashpass) {
        int uid = -1;
        Connection connection = DBSingleton.getConnection();
        try {
            PreparedStatement preparedStatement = null;
            /*
            //Для использования SQL запросов существуют 3 типа объектов:
            //1.Statement: используется для простых случаев без параметров
            //Statement statement = null;
            //statement = connection.createStatement();
            //Выполним запрос
           //ResultSet result1 = statement.executeQuery(
             //       "SELECT * FROM users WHERE username == " + use);
            //result это указатель на первую строку с выборки
            //чтобы вывести данные мы будем использовать
            //метод next() , с помощью которого переходим к следующему элементу

            System.out.println("Выводим statement");
            while (result1.next()) {
                System.out.println("Номер в выборке #" + result1.getRow()
                        + "\t Номер в базе #" + result1.getInt("id")
                        + "\t" + result1.getString("username"));
            }
            // Вставить запись
            statement.executeUpdate(
                    "INSERT INTO users(username) values('name')");
            //Обновить запись
            statement.executeUpdate(
                    "UPDATE users SET username = 'admin' where id = 1");
            */
            //2.PreparedStatement: предварительно компилирует запросы,
            //которые могут содержать входные параметры

            // ? - место вставки нашего значеня
            preparedStatement = connection.prepareStatement("SELECT user_id FROM users WHERE username = '" + username + "' AND pass = '" + hashpass + "'");
            //Устанавливаем в нужную позицию значения определённого типа
            //выполняем запрос
            ResultSet result2 = preparedStatement.executeQuery();
            //preparedStatement
            /*
            System.out.println("Выводим PreparedStatement");
            while (result2.next()) {
                System.out.println("Номер в выборке #" + result2.getRow()
                        + "\t Номер в базе #" + result2.getInt("id")
                        + "\t" + result2.getString("username"));
            }*/
            if (result2.next()) {
                uid = result2.getInt(1);
            }
            result2.close();
            System.out.println("UID: " + uid);
            // Это действительно нужный пользователь. Проверим, есть ли сессия с ним
            preparedStatement = connection.prepareStatement("SELECT session_id, session_time FROM sessions WHERE uid = ?");
            preparedStatement.setInt(1, uid);
            result2 = preparedStatement.executeQuery();
            String session_id = null;
            Time session_time = null;
            boolean session_exists = true;
            if (result2.next()) {
                session_id = result2.getString(1);
                session_time = result2.getTime(2);
            }
            if ((session_id == null) || (session_time == null))
                session_exists = false;

            result2.close();
            Date curTime = new Date(System.currentTimeMillis());

            if (!session_exists) {
                preparedStatement = connection.prepareStatement("INSERT INTO sessions (uid) VALUES (?)");
                preparedStatement.setInt(1, uid);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
            }

            // Если сессия на сервере истекла или её не было вообще - завести новую
            if ((!session_exists) || (session_id.isEmpty()) || (session_time.before(curTime))) {
                String new_session_id = UUID.randomUUID().toString();

                preparedStatement = connection.prepareStatement("UPDATE sessions SET (session_id, session_time) = (?, ?) WHERE uid = ?");
                preparedStatement.setString(1, new_session_id);
                curTime.setTime(System.currentTimeMillis() + 1000*60*5);
                preparedStatement.setDate(2, curTime);
                preparedStatement.setInt(3, uid);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();

            } else {
                System.out.println("ERROR");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uid;
    }
}
