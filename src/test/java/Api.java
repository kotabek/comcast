import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kotabek on 3/22/17.
 */
enum HttpMethods {
    GET, POST, PUT, DELETE
};

public class Api {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String domainName = "localhost";
    private static int domainPort = 8080;

    public static HttpResponse doRequest(HttpMethods method, String url, Map<String, Object> data, Map<String, Object> query, Map<String, String> headers) throws Exception {
        String resultUrl = getUrl(url, query);
        HttpRequestBase request;
        switch (method) {
            case GET:
                request = new HttpGet(resultUrl);
                break;
            case POST:
                request = new HttpPost(resultUrl);
                break;
            case PUT:
                request = new HttpPut(resultUrl);
                break;
            case DELETE:
                request = new HttpDelete(resultUrl);
                break;
            default:
                throw new RuntimeException("Method not supporting");
        }

        if (headers == null) {
            headers = doJson();
        }
        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.addHeader(header.getKey(), header.getValue());
        }

        System.out.println("Request by Url=>" + resultUrl);

        if (HttpMethods.POST.equals(method)
            || HttpMethods.PUT.equals(method)) {
            String dataStr;
            if (data != null && !data.isEmpty()) {
                dataStr = objectMapper.writeValueAsString(data);
            } else {
                dataStr = "{}";
            }

            System.out.println(dataStr);
            ((HttpEntityEnclosingRequestBase) request).setEntity(new StringEntity(dataStr));
        }
        if (data != null && !data.isEmpty()
            && (HttpMethods.POST.equals(method)
                || HttpMethods.PUT.equals(method))) {

        }
        HttpClient httpClient = new DefaultHttpClient();
        try {
            return httpClient.execute(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getUrl(String path, Map<String, Object> params) {
        String url = "http://" + domainName + ":" + domainPort + path;
        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                sb.append(param.getKey()).append("=").append(param.getValue()).append("&");
            }
            return url + "?" + sb.toString();
        }
        return url;
    }

    public static Map<String, String> doJson() {
        Map<String, String> headers = new HashMap<String, String>() {{
            put("Content-Type", "application/json");
            put("Accept", "application/json");
        }};
        return headers;
    }

    public static HttpResponse doGet(String url, Map<String, Object> query, Map<String, String> headers) throws Exception {
        return doRequest(HttpMethods.GET, url, null, query, headers);
    }

    public static HttpResponse doPost(String url, Map<String, Object> data, Map<String, Object> query, Map<String, String> headers) throws Exception {
        return doRequest(HttpMethods.POST, url, data, query, headers);
    }

    public static HttpResponse doPut(String url, Map<String, Object> data, Map<String, Object> query, Map<String, String> headers) throws Exception {
        return doRequest(HttpMethods.PUT, url, data, query, headers);
    }

    public static HttpResponse doDelete(String url, Map<String, Object> query, Map<String, String> headers) throws Exception {
        return doRequest(HttpMethods.DELETE, url, null, query, headers);
    }

    public static JSONObject getObject(HttpResponse response) {
        final HttpEntity entity = response.getEntity();
        try {
            String sb = EntityUtils.toString(entity);
            System.out.println("response => " + sb);
            return (JSONObject) (new JSONParser().parse(sb));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONArray getArray(HttpResponse response) {
        final HttpEntity entity = response.getEntity();
        try {
            String sb = EntityUtils.toString(entity);
            System.out.println("response => " + sb);
            return (JSONArray) (new JSONParser().parse(sb));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}