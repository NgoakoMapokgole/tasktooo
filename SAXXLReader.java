import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Map;

public class SAXXMLReader {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the fields you want to print (comma separated):");
            String input = scanner.nextLine();
            String[] fields = input.split(",");
            scanner.close();

            if (input.trim().isEmpty()) {
                System.out.println("No fields entered.");
                return;
            }

            for (String field : fields) {
                if (field.trim().isEmpty()) {
                    System.out.println("Invalid field: " + field);
                    return;
                }
            }

            DefaultHandler handler = new DefaultHandler() {
                boolean[] fieldFlags = new boolean[fields.length];
                Map<String, String> elementData = new HashMap<>();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    for (int i = 0; i < fields.length; i++) {
                        if (qName.equalsIgnoreCase(fields[i].trim())) {
                            fieldFlags[i] = true;
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    for (int i = 0; i < fields.length; i++) {
                        if (qName.equalsIgnoreCase(fields[i].trim())) {
                            fieldFlags[i] = false;
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    for (int i = 0; i < fields.length; i++) {
                        if (fieldFlags[i]) {
                            elementData.put(fields[i].trim(), new String(ch, start, length));
                        }
                    }
                }

                @Override
                public void endDocument() throws SAXException {
                    JSONObject jsonObject = new JSONObject();
                    for (String field : fields) {
                        jsonObject.put(field.trim(), elementData.getOrDefault(field.trim(), "Field not found"));
                    }
                    System.out.println(jsonObject.toJSONString());
                }
            };

            saxParser.parse("/workspaces/tasktooo/xmlfile.xml", handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
