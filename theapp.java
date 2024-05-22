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
            
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("record");
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    if (selectedFields.contains("name")) {
                        String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                        System.out.println("Name: " + name);
                    }

                    if (selectedFields.contains("postalZip")) {
                        String postalZip = eElement.getElementsByTagName("postalZip").item(0).getTextContent();
                        System.out.println("Postal/Zip: " + postalZip);
                    }

                    if (selectedFields.contains("region")) {
                        String region = eElement.getElementsByTagName("region").item(0).getTextContent();
                        System.out.println("Region: " + region);
                    }

                    if (selectedFields.contains("country")) {
                        String country = eElement.getElementsByTagName("country").item(0).getTextContent();
                        System.out.println("Country: " + country);
                    }

                    if (selectedFields.contains("address")) {
                        String address = eElement.getElementsByTagName("address").item(0).getTextContent();
                        System.out.println("Address: " + address);
                    }

                    if (selectedFields.contains("list")) {
                        String listString = eElement.getElementsByTagName("list").item(0).getTextContent();
                        String[] list = listString.isEmpty() ? new String[0] : listString.split(", ");
                        System.out.print("List: ");
                        for (String item : list) {
                            System.out.print(item + " ");
                        }
                        System.out.println();
                    }

                    System.out.println();
                }
            }
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
