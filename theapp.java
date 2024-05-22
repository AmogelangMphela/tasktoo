import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.json.JSONObject;
import org.json.JSONArray;

public class theapp{

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

        try {
            File inputFile = new File("data.xml"); // specify the path to your XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");

            JSONArray jsonArray = new JSONArray();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    JSONObject jsonObject = new JSONObject();

                    for (String field : selectedFields) {
                        String fieldValue = eElement.getElementsByTagName(field).item(0).getTextContent();
                        jsonObject.put(field, fieldValue);
                    }

                    jsonArray.put(jsonObject);
                }
            }

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

