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

@WebServlet(name="register",urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {
    int count = 0;

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
        response.setContentType("text/html");
        // super.doPost(request, response);
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String hashpass = getSHA256(request.getParameter("password"));
        String hashpass2 = getSHA256(request.getParameter("password_r"));

        System.out.println("'" + username + "'");
        System.out.println("'" + hashpass + "'");
        System.out.println("'" + hashpass2 + "'");

        if ((hashpass.equals(hashpass2) && (DBWork.registerUser(username, hashpass)))) {
            System.out.print("OK");
            RequestDispatcher rd = request.getRequestDispatcher("hello");
            rd.forward(request, response);
        } else {
            System.out.print("Sorry username or password error");
        //    RequestDispatcher rd=request.getRequestDispatcher("register");
        //    rd.forward(request,response);
        }
        //out.println("Login post!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        if (sid.equals(DBWork.getSID(uid))) {
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        } else {
            System.out.println("WRONG SESSION");
            response.sendRedirect("login");
        }
    }
}
