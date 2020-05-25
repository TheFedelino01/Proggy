package me.proggy.proggywebservices.utils;

import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Classe per la validazione di un documento xml
 */
public final class XMLValidator {
    /**
     * Schema per la validazione
     */
    private static final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

    /**
     * Costruttore privato per evitare che venga istanziata la classe
     */
    private XMLValidator() {
    }

    /**
     * Valida un file xml
     *
     * @param XSDSchema   file xsd grammatica
     * @param XMLDocument file xml da validate
     * @return true se valido, false in caso contrario
     * @throws IOException
     */
    public static boolean validate(File XSDSchema, File XMLDocument) throws IOException {
        try {
            final Schema schema = factory.newSchema(XSDSchema);
            final Validator validator = schema.newValidator();
            final Source source = new StreamSource(XMLDocument);
            validator.validate(source);
        } catch (SAXException e) {
            return false;
        }
        return true;
    }

    /**
     * Valida una stringa  xml
     *
     * @param XSDSchema file xsd grammatica
     * @param xml       stringa xml da validate
     * @return true se valido, false in caso contrario
     * @throws IOException
     */
    public static boolean validate(File XSDSchema, String xml) throws IOException {
        try {
            final Schema schema = factory.newSchema(XSDSchema);
            final Validator validator = schema.newValidator();
            Source source = new StreamSource(new StringReader(xml));
            validator.validate(source);
        } catch (SAXException e) {
            e.printStackTrace();
//            System.out.println(xml);
            return false;
        }
        return true;
    }

    /**
     * Valida un file xml
     * Richiama {@link #validate(File, String)}
     *
     * @param XSDSchemaPath percorso del file xsd grammatica
     * @param xml           stringa xml da validate
     * @return true se valido, false in caso contrario
     * @throws IOException
     */
    public static boolean validate(String XSDSchemaPath, String xml) throws IOException {
        return validate(new File(XSDSchemaPath), xml);
    }

}
