import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.Scanner;

// Add these lines to the main method after reading the XML
Scanner scanner = new Scanner(System.in);
System.out.println("Enter the fields you want to print (comma separated):");
String input = scanner.nextLine();
String[] fields = input.split(",");

for (int i = 0; i < nList.getLength(); i++) {
    Node nNode = nList.item(i);
    System.out.println("\nCurrent Element: " + nNode.getNodeName());
    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        Element eElement = (Element) nNode;
        for (String field : fields) {
            field = field.trim();
            try {
                System.out.println(field + ": " + eElement.getElementsByTagName(field).item(0).getTextContent());
            } catch (Exception e) {
                System.out.println("Field " + field + " not found");
            }
        }
    }
}
scanner.close();

public class XMLReader {
    public static void main(String[] args) {
        try {
            File inputFile = new File("path/to/your/file.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("yourElementTagName");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                System.out.println("\nCurrent Element: " + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Field1: " + eElement.getElementsByTagName("field1").item(0).getTextContent());
                    System.out.println("Field2: " + eElement.getElementsByTagName("field2").item(0).getTextContent());
                    // Add more fields as necessary
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
