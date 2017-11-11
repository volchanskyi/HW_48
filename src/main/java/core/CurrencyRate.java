package core;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.*;
import org.json.simple.parser.*;

public class CurrencyRate {
    
    
    
    public static void main(String[] args) throws IOException, ParseException {

	JSONParser jp = new JSONParser();

	URL cc = new URL("http://www.geoplugin.net/json.gp?ip=213.87.141.36"); //RUB
	URL cc2 = new URL("http://www.geoplugin.net/json.gp?ip=88.191.179.56"); //EUR
	
	JSONObject cc_json = (JSONObject) jp
		.parse(new BufferedReader(new InputStreamReader(cc.openConnection().getInputStream())));
	JSONObject cc_json2 = (JSONObject) jp
		.parse(new BufferedReader(new InputStreamReader(cc2.openConnection().getInputStream())));

	String usa_code = "USD";
	String local_code = (String) cc_json.get("geoplugin_currencyCode"); // RUB
	String baseCode = (String) cc_json2.get("geoplugin_currencyCode"); // EUR
//	System.out.println(local_code);
//	System.out.println(baseCode);
	URL rate_url = new URL("https://api.fixer.io/latest?base=" + local_code + "&symbols=" + baseCode);
//	System.out.println(rate_url);
	JSONObject rate_json = (JSONObject) jp
		.parse(new BufferedReader(new InputStreamReader(rate_url.openConnection().getInputStream())));
//	 System.out.println(rate_json);
	
	JSONObject rate = (JSONObject) rate_json.get("rates");
	Pattern pattern = Pattern.compile("\\d*\\.\\d*");
	Matcher matcher = pattern.matcher((rate.toString()));
	matcher.find();
	String convertedRate = matcher.group(0);
//	System.out.println(baseCode + " to " + local_code  + " = " + usd_eur_rate);
	

    }
    
    
}
