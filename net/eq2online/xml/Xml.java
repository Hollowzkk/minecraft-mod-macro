/*     */ package net.eq2online.xml;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import net.eq2online.console.Log;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public final class Xml
/*     */ {
/*     */   private static XPath xpath;
/*  47 */   private static final Xmlns ns = new Xmlns();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DocumentBuilderFactory documentBuilderFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DocumentBuilder documentBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TransformerFactory transformerFactory;
/*     */ 
/*     */ 
/*     */   
/*     */   private static Transformer transformer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  72 */     xpath = XPathFactory.newInstance().newXPath();
/*  73 */     xpath.setNamespaceContext(ns);
/*     */ 
/*     */     
/*  76 */     documentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  77 */     documentBuilderFactory.setNamespaceAware(true);
/*  78 */     createDocumentBuilder(documentBuilderFactory);
/*     */ 
/*     */     
/*  81 */     transformerFactory = TransformerFactory.newInstance();
/*  82 */     createTransformer(transformerFactory);
/*     */   }
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
/*     */   private static boolean createDocumentBuilder(DocumentBuilderFactory factory) {
/*  96 */     if (documentBuilder == null) {
/*     */       
/*     */       try {
/*     */ 
/*     */         
/* 101 */         documentBuilder = documentBuilderFactory.newDocumentBuilder();
/*     */       } catch (ParserConfigurationException e) {
/* 103 */         return false;
/*     */       } 
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean createTransformer(TransformerFactory factory) {
/* 118 */     if (transformer == null) {
/*     */ 
/*     */       
/*     */       try {
/* 122 */         transformer = transformerFactory.newTransformer();
/*     */       } catch (TransformerConfigurationException e) {
/* 124 */         return false;
/*     */       } 
/*     */       
/* 127 */       transformer.setOutputProperty("omit-xml-declaration", "no");
/* 128 */       transformer.setOutputProperty("indent", "yes");
/* 129 */       transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document xmlCreate() {
/* 141 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.newDocument() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document xmlCreate(String uri) throws SAXException, IOException {
/* 151 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(uri) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document xmlCreate(File file) throws SAXException, IOException {
/* 161 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(file) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Document xmlCreate(InputStream stream) throws SAXException, IOException {
/* 166 */     return createDocumentBuilder(documentBuilderFactory) ? documentBuilder.parse(stream) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean xmlSave(File file, Document document) {
/* 178 */     if (createTransformer(transformerFactory)) {
/*     */       
/*     */       try {
/*     */         
/* 182 */         transformer.transform(new DOMSource(document), new StreamResult(file));
/* 183 */         return true;
/*     */       }
/* 185 */       catch (TransformerException transformerException) {}
/*     */     }
/*     */     
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void xmlAddNamespace(String prefix, String namespaceURI) {
/* 199 */     ns.addPrefix(prefix, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void xmlClearNamespace() {
/* 207 */     ns.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList xmlQuery(Node xml, String xPath) {
/* 218 */     return xmlQuery(xml, xPath, ns);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList xmlQuery(Node xml, String xPath, NamespaceContext namespaceContext) {
/*     */     try {
/* 225 */       xpath.setNamespaceContext(namespaceContext);
/* 226 */       return (NodeList)xpath.evaluate(xPath, xml, XPathConstants.NODESET);
/*     */     }
/* 228 */     catch (XPathExpressionException ex) {
/*     */       
/* 230 */       Log.printStackTrace(ex);
/* 231 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Node> xmlNodes(Node xml, String xPath) {
/* 237 */     return xmlNodes(xml, xPath, ns);
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<Node> xmlNodes(Node xml, String xPath, NamespaceContext namespaceContext) {
/* 242 */     List<Node> nodes = new ArrayList<>();
/* 243 */     NodeList result = xmlQuery(xml, xPath, namespaceContext);
/*     */     
/* 245 */     if (result != null)
/*     */     {
/* 247 */       for (int nodeIndex = 0; nodeIndex < result.getLength(); nodeIndex++) {
/* 248 */         nodes.add(result.item(nodeIndex));
/*     */       }
/*     */     }
/* 251 */     return nodes;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Node xmlGet(Node node, String nodeName) {
/* 256 */     return xmlGet(node, nodeName, ns);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Node xmlGet(Node node, String nodeName, NamespaceContext namespaceContext) {
/*     */     try {
/* 263 */       xpath.setNamespaceContext(namespaceContext);
/* 264 */       return (Node)xpath.evaluate(nodeName, node, XPathConstants.NODE);
/*     */     }
/* 266 */     catch (XPathExpressionException xPathExpressionException) {
/*     */       
/* 268 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String xmlGetValue(Node node, String nodeName, String defaultValue) {
/* 273 */     return xmlGetValue(node, nodeName, defaultValue, ns);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String xmlGetValue(Node node, String nodeName, String defaultValue, NamespaceContext namespaceContext) {
/*     */     try {
/* 280 */       xpath.setNamespaceContext(namespaceContext);
/* 281 */       NodeList nodes = (NodeList)xpath.evaluate(nodeName, node, XPathConstants.NODESET);
/*     */       
/* 283 */       if (nodes.getLength() > 0) {
/* 284 */         return nodes.item(0).getTextContent();
/*     */       }
/* 286 */     } catch (XPathExpressionException ex) {
/*     */       
/* 288 */       Log.printStackTrace(ex);
/*     */     } 
/*     */     
/* 291 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int xmlGetValue(Node node, String nodeName, int defaultValue) {
/* 296 */     return xmlGetValue(node, nodeName, defaultValue, ns);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int xmlGetValue(Node node, String nodeName, int defaultValue, NamespaceContext namespaceContext) {
/* 301 */     String nodeValue = xmlGetValue(node, nodeName, "" + defaultValue, namespaceContext);
/*     */ 
/*     */     
/*     */     try {
/* 305 */       return Integer.parseInt(nodeValue);
/*     */     }
/* 307 */     catch (NumberFormatException ex) {
/*     */       
/* 309 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String xmlGetAttribute(Node node, String attributeName, String defaultValue) {
/* 315 */     Node attribute = node.getAttributes().getNamedItem(attributeName);
/*     */     
/* 317 */     if (attribute != null)
/*     */     {
/* 319 */       return attribute.getTextContent();
/*     */     }
/*     */     
/* 322 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int xmlGetAttribute(Node node, String attributeName, int defaultValue) {
/* 327 */     String attributeValue = xmlGetAttribute(node, attributeName, "" + defaultValue);
/*     */ 
/*     */     
/*     */     try {
/* 331 */       return Integer.parseInt(attributeValue);
/*     */     }
/* 333 */     catch (NumberFormatException ex) {
/*     */       
/* 335 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\xml\Xml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */