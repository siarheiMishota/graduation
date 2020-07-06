package by.mishota.graduation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/upload/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadController extends HttpServlet {

    private static AtomicInteger counter = new AtomicInteger();
    private static final String UPLOAD_DIR = "D:\\photos";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uploadFileDir = UPLOAD_DIR + File.separator;

        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

//        try {
//            req.getPart("name").write(uploadFileDir + LocalDateTime.now().toString() + counter.getAndIncrement());
//            req.setAttribute("upload_result","upload successfully");
//        }catch (IOException e){
//            LOGGER.info("Photo isn't uploaded",e);
//            System.out.println("Photo isn't uploaded");
//            req.setAttribute("upload_result","upload failed");
//        }

        Collection<Part> parts = req.getParts();
        System.out.println(parts);

        req.getParts().forEach(part -> {
            try {
                part.write(uploadFileDir + LocalDate.now().toString() + counter.getAndIncrement()+
                        part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf(".")));
                req.setAttribute("upload_result", "upload successfully");
            } catch (IOException e) {
                LOGGER.info("Photo isn't uploaded", e);
                System.out.println("Photo isn't uploaded");
                req.setAttribute("upload_result", "upload failed");
            }
        });

        req.getRequestDispatcher("/index.jsp").forward(req, resp);

//        req.getParts().stream().forEach(part->{
//            try {
//
//            }
//        });

    }
}
