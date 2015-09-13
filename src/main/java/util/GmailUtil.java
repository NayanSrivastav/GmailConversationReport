package util;

import com.google.api.client.auth.oauth2.Credential;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.gson.Gson;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import model.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import model.ValueComparator;
import org.json.JSONArray;
import org.json.JSONObject;

public final class GmailUtil
{

    Date endDate, startDate;
    String nextPageToken = "";
    boolean isFirstPage = true;
    static SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    Map<String, Integer> conversation = new HashMap<>();
    List<com.google.api.services.gmail.model.Thread> threads;
    List<Message> messages = new ArrayList<>();
    private static final String CLIENT_ID = "153291870773-0o323f7jjonnmoqil49sichl01tc3km9.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "RVl5Jabn77Xv75sPJna2BEPk";
    private static final String CALLBACK_URI = "http://localhost:8084/mailreport/GoogleConnector";
    private static final List<String> SCOPE = Arrays
            .asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email;https://www.googleapis.com/auth/gmail.readonly"
                    .split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo",
            mail = "https://www.googleapis.com/oauth2/v1/";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private String stateToken;
    private GoogleAuthorizationCodeFlow flow = null;
    private boolean dateExceed = false;

    public GmailUtil()
    {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
        generateStateToken();
    }

    private void generateStateToken()
    {

        SecureRandom sr1 = new SecureRandom();
        stateToken = "google;" + sr1.nextInt();
    }

    public String buildLoginUrl()
    {

        final GoogleAuthorizationCodeRequestUrl url = flow
                .newAuthorizationUrl();
        System.out.println("state token-" + stateToken);
        return url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
    }

    public String getStateToken()
    {
        System.out.println("state token-" + stateToken);
        return stateToken;
    }

