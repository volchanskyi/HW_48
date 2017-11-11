package core;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CurrencyCode {
    public static void main(String[] args) throws IOException, ParseException {

	JSONParser jp = new JSONParser();
	URL cc = new URL("http://www.geoplugin.net/json.gp?ip=93.183.203.67");

	BufferedReader in = new BufferedReader(new InputStreamReader(cc.openConnection().getInputStream()));

	JSONObject cc_jo = (JSONObject) jp.parse(in);
	String countryName = (String) cc_jo.get("geoplugin_countryName");
	String countryCode = (String) cc_jo.get("geoplugin_currencyCode");
	String countrySym = (String) cc_jo.get("geoplugin_currencySymbol_UTF8");
	System.out.println("Country: \t\t" + countryName);
	System.out.println("Currency Code: \t\t" + countryCode);
	System.out.println("Currency Symbol: \t" + countrySym);
    }
}
