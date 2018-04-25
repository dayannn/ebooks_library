import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;

@WebServlet(name="login_success",urlPatterns={"/login_success"})
public class LoginSuccess extends HttpServlet {
    int uid;
    String sid;

    public static String getSHA256(String data){
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes());
            byte byteData[] = md.digest();

            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        if (sid.equals(DBWork.getSID(uid))) {
            request.getRequestDispatcher("WEB-INF/login_success.jsp").forward(request, response);
        } else {
            System.out.println("WRONG SESSION");
            response.sendRedirect("login");
        }

    }
}
