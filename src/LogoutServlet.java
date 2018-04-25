import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="logout_servlet", urlPatterns={"/logout_servlet"})
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        List<Cookie> loginCookie = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        int uid = -1;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_id")) {
                    uid = Integer.parseInt(cookie.getValue());
                    loginCookie.add(cookie);
                    break;
                }
                if (cookie.getName().equals("session_id")) {
                    loginCookie.add(cookie);
                    break;
                }

            }
            if (loginCookie.size() > 0) {
                for (Cookie cookie : loginCookie) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }

        }
        DBWork.logoutUser(uid);

        response.sendRedirect("/login");
    }
}
