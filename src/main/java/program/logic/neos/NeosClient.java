package program.logic.neos;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

    public void submitProblem(String path) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<document>\n" +
                "    <category>go</category>\n" +
                "    <solver>BARON</solver>\n" +
                "    <inputMethod>AMPL</inputMethod>\n" +
                "    <email>insert valid email address</email>\n" +
                "    <client>\n" +
                "        <![CDATA[Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 YaBrowser/24.4.0.0 Safari/537.36@31.47.164.130]]></client>\n" +
                "    <priority><![CDATA[long]]></priority>\n" +
                "    <email><![CDATA[cr4nkle@yandex.ru]]></email>\n" +
                "    <model><param cost{i in 1..6};\n" +
                "param coef{i in 1..5, j in 1..6};\n" +
                "\n" +
                "var x{i in 1..6};\n" +
                "var bin{i in 1..3} binary;\n" +
                "\n" +
                "minimize sum_costs: (sum{i in 1..6} x[i]*cost[i]);\n" +
                "\n" +
                "subject to R1: sum{i in 1..1, j in 1..6} coef[i, j]*x[j]<=10;\n" +
                "subject to R2: sum{i in 2..2, j in 1..6} coef[i, j]*x[j]=0;\n" +
                "subject to R3: sum{i in 3..3, j in 1..6} coef[i, j]*x[j]=-3;\n" +
                "subject to R4: sum{i in 4..4, j in 1..6} coef[i, j]*x[j]=-4;\n" +
                "\n" +
                "subject to limits:bin[1]+bin[2]+bin[3]=1;\n" +
                "\n" +
                "subject to x_c {i in 1..6}: 0<=x[i];></model>\n" +
                "    <data><![CDATA[param cost:= 1 10.0\t2 20.0\t3 50.0\t4 50.0\t5 0.0\t6 0.0;\n" +
                "\n" +
                "param coef:\t1\t2\t3\t4\t5\t6:=\n" +
                "\t\t\t1\t1\t1\t1\t1\t0\t0\n" +
                "\t\t\t2\t-1\t-1\t-1\t-1\t1\t1\n" +
                "\t\t\t3\t0\t0\t0\t0\t-1\t0\n" +
                "\t\t\t4\t0\t0\t0\t0\t0\t-1;]]></data>\n" +
                "    <commands><![CDATA[solve;\n" +
                "display x;]]></commands>\n" +
                "    <comments><![CDATA[]]></comments>\n" +
                "</document>";

        String submitUrl = "https://neos-server.org/neos/cgi-bin/submit.cgi";
        HttpPost httpPost = new HttpPost(submitUrl);


        httpPost.setEntity(new StringEntity(xmlContent));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String responseString = EntityUtils.toString(resEntity);
                System.out.println("Response: " + responseString);
            }
        } catch (IOException e) {
            // Log the exception instead of throwing a RuntimeException
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        }
    }

    public void getResults() {
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
