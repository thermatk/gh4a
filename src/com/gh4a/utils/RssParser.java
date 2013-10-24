package com.gh4a.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.gh4a.holder.YourActionFeed;

public class RssParser {
    
    final URL feedUrl;
    
    public RssParser(String feedUrl, String username, String password){
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() throws IOException {
//        HttpsURLConnection request = (HttpsURLConnection) feedUrl.openConnection();
//        
//        request.setHostnameVerifier(DO_NOT_VERIFY);
//        request.setRequestMethod("GET");
//        request.setDoOutput(true);
//        
//        if (ApplicationConstants.CONNECT_TIMEOUT > -1) {
//            request.setConnectTimeout(ApplicationConstants.CONNECT_TIMEOUT);
//        }
//
//        if (ApplicationConstants.READ_TIMEOUT > -1) {
//            request.setReadTimeout(ApplicationConstants.READ_TIMEOUT);
//        }
//        String credentials = username + ":" + password;
//        request.setRequestProperty("Authorization", "Basic " + Base64.encodeBytes(credentials.getBytes()));
//        request.connect();
//        return new BufferedInputStream(request.getInputStream());
        return null;
    }

    
    public List<YourActionFeed> parse() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        RssHandler handler = new RssHandler();
        parser.parse(this.getInputStream(), handler);
        return handler.getFeeds();
    }
    
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