    public Map<String, Integer> getUserInfoJson(final String authCode) throws IOException, ParseException
    {
        final GoogleTokenResponse response = flow.newTokenRequest(authCode)
                .setRedirectUri(CALLBACK_URI).execute();
        final Credential credential = flow.createAndStoreCredential(response,
                null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT
                .createRequestFactory(credential);
        final GenericUrl url = new GenericUrl(USER_INFO_URL);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        String data = request.execute().parseAsString();
        System.out.println("data-" + data);
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        System.out.println("userId--" + user.getId() + " data--" + data);
        while (!dateExceed)
        {
            fetchMessageList(mail, user, requestFactory);
        }
        TreeMap<String, Integer> tempMap=new TreeMap<>(new ValueComparator(conversation));
        tempMap.putAll(conversation);
        return tempMap;
    }

    public void fetchMessageList(String mailId, User user, HttpRequestFactory requestFactory) throws IOException, ParseException
    {
        String url = "https://www.googleapis.com/gmail/v1/users/me/messages";
        Gmail gm=new Gmail(HTTP_TRANSPORT, JSON_FACTORY, null);
        if (!isFirstPage)
        {
            if (nextPageToken != null && nextPageToken.length() > 0)
            {
                url += "?pageToken=" + nextPageToken;
            }
            else
            {
                dateExceed=true;
                return;
            }
        }
        else
        {
            isFirstPage=false;
        }
        GenericUrl url1 = new GenericUrl(url);
        final HttpRequest request1 = requestFactory.buildGetRequest(url1);
        request1.setConnectTimeout(4*60000);
        request1.setReadTimeout(20*60000);
        request1.getHeaders().setContentType("application/json");
        String mailData = request1.execute().parseAsString();
        System.out.println("mailData-- " + mailData);
        JSONObject jSONObject = new JSONObject(mailData);
        try
        {
        nextPageToken = jSONObject.getString("nextPageToken");
        }
        catch(Exception e)
        {
            nextPageToken=null;
        }
        JSONArray messageArray = jSONObject.getJSONArray("messages");
        messages.clear();
        for (int i = 0; i < messageArray.length(); i++)
        {
            Message msg = new Message();
            msg.setId(messageArray.getJSONObject(i).getString("id"));
            msg.setThreadId(messageArray.getJSONObject(i).getString("threadId"));
            messages.add(msg);
        }
        System.out.println("message length- " + messages.size());
        for (Message msg : messages)
        {
            if(!dateExceed)
            getMessages(user.getEmail(), user.getId(), msg.getId(), requestFactory);
            else
                return;
        }
        System.out.println("------------------------------------------coversation------------------------------------------");
        System.out.println("  convesation size- "+conversation.size());
        for (String x : conversation.keySet())
        {
            String email = x;
            if (x.contains("<") && x.contains(">"))
            {

                email = x.substring(x.indexOf("<") + 1, x.indexOf(">"));
            }
            System.out.println("email- " + email + " value- " + conversation.get(x));
        }
    }


    public void getMessages(String mailId, String userId, String messageId, HttpRequestFactory requestFactory) throws IOException, ParseException
    {
        final GenericUrl url1 = new GenericUrl("https://www.googleapis.com/gmail/v1/users/" + userId + "/messages/" + messageId + "?format=metadata");
        final HttpRequest request1 = requestFactory.buildGetRequest(url1);
        request1.getHeaders().setContentType("application/json");
        request1.setConnectTimeout(4*60000);
        request1.setReadTimeout(20*60000);
        String mailData = request1.execute().parseAsString();
        System.out.println("message in getmessage-- \n" + mailData);
        JSONObject jSONObject = new JSONObject(mailData);
        String date = jSONObject.getString("internalDate");
        Date msgDate = new Date(Long.parseLong(date));
        System.out.println("message date- "+msgDate);
        if (endDate == null)
        {
            startDate = msgDate;
            Calendar cal = new GregorianCalendar();
            cal.setTime(startDate);
            cal.add(Calendar.DAY_OF_MONTH, -90);
            endDate = cal.getTime();
        }
        if(endDate.compareTo(msgDate)>0)
        {
            dateExceed=true;
            return;
        }            
        System.out.println("startdate date-- " + startDate + " end date-- " + endDate);
        String[] lables = new Gson().fromJson(jSONObject.getJSONArray("labelIds").toString(), String[].class);
        int msgType = 2;
        for (String lable : lables)
        {
            if (lable.equals("CHAT"))
            {
                msgType = 0;
                break;
            }
            if (lable.equals("INBOX"))
            {
                msgType = 1;
                break;
            }

        }

        JSONArray headerArray = jSONObject.getJSONObject("payload").getJSONArray("headers");
        for (int i = 0; i < headerArray.length(); i++)
        {
            String header = ((JSONObject) headerArray.get(i)).getString("name");
            String headerValue = ((JSONObject) headerArray.get(i)).getString("value");
            if (msgType == 0)
            {
                if (header.equalsIgnoreCase("from"))
                {

                    if (conversation.containsKey(headerValue))
                    {
                        conversation.replace(headerValue, conversation.get(headerValue) + 1);
                    }
                    else
                    {
                        conversation.put(headerValue, 1);
                    }
                }
            }
            else if (msgType == 1)
            {
                if (header.equalsIgnoreCase("from"))
                {
                    if (!((JSONObject) headerArray.get(i)).getString("value").equals(mailId))
                    {
                        if (conversation.containsKey(headerValue))
                        {
                            conversation.replace(headerValue, conversation.get(headerValue) + 1);
                        }
                        else
                        {
                            conversation.put(headerValue, 1);
                        }
                    }
                }
            }
            else
            {
                if (header.equalsIgnoreCase("to") || header.equalsIgnoreCase("cc") || header.equalsIgnoreCase("bcc"))
                {
                    if (!((JSONObject) headerArray.get(i)).getString("value").equals(mailId))
                    {
                        if (conversation.containsKey(headerValue))
                        {
                            conversation.replace(headerValue, conversation.get(headerValue) + 1);
                        }
                        else
                        {
                            conversation.put(headerValue, 1);
                        }
                    }
                }
            }
        }

    }
}
