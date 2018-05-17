import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

public class WebsiteSearcher {
    private static final String ALL_URLS = "https://s3.amazonaws.com/fieldlens-public/urls.txt";
    private static final String KEYWORD = "you";
    private static final String RESULTS_FILE = "results.txt";

    private static UrlLine urlLine;

    public static void main(String[] args) throws Exception {
        List<String> urls = UrlLine.fetchLines(ALL_URLS);
        List<String> validUrls = urls.stream()
            .filter(l -> doesContainKeyword(l))
            .collect(Collectors.toList());

        writeToFile(validUrls);
    }

    private static void writeToFile(List<String> validUrls) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE));
        for(String url: validUrls) {
            writer.write(url);    
        }
        writer.close();
    }

    private static boolean doesContainKeyword(String urlLink) {
        try {
            // todo - should have have timeouts
            URL url = new URL(urlLink);
            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));
            List<String> inputLines = new ArrayList<>();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(KEYWORD)) {
                    return true;
                }
            }
            in.close();
        } catch (Exception e) {
            // todo - log
            return false;
        }
        return false;
    }
    
}