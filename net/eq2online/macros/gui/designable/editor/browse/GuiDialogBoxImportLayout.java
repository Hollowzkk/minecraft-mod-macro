/*     */ package net.eq2online.macros.gui.designable.editor.browse;
/*     */ 
/*     */ import bib;
/*     */ import bje;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiDialogBox;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class GuiDialogBoxImportLayout
/*     */   extends GuiDialogBox
/*     */ {
/*     */   private final Macros macros;
/*     */   private final LayoutManager layoutManager;
/*     */   private final Node layoutNode;
/*     */   private bje txtNewItemName;
/*     */   private bje txtNewItemDisplayName;
/*  36 */   private String newItemName = "", newItemDisplayName = "";
/*     */   
/*  38 */   private String allowedcharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890_-. ";
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxImportLayout(bib minecraft, GuiScreenEx parentScreen, String windowTitle, String prompt, Macros macros, LayoutManager layoutManager, Node layoutNode) {
/*  43 */     super(minecraft, (blk)parentScreen, 320, 110, windowTitle);
/*  44 */     this.prompt = prompt;
/*     */     
/*  46 */     this.macros = macros;
/*  47 */     this.layoutManager = layoutManager;
/*  48 */     this.layoutNode = layoutNode;
/*  49 */     this.newItemDisplayName = Xml.xmlGetAttribute(layoutNode, "display", "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  55 */     this.txtNewItemName.a();
/*  56 */     this.txtNewItemDisplayName.a();
/*     */     
/*  58 */     super.e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSubmit() {
/*  64 */     this.newItemName = this.txtNewItemName.b();
/*  65 */     this.newItemDisplayName = this.txtNewItemDisplayName.b();
/*     */ 
/*     */     
/*     */     try {
/*  69 */       Element element = (Element)this.layoutNode;
/*  70 */       element.setAttribute("name", this.newItemName);
/*  71 */       element.setAttribute("display", this.newItemDisplayName);
/*     */       
/*  73 */       Xml.xmlClearNamespace();
/*  74 */       Xml.xmlAddNamespace("gc", "http://eq2online.net/macros/guiconfiguration");
/*  75 */       Xml.xmlAddNamespace("gb", "http://eq2online.net/macros/guibinding");
/*     */       
/*  77 */       DesignableGuiLayout layout = new DesignableGuiLayout(this.macros, this.j, this.layoutManager, this.layoutNode);
/*  78 */       layout.register();
/*     */     }
/*  80 */     catch (Exception ex) {
/*     */       
/*  82 */       ex.printStackTrace();
/*     */     } 
/*     */     
/*  85 */     Xml.xmlClearNamespace();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogMouseClicked(int mouseX, int mouseY, int button) throws IOException {
/*  91 */     this.txtNewItemName.a(mouseX, mouseY, button);
/*  92 */     this.txtNewItemDisplayName.a(mouseX, mouseY, button);
/*  93 */     super.dialogMouseClicked(mouseX, mouseY, button);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initDialog() {
/*  99 */     this.txtNewItemName = new bje(0, this.q, this.dialogX + 80, this.dialogY + 30, this.dialogWidth - 90, 16);
/* 100 */     this.txtNewItemName.f(255);
/* 101 */     this.txtNewItemName.a(this.newItemName);
/* 102 */     this.txtNewItemName.b(true);
/*     */     
/* 104 */     this.txtNewItemDisplayName = new bje(1, this.q, this.dialogX + 80, this.dialogY + 54, this.dialogWidth - 90, 16);
/* 105 */     this.txtNewItemDisplayName.f(255);
/* 106 */     this.txtNewItemDisplayName.a(this.newItemDisplayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dialogKeyTyped(char keyChar, int keyCode) {
/* 112 */     if (keyCode == 14 || keyCode == 211 || keyCode == 199 || keyCode == 207 || keyCode == 203 || keyCode == 205 || this.allowedcharacters
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       .indexOf(keyChar) >= 0) {
/*     */       
/* 120 */       String oldName = this.txtNewItemName.b();
/*     */       
/* 122 */       this.txtNewItemName.a(keyChar, keyCode);
/* 123 */       this.txtNewItemDisplayName.a(keyChar, keyCode);
/*     */       
/* 125 */       this.txtNewItemName.g(14737632);
/*     */       
/* 127 */       if (!this.txtNewItemName.b().equals(oldName))
/*     */       {
/* 129 */         this.txtNewItemDisplayName.a(this.txtNewItemName.b());
/*     */       }
/*     */     }
/* 132 */     else if (keyCode == 15) {
/*     */       
/* 134 */       if (this.txtNewItemName.m()) {
/*     */         
/* 136 */         this.txtNewItemName.b(false);
/* 137 */         this.txtNewItemDisplayName.b(true);
/*     */       }
/*     */       else {
/*     */         
/* 141 */         this.txtNewItemName.b(true);
/* 142 */         this.txtNewItemDisplayName.b(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogSubmissionFailed(String reason) {
/* 150 */     this.txtNewItemName.g(16733525);
/* 151 */     this.txtNewItemName.b(true);
/* 152 */     this.txtNewItemDisplayName.b(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validateDialog() {
/* 158 */     String name = this.txtNewItemName.b();
/* 159 */     if (name.length() > 0 && this.txtNewItemDisplayName.b().length() > 0) {
/*     */       
/* 161 */       if (this.layoutManager.layoutExists(name)) {
/*     */         
/* 163 */         this.prompt = I18n.get("patch.create.exists", new Object[] { name });
/* 164 */         return false;
/*     */       } 
/*     */       
/* 167 */       return true;
/*     */     } 
/*     */     
/* 170 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawDialog(int mouseX, int mouseY, float f) {
/* 177 */     c(this.q, this.prompt, this.dialogX + 10, this.dialogY + 10, -22016);
/*     */     
/* 179 */     c(this.q, I18n.get("patch.create.label.name"), this.dialogX + 10, this.dialogY + 36, -256);
/* 180 */     this.txtNewItemName.g();
/*     */     
/* 182 */     c(this.q, I18n.get("patch.create.label.display"), this.dialogX + 10, this.dialogY + 60, -256);
/* 183 */     this.txtNewItemDisplayName.g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNewItemName() {
/* 191 */     return this.newItemName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewItemDisplayName() {
/* 196 */     return this.newItemDisplayName;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\editor\browse\GuiDialogBoxImportLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */