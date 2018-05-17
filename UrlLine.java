import java.net.*;
import java.io.*;
import java.util.*;

public class UrlLine {
	private static int NUM_FIELDS = 6;
	private static int URL_INDEX = 1;

    public String url;

    public static String urlFromLine(String line) {
    	String[] fields = line.split(",");
    	if (fields.length != NUM_FIELDS) {
    		// incorrect number of fields. todo - log
    		return null;
    	}
    	String urlWithoutQuotes = getValidUrl(fields[URL_INDEX]);
    	return urlWithoutQuotes;
    }

    public static String getValidUrl(String urlWithStrings) {
    	String url = urlWithStrings.replaceAll("\"", "");
    	url = url.substring(0,url.length()-1);
    	url = "http://" + url;
    	return url;
    }

    public static List<String> fetchLines(String urlLink) throws Exception {
        URL allUrls = new URL(urlLink);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(allUrls.openStream()));
        List<String> inputLines = new ArrayList<>();
        String inputLine;
        String url;
        Boolean header = true;
        while ((inputLine = in.readLine()) != null) {
            if (header) {
                header = false;
                continue;
            }
            url = UrlLine.urlFromLine(inputLine);
            if (url != null) {
            	inputLines.add(url);
            }
        }
        in.close();
        return inputLines;
    }
}