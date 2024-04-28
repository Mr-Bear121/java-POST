
import TOTP.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
//import java.nio.charset.StandardCharsets;
import java.net.UnknownServiceException;
import java.io.OutputStream;
import java.util.Base64;
//import java.io.InputStream;
//import java.io.InputStreamReader;


public class Main {
 

    public static String convertStringToHex(String str) {

        StringBuffer hex = new StringBuffer();
    
        // loop chars one by one
        for (char temp : str.toCharArray()) {
    
            // convert char to int, for char `a` decimal 97
            int decimal = (int) temp;
    
            // convert int to hex, for decimal 97 hex 61
            hex.append(Integer.toHexString(decimal));
        }
    
        return hex.toString();
    
    }
    
    // Hex -> Decimal -> Char
    public static String convertHexToString(String hex) {
    
        StringBuilder result = new StringBuilder();
    
        // split into two chars per loop, hex, 0A, 0B, 0C...
        for (int i = 0; i < hex.length() - 1; i += 2) {
    
            String tempInHex = hex.substring(i, (i + 2));
    
            //convert hex to decimal
            int decimal = Integer.parseInt(tempInHex, 16);
    
            // convert the decimal to char
            result.append((char) decimal);
    
        }
    
        return result.toString();
    
    }
  
    
    public static void StringSendPost(String URL, String password) throws MalformedURLException, IOException, UnknownServiceException{

        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        String Json = "{github_url: https://gist.github.com/Mr-Bear121/3210f07338e71ebe348201bb4a96f35b , contact_email: onagy9000@gmail.com}";
        String userCredentials = "onagy9000@gmail.com:" + password;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        
        //connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            
        connection.setRequestMethod("POST");
        
        connection.setRequestProperty ("Authorization", "Basic " + basicAuth);
        connection.setRequestProperty("Host","api.challenge.hennge.com");
        connection.setRequestProperty("Accept","*/*");
        connection.setRequestProperty("Content-Type", "application/json");
        
        connection.setRequestProperty("Content-Length", "" + Json.getBytes().length);
        connection.setDoOutput(true);
        connection.connect();

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = Json.getBytes("utf-8");
            os.write(input, 0, input.length);			
        }

        //InputStream is = connection.getInputStream();
		//	InputStreamReader isr = new InputStreamReader(is);


        int code = connection.getResponseCode();
        System.out.println("Response code of the object is "+code);
        if (code==200)
        {
            System.out.println("OK");
        }
        
    }
    
public static void main(String[] args) throws MalformedURLException, IOException{
    String Mine = "onagy9000@gmail.com726978787169677265767669787169484851";
    //String Mine = "onagy9000@gmail.comHENNGECHALLENGE003";
    String seed64 = convertStringToHex(Mine);
    long unix = System.currentTimeMillis() / 1000L;
    
    //SecretKeySpec key = new SecretKeySpec(seed64.getBytes(),"HMAC_SHA512");

    long T0 = 0;
  long X = 30;
  String steps = "0";

  
   
        long T = (unix - T0)/X;
        steps = Long.toHexString(T).toUpperCase();
        while (steps.length() < 16) steps = "0" + steps;
        

        String key = TOTP.generateTOTP512(seed64, steps, "10");


        for (int x = 0 ; x <= key.length(); x++){

            System.out.println(x);
        }

    System.out.println("your code is: " + key);

    

    //StringSendPost("http://192.168.1.14:9000",key);

    StringSendPost("https://api.challenge.hennge.com/challenges/003",key);

    
}
}

