import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="book_shop",urlPatterns={"/book_shop"})
public class BookShop extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        int uid = 0;
        String sid = "";
        System.out.println("LOGIN GET");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user_id"))
                    uid = Integer.parseInt(cookie.getValue());
                if(cookie.getName().equals("session_id"))
                    sid = cookie.getValue();
            }
        }
        if (uid < 0)
            response.sendRedirect("login");
        if (!sid.equals(DBWork.getSID(uid))) {
            System.out.println("WRONG SESSION");
            response.sendRedirect("login");
        }


        List<Book> list_books = new ArrayList<>();
        list_books = DBWork.getAllBooks();

        PrintWriter out= response.getWriter();
        for (int i = 0; i < list_books.size(); i++) {
            out.println("<br>" + list_books.get(i).getAutor() + ", " + list_books.get(i).getName() + "</br>");
        }

    }
}
