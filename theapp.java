import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.json.JSONObject;
import org.json.JSONArray;

public class theapp{
    private static JSONArray jsonArray; // Declare jsonArray as a class-level variable
    private static String qName; // Declare qName as a class-level variable

    public static void main(String[] args) {
        // Define the available fields
        Set<String> availableFields = new HashSet<>();
        availableFields.add("name");
        availableFields.add("postalZip");
        availableFields.add("region");
        availableFields.add("country");
        availableFields.add("address");
        availableFields.add("list");

        // Get user-selected fields
        Set<String> selectedFields = getUserSelectedFields(availableFields);

        // Initialize jsonArray
        jsonArray = new JSONArray();

        try {
            File inputFile = new File("data.xml"); // specify the path to your XML file

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Create a handler for SAX events
            DefaultHandler handler = new DefaultHandler() {
                boolean inRecord = false;
                JSONObject jsonObject = null;

                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("record")) {
                        inRecord = true;
                        jsonObject = new JSONObject();
                    }
                    XMLParser.qName = qName; // Assign qName value
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("record")) {
                        inRecord = false;
                        if (jsonObject != null) {
                            jsonArray.put(jsonObject);
                        }
                    }
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (inRecord) {
                        String data = new String(ch, start, length).trim();
                        if (data.length() > 0) {
                            String fieldName = XMLParser.qName.toLowerCase(); // Use class-level qName
                            if (selectedFields.contains(fieldName)) {
                                jsonObject.put(fieldName, data);
                            }
                        }
                    }
                }
            };

            // Parse the XML file using the handler
            saxParser.parse(inputFile, handler);

            // Print the JSON array
            System.out.println(jsonArray.toString(4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getUserSelectedFields(Set<String> availableFields) {
        Set<String> selectedFields = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available fields: " + availableFields);
        System.out.println("Enter the fields you want to display, separated by commas:");
        String input = scanner.nextLine();
        scanner.close();

        String[] fields = input.split(",");
        for (String field : fields) {
            field = field.trim();
            if (availableFields.contains(field)) {
                selectedFields.add(field);
            } else {
                System.out.println("Invalid field: " + field);
            }
        }

        return selectedFields;
    }
}




