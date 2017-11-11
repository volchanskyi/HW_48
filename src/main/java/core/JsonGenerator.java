package core;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonGenerator {

    static String firstName = "John";
    static String lastName = "Smith";
    static Integer age = 40;

    static String streetAddress = "711 Eddy Street";
    static String city = "San Francisco";
    static String state = "CA";
    static String postalCode = "94109";

    static String home_phone = "415 555-1234";
    static String cell_phone = "415 555-4567";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {

	JSONObject json = new JSONObject();
	json.put("firstName", firstName);
	json.put("lastName", lastName);
	json.put("age", age);

	JSONObject address = new JSONObject();
	address.put("streetAddress", streetAddress);
	address.put("city", city);
	address.put("state", state);
	address.put("postalCode", postalCode);

	json.put("address", address);

	JSONArray phone = new JSONArray();
	phone.add(home_phone);
	phone.add(cell_phone);

	json.put("phone", phone);

	FileWriter file = new FileWriter("./test.json");

	file.write(json.toJSONString());
	file.flush();
	file.close();

	System.out.print(json);
    }
}
