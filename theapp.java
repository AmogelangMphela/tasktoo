import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class theapp{

    public static void main(String[] args) {
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
                    
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String postalZip = eElement.getElementsByTagName("postalZip").item(0).getTextContent();
                    String region = eElement.getElementsByTagName("region").item(0).getTextContent();
                    String country = eElement.getElementsByTagName("country").item(0).getTextContent();
                    String address = eElement.getElementsByTagName("address").item(0).getTextContent();
                    String listString = eElement.getElementsByTagName("list").item(0).getTextContent();
                    
                    // If the list element is empty, handle it accordingly
                    String[] list = listString.isEmpty() ? new String[0] : listString.split(", ");
                    
                    System.out.println("Name : " + name);
                    System.out.println("Postal/Zip : " + postalZip);
                    System.out.println("Region : " + region);
                    System.out.println("Country : " + country);
                    System.out.println("Address : " + address);
                    System.out.print("List : ");
                    for (String item : list) {
                        System.out.print(item + " ");
                    }
                    System.out.println("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
