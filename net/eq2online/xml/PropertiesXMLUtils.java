/*     */ package net.eq2online.xml;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import java.util.InvalidPropertiesFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import net.eq2online.console.Log;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertiesXMLUtils
/*     */ {
/*     */   private static final String PROPS_DTD_URI = "http://eq2online.net/dtd/properties.dtd";
/*     */   private static final String PROPS_DTD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, (entry|array)* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry key CDATA #REQUIRED><!ELEMENT array (element*) ><!ATTLIST array key CDATA #REQUIRED><!ATTLIST array type (boolean|int|string) #REQUIRED ><!ELEMENT element (#PCDATA) ><!ATTLIST element pos CDATA \"0\">";
/*     */   private static final String EXTERNAL_XML_VERSION = "1.0";
/*     */   
/*     */   public static boolean load(Properties props, IArrayStorageBundle arrayStorage, InputStream in) throws IOException, InvalidPropertiesFormatException {
/*  88 */     Document doc = null;
/*     */     
/*     */     try {
/*  91 */       in.mark(1048576);
/*  92 */       doc = getLoadingDoc(in);
/*     */     }
/*  94 */     catch (SAXException saxe) {
/*     */       
/*  96 */       Log.info("Outdated variable store found, updating to new format");
/*  97 */       in.reset();
/*  98 */       props.loadFromXML(in);
/*  99 */       return true;
/*     */     } 
/* 101 */     Element propertiesElement = (Element)doc.getChildNodes().item(1);
/* 102 */     String xmlVersion = propertiesElement.getAttribute("version");
/* 103 */     if (xmlVersion.compareTo("1.0") > 0)
/*     */     {
/* 105 */       throw new InvalidPropertiesFormatException("Exported Properties file format version " + xmlVersion + " is not supported. This java installation can read versions " + "1.0" + " or older. You may need to install a newer version of JDK.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 110 */     importProperties(props, arrayStorage, propertiesElement);
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Document getLoadingDoc(InputStream in) throws SAXException, IOException {
/* 118 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 119 */     dbf.setIgnoringElementContentWhitespace(true);
/* 120 */     dbf.setValidating(true);
/* 121 */     dbf.setCoalescing(true);
/* 122 */     dbf.setIgnoringComments(true);
/*     */     
/*     */     try {
/* 125 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 126 */       db.setEntityResolver(new Resolver());
/* 127 */       db.setErrorHandler(new EH());
/* 128 */       InputSource is = new InputSource(in);
/* 129 */       return db.parse(is);
/*     */     }
/* 131 */     catch (ParserConfigurationException x) {
/*     */       
/* 133 */       throw new Error(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void importProperties(IArrayStorageBundle arrayStorage, Node propertiesNode) {
/* 139 */     if (propertiesNode instanceof Element)
/*     */     {
/* 141 */       importProperties(null, arrayStorage, (Element)propertiesNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void importProperties(Properties props, IArrayStorageBundle arrayStorage, Element propertiesElement) {
/* 147 */     arrayStorage.preDeserialise();
/*     */     
/* 149 */     NodeList entries = propertiesElement.getChildNodes();
/* 150 */     int numEntries = entries.getLength();
/* 151 */     int start = (numEntries > 0 && entries.item(0).getNodeName().equals("comment")) ? 1 : 0;
/* 152 */     for (int i = start; i < numEntries; i++) {
/*     */       
/* 154 */       if (entries.item(i) != null && entries.item(i) instanceof Element) {
/*     */         
/* 156 */         Element entry = (Element)entries.item(i);
/* 157 */         if (entry.hasAttribute("key")) {
/*     */           
/* 159 */           String key = entry.getAttribute("key");
/* 160 */           if (entry.getTagName().equals("array") && entry.hasAttribute("type")) {
/*     */             
/* 162 */             String type = entry.getAttribute("type");
/* 163 */             Map<Integer, ?> arrayMap = deserialiseArray(entry, type);
/* 164 */             Map<String, Map<Integer, ?>> arrays = arrayStorage.getStorage(type);
/* 165 */             if (arrays != null)
/*     */             {
/* 167 */               arrays.put(key, arrayMap);
/*     */             }
/*     */             else
/*     */             {
/* 171 */               Log.info("Couldn't find array storage for type \"" + type + "\"!");
/*     */             }
/*     */           
/* 174 */           } else if (props != null) {
/*     */             
/* 176 */             Node n = entry.getFirstChild();
/* 177 */             String val = (n == null) ? "" : n.getNodeValue();
/* 178 */             props.setProperty(key, val);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     arrayStorage.postDeserialise();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Integer, ?> deserialiseArray(Element entry, String arrayType) {
/* 188 */     TreeMap<Integer, Object> arrayMap = new TreeMap<>();
/*     */     
/* 190 */     NodeList entries = entry.getChildNodes();
/* 191 */     int numEntries = entries.getLength();
/* 192 */     for (int i = 0; i < numEntries; i++) {
/*     */       
/* 194 */       if (entries.item(i) instanceof Element) {
/*     */         
/* 196 */         Element arrayElement = (Element)entries.item(i);
/* 197 */         if (arrayElement.hasAttribute("pos")) {
/* 198 */           Integer pos = Integer.valueOf(Integer.parseInt(arrayElement.getAttribute("pos")));
/* 199 */           Node node = arrayElement.getFirstChild();
/*     */           
/* 201 */           if (node != null && node.getNodeValue().length() > 0)
/*     */           {
/* 203 */             arrayMap.put(pos, getNodeValue(arrayType, node));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 208 */     return arrayMap;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object getNodeValue(String arrayType, Node node) throws DOMException, NumberFormatException {
/* 213 */     if (arrayType.equals("boolean"))
/*     */     {
/* 215 */       return Boolean.valueOf(Boolean.parseBoolean(node.getNodeValue()));
/*     */     }
/* 217 */     if (arrayType.equals("int"))
/*     */     {
/* 219 */       return Integer.valueOf(Integer.parseInt(node.getNodeValue()));
/*     */     }
/*     */     
/* 222 */     return node.getNodeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void save(Properties props, IArrayStorageBundle arrayStorage, OutputStream os, String comment, String encoding) throws IOException {
/* 228 */     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
/* 229 */     DocumentBuilder documentBuilder = null;
/*     */     
/*     */     try {
/* 232 */       documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */     }
/* 234 */     catch (ParserConfigurationException pce) {
/*     */       assert false;
/*     */     } 
/*     */ 
/*     */     
/* 239 */     if (documentBuilder != null) {
/*     */       
/* 241 */       Document doc = documentBuilder.newDocument();
/* 242 */       Element properties = (Element)doc.appendChild(doc.createElement("properties"));
/*     */       
/* 244 */       if (comment != null) {
/*     */         
/* 246 */         Element comments = (Element)properties.appendChild(doc.createElement("comment"));
/* 247 */         comments.appendChild(doc.createTextNode(comment));
/*     */       } 
/*     */       
/* 250 */       Iterator<String> propertiesIterator = props.keySet().iterator();
/* 251 */       while (propertiesIterator.hasNext()) {
/*     */         
/* 253 */         String key = propertiesIterator.next();
/* 254 */         Element entry = (Element)properties.appendChild(doc.createElement("entry"));
/* 255 */         entry.setAttribute("key", key);
/* 256 */         entry.appendChild(doc.createTextNode(props.getProperty(key)));
/*     */       } 
/*     */       
/* 259 */       arrayStorage.preSerialise();
/*     */       
/* 261 */       for (String storageType : arrayStorage.getStorageTypes())
/*     */       {
/* 263 */         serialiseArrays(storageType, arrayStorage.getStorage(storageType), doc, properties);
/*     */       }
/*     */       
/* 266 */       arrayStorage.postSerialise();
/*     */       
/* 268 */       emitDocument(doc, os, encoding);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void serialiseArrays(String type, Map<String, Map<Integer, ?>> arrays, Document doc, Element properties) throws DOMException {
/* 274 */     for (Map.Entry<String, Map<Integer, ?>> arrayEntry : arrays.entrySet()) {
/*     */       
/* 276 */       Element entry = (Element)properties.appendChild(doc.createElement("array"));
/* 277 */       entry.setAttribute("key", arrayEntry.getKey());
/* 278 */       entry.setAttribute("type", type);
/*     */       
/* 280 */       for (Map.Entry<Integer, ?> mapEntry : (Iterable<Map.Entry<Integer, ?>>)((Map)arrayEntry.getValue()).entrySet()) {
/*     */         
/* 282 */         Element arrayElement = (Element)entry.appendChild(doc.createElement("element"));
/* 283 */         arrayElement.setAttribute("pos", String.valueOf(mapEntry.getKey()));
/* 284 */         arrayElement.appendChild(doc.createTextNode(String.valueOf(mapEntry.getValue())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void emitDocument(Document doc, OutputStream os, String encoding) throws IOException {
/* 291 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 292 */     Transformer transformer = null;
/*     */     
/*     */     try {
/* 295 */       transformer = transformerFactory.newTransformer();
/* 296 */       transformer.setOutputProperty("doctype-system", "http://eq2online.net/dtd/properties.dtd");
/* 297 */       transformer.setOutputProperty("indent", "yes");
/* 298 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
/* 299 */       transformer.setOutputProperty("method", "xml");
/* 300 */       transformer.setOutputProperty("encoding", encoding);
/* 301 */       transformer.setOutputProperty("omit-xml-declaration", "no");
/*     */     }
/* 303 */     catch (TransformerConfigurationException tce) {
/*     */       assert false;
/*     */     } 
/*     */     
/* 307 */     DOMSource doms = new DOMSource(doc);
/* 308 */     StreamResult sr = new StreamResult(os);
/*     */     
/*     */     try {
/* 311 */       if (transformer != null)
/*     */       {
/* 313 */         transformer.transform(doms, sr);
/*     */       }
/*     */     }
/* 316 */     catch (TransformerException te) {
/*     */       
/* 318 */       IOException ioe = new IOException();
/* 319 */       ioe.initCause(te);
/* 320 */       throw ioe;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class Resolver
/*     */     implements EntityResolver {
/*     */     private Resolver() {}
/*     */     
/*     */     public InputSource resolveEntity(String pid, String sid) throws SAXException {
/* 329 */       if (sid.equals("http://eq2online.net/dtd/properties.dtd")) {
/*     */ 
/*     */         
/* 332 */         InputSource is = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!-- DTD for properties --><!ELEMENT properties ( comment?, (entry|array)* ) ><!ATTLIST properties version CDATA #FIXED \"1.0\"><!ELEMENT comment (#PCDATA) ><!ELEMENT entry (#PCDATA) ><!ATTLIST entry key CDATA #REQUIRED><!ELEMENT array (element*) ><!ATTLIST array key CDATA #REQUIRED><!ATTLIST array type (boolean|int|string) #REQUIRED ><!ELEMENT element (#PCDATA) ><!ATTLIST element pos CDATA \"0\">"));
/* 333 */         is.setSystemId("http://eq2online.net/dtd/properties.dtd");
/* 334 */         return is;
/*     */       } 
/* 336 */       throw new SAXException("Invalid system identifier: " + sid);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EH
/*     */     implements ErrorHandler {
/*     */     private EH() {}
/*     */     
/*     */     public void error(SAXParseException x) throws SAXException {
/* 345 */       throw x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fatalError(SAXParseException x) throws SAXException {
/* 351 */       throw x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void warning(SAXParseException x) throws SAXException {
/* 357 */       throw x;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\xml\PropertiesXMLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */