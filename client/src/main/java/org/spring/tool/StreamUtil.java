package org.spring.tool;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * TODO
 *
 * @Description
 * @Author ZW
 * @Date 2023/3/3 8:35
 **/
public interface StreamUtil {
    Logger logger = LoggerFactory.getLogger(StreamUtil.class);


    /**
     * 将指定文件路径的流写入到指定的输出文件路径(应该为文件)中,若文件不存在,将自动创建,若文件已经存在则进行覆盖写,若文件非法,则返回false,并终止操作
     *
     * @param file        需要读取流的文件
     * @param outfilePath 输出流的文件路径
     * @return 是否完全读写
     */
    default boolean inputStreamToFile(File file, String outfilePath) {
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error("文件输入流创建失败");
            return false;
        }
        return inputStreamToFile(inputStream, outfilePath);
    }
    /**
     * 将 input流写入 output流中 内部会处理异常,如果出现异常将中断并返回false
     *
     * @param inputStream  input的流
     * @param outputStream output 流
     * @return 是否完全写入
     */
    boolean inputStreamToOutput(InputStream inputStream, OutputStream outputStream);

    /**
     * 将流写入指定的文件中,若文件不存在,将自动创建,若文件已经存在则进行覆盖写,若文件非法,则返回false,并终止操作
     *
     * @param inputStream 需要写入的流
     * @param file        文件路径
     * @return 是否写入成功
     */
    boolean inputStreamToFile(InputStream inputStream, File file);

    /**
     * 将流写入指定的文件路径(应该为文件)中,若文件不存在,将自动创建,若文件已经存在则进行覆盖写,若文件非法,则返回false,并终止操作
     *
     * @param inputStream 需要写入的流
     * @param filePath    文件路径
     * @return 是否写入成功
     */
    default boolean inputStreamToFile(InputStream inputStream, String filePath) {
        File file = new File(filePath);
        return inputStreamToFile(inputStream, file);
    }


    /**
     * 将指定文件路径的流写入到指定的输出文件路径(应该为文件)中,若文件不存在,将自动创建,若文件已经存在则进行覆盖写,若文件非法,则返回false,并终止操作
     *
     * @param filepath    需要读取流的文件路径
     * @param outfilePath 输出流的文件路径
     * @return 是否完全读写
     */
    default boolean inputStreamToFile(String filepath, String outfilePath) {
        return inputStreamToFile(new File(filepath), outfilePath);
    }

    default boolean httpToFile(String ip, int port, File file, String md5) {

        if (!file.exists() || !file.isFile()) {
            return false;
        }
        OutputStream out = null;
        DataInputStream in = null;
        try {
            URL url = new URL("http://" + ip + ":" + port + "/Data/accept");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行

            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/html");
            conn.setRequestProperty("filename", file.getName());
            conn.setRequestProperty("md5", md5);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            conn.setConnectTimeout(10000);
            out = conn.getOutputStream();

            in = new DataInputStream(new FileInputStream(file));

            int bytes = 0;
            byte[] buffer = new byte[1024];
            while ((bytes = in.read(buffer)) != -1) {
                logger.info("文件读出: {}", bytes);
                out.write(buffer, 0, bytes);
            }
            in.close();
            out.flush();
            out.close();
            conn.disconnect();
            return true;
        } catch (Exception e) {
            System.out.println("发送文件出现异常！" + e);
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ;
        }

    }

    default boolean httpToFileNew(String ip, int port, File file, String md5) {
        try {
            OkHttpClient client = new OkHttpClient();
            InputStream inputStream = new BufferedInputStream(
                    new FileInputStream(file));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] bytes = buffer.toByteArray();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("md5", md5)
                    .addFormDataPart("filename", file.getName())
                    .addFormDataPart("file", file.getName(), RequestBody.create(bytes))
                    .build();
            String url = "http://" + ip + ":" + port + "/Data/accept";
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            inputStream.close();
        } catch (Exception e) {
            logger.info("error :{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 将文件写入获取的流中,但不进行中指流的操作
     * @param file 需要写入的文件
     * @param outputStream PrintWriter字符流
     * @param offset 偏移量
     * @return 是否完全写入
     */
    boolean readOffsetToOut(File file, OutputStream outputStream, long offset);
}
