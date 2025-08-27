/*     */ package net.eq2online.macros.scripting.docs;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.macros.scripting.IDocumentor;
/*     */ import net.eq2online.macros.scripting.api.IScriptAction;
/*     */ import net.eq2online.xml.Xml;
/*     */ import net.eq2online.xml.Xmlns;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Documentor
/*     */   implements IDocumentor
/*     */ {
/*     */   private static Documentor instance;
/*  27 */   private final Xmlns namespaceContext = new Xmlns();
/*     */ 
/*     */   
/*     */   private Document xml;
/*     */ 
/*     */   
/*     */   private Element root;
/*     */ 
/*     */   
/*  36 */   private Map<String, IDocumentationEntry> actionDoc = new TreeMap<>();
/*     */ 
/*     */   
/*     */   public static Documentor getInstance() {
/*  40 */     if (instance == null)
/*     */     {
/*  42 */       instance = new Documentor();
/*     */     }
/*     */     
/*  45 */     return instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private Documentor() {
/*  50 */     Xml.xmlAddNamespace("sa", "http://eq2online.net/macros/scriptaction");
/*  51 */     this.xml = Xml.xmlCreate();
/*     */     
/*  53 */     this.root = this.xml.createElement("scriptactions");
/*  54 */     this.root.setAttribute("xmlns:sa", "http://eq2online.net/macros/scriptaction");
/*  55 */     this.xml.appendChild(this.root);
/*     */     
/*  57 */     this.namespaceContext.addPrefix("sa", "http://eq2online.net/macros/scriptaction");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentationEntry getDocumentation(String scriptActionName) {
/*  63 */     return this.actionDoc.get(scriptActionName.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentation(IScriptAction scriptAction) {
/*  69 */     if (scriptAction != null && !"net.eq2online.macros.scripting.parser.ScriptAction".equals(scriptAction.getClass().getName()))
/*     */     {
/*  71 */       this.actionDoc.put(scriptAction.toString(), getDocumentation(scriptAction));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentationEntry getDocumentation(IScriptAction scriptAction) {
/*  78 */     Xml.xmlAddNamespace("sa", "http://eq2online.net/macros/scriptaction");
/*  79 */     String xPath = "/scriptactions/sa:" + scriptAction.getClass().getName().toLowerCase().replace(".", "/sa:");
/*  80 */     NodeList nodes = Xml.xmlQuery(this.xml, xPath, (NamespaceContext)this.namespaceContext);
/*     */     
/*  82 */     if (nodes.getLength() > 0)
/*     */     {
/*  84 */       return new DocumentationEntry(nodes.item(0));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendScriptActionNode(IScriptAction scriptAction) {
/*  96 */     String[] parts = scriptAction.getClass().getName().toLowerCase().split("\\.");
/*  97 */     Element parent = this.root;
/*     */     
/*  99 */     for (int i = 0; i < parts.length; i++) {
/*     */       
/* 101 */       Element subNode = createOrGetElement(parent, "sa:" + parts[i]);
/* 102 */       parent = subNode;
/*     */       
/* 104 */       if (i == parts.length - 1) {
/*     */         
/* 106 */         parent.setAttribute("hidden", "false");
/*     */         
/* 108 */         Element nameElement = this.xml.createElement("sa:name");
/* 109 */         nameElement.setTextContent(scriptAction.toString().toUpperCase());
/* 110 */         parent.appendChild(nameElement);
/*     */         
/* 112 */         Element usageElement = this.xml.createElement("sa:usage");
/* 113 */         usageElement.setTextContent(scriptAction.toString().toUpperCase() + "()");
/* 114 */         parent.appendChild(usageElement);
/*     */         
/* 116 */         Element descriptionElement = this.xml.createElement("sa:description");
/* 117 */         descriptionElement.setTextContent("desc");
/* 118 */         parent.appendChild(descriptionElement);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Element createOrGetElement(Element parent, String nodeName) throws DOMException {
/* 125 */     NodeList childNodes = parent.getChildNodes();
/*     */     
/* 127 */     for (int index = 0; index < childNodes.getLength(); index++) {
/*     */       
/* 129 */       if (nodeName.equals(childNodes.item(index).getNodeName()))
/*     */       {
/* 131 */         return (Element)childNodes.item(index);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     Element subNode = this.xml.createElement(nodeName);
/* 136 */     parent.appendChild(subNode);
/* 137 */     return subNode;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IDocumentor loadXml(String language) {
/* 143 */     InputStream is = null;
/*     */ 
/*     */     
/*     */     try {
/* 147 */       is = Documentor.class.getResourceAsStream("/lang/macros/scripting/" + language + ".xml");
/* 148 */       if (is == null && !"en_gb".equals(language))
/*     */       {
/* 150 */         Documentor.class.getResourceAsStream("/lang/macros/scripting/en_gb.xml");
/*     */       }
/*     */       
/* 153 */       if (is != null)
/*     */       {
/* 155 */         Xml.xmlAddNamespace("sa", "http://eq2online.net/macros/scriptaction");
/* 156 */         this.xml = Xml.xmlCreate(is);
/*     */       }
/*     */     
/* 159 */     } catch (Exception ex) {
/*     */       
/* 161 */       ex.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 165 */       if (is != null) {
/*     */         
/*     */         try {
/*     */           
/* 169 */           is.close();
/*     */         }
/* 171 */         catch (IOException ex) {
/*     */           
/* 173 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeXml(File xmlFile) {
/* 184 */     Xml.xmlSave(xmlFile, this.xml);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getEntries() {
/* 189 */     return this.actionDoc.keySet();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\docs\Documentor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */