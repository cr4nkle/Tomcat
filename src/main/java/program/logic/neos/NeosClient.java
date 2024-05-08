package program.logic.neos;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

public class NeosClient {
    private static volatile NeosClient INSTANCE;

    private NeosClient() {
    }

    public static NeosClient getInstance() {
        if (INSTANCE == null) {
            synchronized (NeosClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NeosClient();
                }
            }
        }
        return INSTANCE;
    }

    public void submitProblem(){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String submitUrl = "https://proto.neos-server.org/neos/cgi-bin/submit.cgi";

            HttpPost httpPost = new HttpPost(submitUrl);

            File xmlFile = new File("");
            FileBody fileBody = new FileBody(xmlFile);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    .build();
            httpPost.setEntity(reqEntity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseString = EntityUtils.toString(resEntity);
                    System.out.println("Response: " + responseString);
                }
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getResults(){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String resultsUrl = "https://proto.neos-server.org/neos/cgi-bin/results.cgi";

            HttpPost httpPost = new HttpPost(resultsUrl);

            StringEntity entity = new StringEntity("job_number=YOUR_JOB_NUMBER&password=YOUR_PASSWORD");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/xml");

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Получаем ответ от сервера
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    String responseString = EntityUtils.toString(resEntity);
                    System.out.println("Response: " + responseString);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
