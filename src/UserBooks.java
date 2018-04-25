//import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

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

@WebServlet(name="user_books",urlPatterns={"/user_books"})
public class UserBooks extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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
        list_books = DBWork.getUserBooks(uid);

        PrintWriter out= response.getWriter();
        out.println("<br><b>Ваши книги:</b></br>");
        for (int i = 0; i < list_books.size(); i++) {
            out.println("<br>" + list_books.get(i).getAutor() + ", " + list_books.get(i).getName() + "</br> ");
            out.println("<form action=\"book_downloader\" method=\"post\">\n" +
                        "   <input type=\"hidden\" name=\"user_id\" value=\""+ uid +"\"> " +
                        "   <input type=\"hidden\" name=\"session_id\" value=\""+ sid +"\"> " +
                        "   <input type=\"hidden\" name=\"book_id\" value=\""+String.valueOf(list_books.get(i).getBook_id())+"\"> " +
                        "   <input type=\"hidden\" name=\"type\" value=\"MP3\"> " +
                        "   <input type=\"submit\" value=\"Скачать MP3\">\n" +
                        "</form>");

            out.println("<form action=\"book_downloader\" method=\"post\">\n" +
                    "   <input type=\"hidden\" name=\"user_id\" value=\""+ uid +"\"> " +
                    "   <input type=\"hidden\" name=\"session_id\" value=\""+ sid +"\"> " +
                    "   <input type=\"hidden\" name=\"book_id\" value=\""+list_books.get(i).getBook_id()+"\"> " +
                    "   <input type=\"hidden\" name=\"type\" value=\"FB2\"> " +
                    "   <input type=\"submit\" value=\"Скачать FB2\">\n" +
                    "</form>");
        }

    }
}
