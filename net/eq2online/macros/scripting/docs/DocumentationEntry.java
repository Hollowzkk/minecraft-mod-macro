/*     */ package net.eq2online.macros.scripting.docs;
/*     */ 
/*     */ import bip;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import net.eq2online.macros.gui.GuiRenderer;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class DocumentationEntry
/*     */   extends GuiRenderer
/*     */   implements IDocumentationEntry
/*     */ {
/*     */   private final boolean hidden;
/*     */   private final String name;
/*     */   private final String usage;
/*     */   private final String description;
/*     */   private final String returnType;
/*     */   
/*     */   public DocumentationEntry(String name, String usage, String description, String returnType, boolean hidden) {
/*  56 */     super(null);
/*     */     
/*  58 */     this.name = name;
/*  59 */     this.usage = usage;
/*  60 */     this.description = description;
/*  61 */     this.returnType = returnType;
/*  62 */     this.hidden = hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentationEntry(Node xmlNode) {
/*  72 */     this(
/*  73 */         Xml.xmlGetValue(xmlNode, "sa:name", ""), 
/*  74 */         Xml.xmlGetValue(xmlNode, "sa:usage", ""), 
/*  75 */         Xml.xmlGetValue(xmlNode, "sa:description", ""), 
/*  76 */         Xml.xmlGetValue(xmlNode, "sa:return", ""), 
/*  77 */         Xml.xmlGetAttribute(xmlNode, "hidden", "false").equalsIgnoreCase("true"));
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
/*     */   
/*     */   public void drawAt(bip fontRenderer, int xPosition, int yPosition) {
/*  92 */     if (this.hidden) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  97 */     int descriptionWidth = fontRenderer.a(this.description);
/*  98 */     int usageWidth = fontRenderer.a(this.usage);
/*     */     
/* 100 */     int width = Math.max(descriptionWidth, usageWidth) + 6;
/* 101 */     int height = 25;
/*     */     
/* 103 */     GL.glPushMatrix();
/* 104 */     GL.glTranslatef(xPosition, yPosition, 0.0F);
/* 105 */     a(width, 2, width + 3, height + 2, 1073741824);
/* 106 */     a(4, height, width, height + 2, 1073741824);
/* 107 */     drawGradientCornerRect(0, 0, width, height, -570425515, -570425362, 0.5F);
/*     */     
/* 109 */     fontRenderer.a(this.usage, 3, 3, 11141120);
/* 110 */     fontRenderer.a(this.description, 3, 13, 170);
/*     */     
/* 112 */     GL.glPopMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHidden() {
/* 118 */     return this.hidden;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 124 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsage() {
/* 130 */     return this.usage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 136 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReturnType() {
/* 142 */     return this.returnType;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\docs\DocumentationEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */