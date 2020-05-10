package me.proggy.proggywebservices.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Metodi utili per gestire l'xml
 */
public final class XMLUtils {

    /**
     * Costruttore privato per evitare che venga istanziata la classe
     */
    private XMLUtils() {
    }

    /**
     * Converte una {@link NodeList} in una {@link List<Node>}
     *
     * @param lista nodelist
     * @return lista di nodi
     */
    public static List<Node> wrapNodeList(NodeList lista) {
        return IntStream.range(0, lista.getLength())
                .mapToObj(lista::item)
                .collect(Collectors.toList());
    }

    /**
     * Estrae il testo da un elemento xml
     *
     * @param root    elemento xml
     * @param tagName nome del tag xml di cui estrarre il testo
     * @return testo estratto
     */
    public static String getTextValue(Element root, String tagName) {
        return root.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }
    
    public static Element loadXmlFromString(String xml) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            final Element root = builder.parse(is).getDocumentElement();
            return root;//Elemento root esempio per ottenere valori: root.getElementsByTagName("idUtente").item(0).getTextContent()
        }catch(ParserConfigurationException | SAXException | IOException e){
            return null;
        }
        
    }
    
    

}
