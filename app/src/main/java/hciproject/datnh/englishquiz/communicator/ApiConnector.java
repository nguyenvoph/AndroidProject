package hciproject.datnh.englishquiz.communicator;

import android.net.Uri;

import com.google.gson.Gson;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import hciproject.datnh.englishquiz.entity.WordQuizEntity;
import hciproject.datnh.englishquiz.model.ListeningQuizModel;
import hciproject.datnh.englishquiz.model.MultipleChoiceQuizModel;
import hciproject.datnh.englishquiz.model.WordQuizModel;

public class ApiConnector{
    public static String host = "http://192.168.43.72:8080";
    public static String listeningResource = "/listening";
    public static String wordResource = "/word-quiz/random";
    public static String allWordResource = "/word-quiz/all";
    public static String multipleChoiceRequest = "/multiple-choice";

    public static WordQuizEntity callWordApi() {
        String uri = host + wordResource;

        String json = sendGetRequest(uri);

        json = (json.equals("")) ? FakeController.getWord() : json;

        //Convert to object
        WordQuizEntity entity = new WordQuizEntity();

        try {
            entity = (new Gson()).fromJson(json, WordQuizEntity.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return entity;
    }

    public static WordQuizModel callAllWordApi() {
        String uri = host + allWordResource;

        String json = sendGetRequest(uri);

        json = (json.equals("")) ? FakeController.getAllWord() : json;

        //Convert to object
        WordQuizModel model = new WordQuizModel();

        try {
            model = (new Gson()).fromJson(json, WordQuizModel.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return model;
    }

    public static ListeningQuizModel callListeningApi(int difficult, int quantity) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("difficult", difficult + "");
        hashMap.put("quantity", quantity + "");

        String uri = host + listeningResource + appendParams(hashMap);

        String json = sendGetRequest(uri);

        json = (json.equals("")) ? FakeController.getListening(difficult, quantity) : json;

        //Convert to object
        ListeningQuizModel model = new ListeningQuizModel();

        try {
            model = (new Gson()).fromJson(json, ListeningQuizModel.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return model;
    }

    public static MultipleChoiceQuizModel callMultipleChoiceApi(int difficult, int quantity) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("difficult", difficult + "");
        hashMap.put("quantity", quantity + "");

        String uri = host + multipleChoiceRequest + appendParams(hashMap);

        String json = sendGetRequest(uri);

        json = (json.equals("")) ? FakeController.getMultipleChoice(difficult, quantity) : json;

        //Convert to object
        MultipleChoiceQuizModel model = new MultipleChoiceQuizModel();

        try {
            model = (new Gson()).fromJson(json, MultipleChoiceQuizModel.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return model;
    }

    static String appendParams(HashMap<String, String> hashMap) {
        String s = "?";

        for (Map.Entry<String, String> entry: hashMap.entrySet()) {
            if (s.equals("?")) {
                s += entry.getKey() + "=" + entry.getValue();
            } else {
                s += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }

        return s.equals("?") ? "" : s;
    }

    static String sendGetRequest(String uri) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(uri);
        request.addHeader("Accept", "application/json");
        try {
            HttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            return json;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ApiConnector() {
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        ApiConnector.host = host;
    }

    public static String getListeningResource() {
        return listeningResource;
    }

    public static void setListeningResource(String listeningResource) {
        ApiConnector.listeningResource = listeningResource;
    }

    public static String getWordResource() {
        return wordResource;
    }

    public static void setWordResource(String wordResource) {
        ApiConnector.wordResource = wordResource;
    }

    public static String getMultipleChoiceRequest() {
        return multipleChoiceRequest;
    }

    public static void setMultipleChoiceRequest(String multipleChoiceRequest) {
        ApiConnector.multipleChoiceRequest = multipleChoiceRequest;
    }
}
