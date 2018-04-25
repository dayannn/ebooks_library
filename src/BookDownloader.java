//import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="book_downloader",urlPatterns={"/book_downloader"})
public class BookDownloader extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getParameter("user_id"));
        System.out.println(request.getParameter("session_id"));
        System.out.println(request.getParameter("book_id"));
        System.out.println(request.getParameter("type"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String session_id = request.getParameter("session_id");
        String book_id = request.getParameter("book_id");
        String type = request.getParameter("type");

        if (!DBWork.getSID(user_id).equals(session_id))
            return;

        String filePath = "C://LABS//MyServlet//web//WEB-INF//books//" + book_id + "//" + book_id + "." + type + "";
        String userfilePath = "C://LABS//MyServlet//web//WEB-INF//"+ user_id +"//" + book_id + "//" + book_id + "." + type + "";
        File originFile = new File(filePath);
        Path path = Paths.get("C://LABS//MyServlet//web//WEB-INF//"+ user_id +"//" + book_id);
        Files.createDirectories(path);

        File downloadFile = new File(userfilePath);


        // DRM!!!
        Files.copy(originFile.toPath(), downloadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);


        FileInputStream inStream = new FileInputStream(downloadFile);


        String relativePath = getServletContext().getRealPath("");
        ServletContext context = getServletContext();

        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {/*
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

        // reads input file from an absolute path
        String filePath = "E:/Test/Download/MYPIC.JPG";
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);

        // if you want to use a relative path to context root:
        String relativePath = getServletContext().getRealPath("");
        System.out.println("relativePath = " + relativePath);

        // obtains ServletContext
        ServletContext context = getServletContext();

        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
*/
    }
}
