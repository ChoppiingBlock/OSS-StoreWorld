package org.spring.controller;

import org.spring.util.MyAsses;
import org.spring.util.MyException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("/storeServer")
public class FileController {

    @GetMapping("/sendfile")
    public void sendfile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException{
        System.out.println("hello world");


        // 假设index是作为方法参数传递进来的
        long index = Long.parseLong(filePath)/* 传递进来的index值 */;
        filePath =  "C:\\05program\\bucketWorld\\files\\input\\1.mp4";
        File file = new File(filePath);
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        response.setContentType(mimeType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");

// 调整文件长度，因为不再是从文件开始传输
        long fileLength = file.length() - index;
        response.setContentLength((int) fileLength);

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

// 从文件的第index个字节开始传输
        inputStream.skip(index);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @GetMapping("/hello")
    public void hello(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write("Hello, World!");
        writer.flush();
    }

}
