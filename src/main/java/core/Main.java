package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
	String usCurrencySym = "$";

	String[] countryArr = { "88.191.179.56", "61.135.248.220", "92.40.254.196", "213.87.141.36", "52.192.103.163" };
	////////////////////////////////////////////////////////////////////////////////
	JSONParser jp = new JSONParser();
	URL localCountryCode = new URL("http://www.geoplugin.net/json.gp?ip=73.116.62.250");
	for (String i : countryArr) {

	    URL foreignCountryCode = new URL("http://www.geoplugin.net/json.gp?ip=" + i); // EUR
//	    System.out.println(foreignCountryCode);
	    BufferedReader in = new BufferedReader(
		    new InputStreamReader(foreignCountryCode.openConnection().getInputStream()));

	    JSONObject cc_jo = (JSONObject) jp.parse(in);

	    String countryName = (String) cc_jo.get("geoplugin_countryName");

	    String currencySym = (String) cc_jo.get("geoplugin_currencySymbol_UTF8");
	    /////////////////////////////////////////////////////////////////////
	    JSONParser jp2 = new JSONParser();

	    JSONObject cc_json = (JSONObject) jp2.parse(
		    new BufferedReader(new InputStreamReader(localCountryCode.openConnection().getInputStream())));
	    JSONObject cc_json2 = (JSONObject) jp2.parse(
		    new BufferedReader(new InputStreamReader(foreignCountryCode.openConnection().getInputStream())));

	    String local_code = (String) cc_json.get("geoplugin_currencyCode"); // RUB
	    String baseCode = (String) cc_json2.get("geoplugin_currencyCode"); // EUR

	    URL rate_url = new URL("https://api.fixer.io/latest?base=" + local_code + "&symbols=" + baseCode);

//	    System.out.println(rate_url);
	    JSONObject rate_json = (JSONObject) jp2
		    .parse(new BufferedReader(new InputStreamReader(rate_url.openConnection().getInputStream())));

	    JSONObject rate = (JSONObject) rate_json.get("rates");
//	    System.out.println(rate);
	    Pattern pattern = Pattern.compile("\\d*\\.?\\d");
	    Matcher matcher = pattern.matcher((rate.toString()));
	    matcher.find();
	    String convertedRate = matcher.group(0);
	    double localPrice = Double.parseDouble(convertedRate);
	    ////////////////////////////////////////////////////
	    Logger logger = Logger.getLogger("");
	    logger.setLevel(Level.OFF);
	    String url = "https://www.amazon.com/All-New-Amazon-Echo-Dot-Add-Alexa-To-Any-Room/dp/B01DFKC2SO";

	    WebDriver driver;

	    logger.setLevel(Level.OFF);
	    ChromeOptions option = new ChromeOptions();
	    String driverPath = "";
	    if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
		driverPath = "./resources/webdrivers/mac/chromedriver";
		option.addArguments("-start-fullscreen");
	    } else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
		driverPath = "./resources/webdrivers/pc/chromedriver.exe";
		option.addArguments("--start-maximized");
	    } else
		throw new IllegalArgumentException("Unknown OS");
	    System.setProperty("webdriver.chrome.driver", driverPath);
	    System.setProperty("webdriver.chrome.silentOutput", "true");
	    option.addArguments("disable-infobars");
	    option.addArguments("--disable-notifications");
	    driver = new ChromeDriver(option);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get(url);

	    // All-New Echo Dot (2nd Generation) - Black
	    String product_title = driver.findElement(By.id("productTitle")).getText();
	    double original_price = Double
		    .parseDouble(driver.findElement(By.id("priceblock_ourprice")).getText().replace("$", "")); // 49.99
	    driver.quit();
	    ////////////////////////////////////////////////////////////////////////////////

	    ////////////////////////////////////////////////////////////////////////////////

	    double convertedPrice = new BigDecimal(original_price * localPrice).setScale(2, RoundingMode.HALF_UP)
		    .doubleValue();
	    System.out.println("Item: " + product_title + "; " + "US Price: " + usCurrencySym + original_price + "; "
		    + "For country: " + countryName + "; " + "Local Price: " + currencySym + " " + convertedPrice);
	}
    }
}
