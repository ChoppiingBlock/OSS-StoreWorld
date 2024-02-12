package org.spring.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class FileService {

    public static String fileOutPutPath = "C:\\05program\\bucketWorld\\files\\output\\3.txt";

    public static void downloadFile()  throws IOException, InterruptedException {
        File file = new File(fileOutPutPath);
        String url = "http://localhost:8073/storeServer/sendfile?filePath=0";
        if(file.exists()) url = "http://localhost:8073/storeServer/sendfile?filePath=" + file.length();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<InputStream> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

        if (httpResponse.statusCode() == 200) {
            // 获取文件名
            String filename = getFilenameFromUrl(url);
            // 保存文件到本地
            saveToFile(httpResponse.body(), file.length());
            System.out.println("文件接收成功！");
        } else {
            System.out.println("文件接收失败，HTTP响应状态码：" + httpResponse.statusCode());
        }
    }

    private static String getFilenameFromUrl(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        return url.substring(lastSlashIndex + 1);
    }

    private static void saveToFile(InputStream inputStream, Long index) throws IOException {

        // 使用Files.newOutputStream创建一个可以追加内容的OutputStream
        try (OutputStream out = Files.newOutputStream(Paths.get(fileOutPutPath),
                StandardOpenOption.CREATE, // 如果文件不存在，则创建
                StandardOpenOption.APPEND)) { // 如果文件存在，追加内容到文件末尾
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } // try-with-resources会自动关闭流
    }



}
