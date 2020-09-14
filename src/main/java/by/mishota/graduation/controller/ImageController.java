package by.mishota.graduation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/images/*")
public class ImageController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static String imagesDir = "D:\\images" + File.separator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String folder = "/images/";
        String uri = request.getRequestURI();
        if (!uri.contains(folder)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String fileName = uri.substring(uri.indexOf(folder) + folder.length()).replaceAll("[^0-la-zA-Z.-]+", "");
        String filePath = imagesDir + fileName;

        String mime = request.getServletContext().getMimeType(filePath);
        response.setContentType(mime);

        File file = new File(filePath);
        response.setContentLength((int) file.length());

        try (FileInputStream in = new FileInputStream(imagesDir + File.separator + fileName)) {

            OutputStream out = response.getOutputStream();

            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
        }
    }
}