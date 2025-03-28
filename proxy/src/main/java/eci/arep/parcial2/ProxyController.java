package eci.arep.parcial2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProxyController {

    private int aux = 0;
    private static final String USER_AGENT = "Mozilla/5.0";
    @GetMapping("/linearsearch")
    public ResponseEntity<?> linearSearchHandler(@RequestParam("list") String list, @RequestParam("value") String value) throws IOException {
        String ans = "";
        if(aux % 2 == 0){
            ans = HTTPConection(System.getenv("ms1") + "/linearsearch?list=" + list + "&value=" + value);
            //ans = HTTPConection("http://ec2-54-211-10-177.compute-1.amazonaws.com:8080" + "/linearsearch?list=" + list + "&value=" + value);
        } else{
            ans = HTTPConection(System.getenv("ms2") + "/linearsearch?list=" + list + "&value=" + value);
            //ans = HTTPConection("http://ec2-18-233-166-218.compute-1.amazonaws.com:8080" + "/linearsearch?list=" + list + "&value=" + value);
        }
        aux += 1;
        return ResponseEntity.status(200).body(ans);
    }

    @GetMapping("/binarysearch")
    public ResponseEntity<?> binarySearchHandler(@RequestParam("list") String list, @RequestParam("value") String value) throws IOException {
        String ans = "";
        if(aux % 2 == 0){
            ans = HTTPConection(System.getenv("ms1") + "/binarysearch?list=" + list + "&value=" + value);
            //ans = HTTPConection("http://ec2-54-211-10-177.compute-1.amazonaws.com:8080" + "/binarysearch?list=" + list + "&value=" + value);
        } else{
            ans = HTTPConection(System.getenv("ms2") + "/binarysearch?list=" + list + "&value=" + value);
            //ans = HTTPConection("http://ec2-18-233-166-218.compute-1.amazonaws.com:8080" + "/binarysearch?list=" + list + "&value=" + value);
        }
        aux += 1;
        return ResponseEntity.status(200).body(ans);
    }

    private String HTTPConection(String url) throws IOException{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } else {
            return "GET request not worked";
        }
    }
}
