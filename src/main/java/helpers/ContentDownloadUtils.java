package helpers;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContentDownloadUtils {

    public static String downloadContentByLink(File file, String contentUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(contentUrl);

        if (file.isDirectory()) {
            throw new IOException("Путь для загрузки передан без указания имени файла");
        }

        File downloadDirectory = file.getParentFile();

        if (!downloadDirectory.exists() && !downloadDirectory.mkdirs()) {
            throw new IOException("Не удалось создать директорию для загрузки файла");
        }

        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            if (response != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    response.getEntity().writeTo(outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String downloadContentByLink(String fileName, String contentUrl) throws IOException {
        File file = new File(DEFAULT_DIR + fileName);
        return downloadContentByLink(file, contentUrl);
    }

    private static final String DEFAULT_DIR = "/news/";
}
