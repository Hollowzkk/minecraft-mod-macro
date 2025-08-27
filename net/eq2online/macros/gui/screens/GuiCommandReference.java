/*     */ package net.eq2online.macros.gui.screens;
/*     */ 
/*     */ import bib;
/*     */ import bja;
/*     */ import blk;
/*     */ import java.io.IOException;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.gui.GuiControl;
/*     */ import net.eq2online.macros.gui.GuiControlEx;
/*     */ import net.eq2online.macros.gui.controls.GuiListBox;
/*     */ import net.eq2online.macros.gui.list.ListEntry;
/*     */ import net.eq2online.macros.interfaces.IListEntry;
/*     */ import net.eq2online.macros.scripting.IDocumentationEntry;
/*     */ import net.eq2online.macros.scripting.docs.Documentor;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCommandReference
/*     */   extends GuiScreenWithHeader
/*     */ {
/*     */   private final blk parentScreen;
/*     */   private GuiListBox<IDocumentationEntry> commandList;
/*     */   private GuiControl btnOk;
/*     */   private IDocumentationEntry selectedEntry;
/*  30 */   private String filterText = null;
/*     */   
/*     */   private String initialWord;
/*     */ 
/*     */   
/*     */   public GuiCommandReference(bib minecraft, blk parentScreen) {
/*  36 */     this(minecraft, parentScreen, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiCommandReference(bib minecraft, blk parentScreen, String initialWord) {
/*  41 */     super(minecraft, 0, 0);
/*     */     
/*  43 */     this.parentScreen = parentScreen;
/*  44 */     this.drawBackground = false;
/*  45 */     this.bannerCentred = false;
/*  46 */     this.bannerColour = 4259648;
/*  47 */     this.banner = "";
/*  48 */     this.title = I18n.get("reference.title");
/*  49 */     this.initialWord = initialWord;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void b() {
/*  55 */     clearControlList();
/*     */     
/*  57 */     addControl((GuiControl)(this.commandList = new GuiListBox(this.j, 0, 4, 40, 174, this.m - 70, 16, false, false, false)));
/*  58 */     addControl(this.btnOk = new GuiControl(1, this.l - 64, this.m - 24, 60, 20, I18n.get("gui.ok")));
/*     */     
/*  60 */     initList();
/*  61 */     if (this.initialWord != null) {
/*     */       
/*  63 */       selectInitialWord();
/*  64 */       this.initialWord = null;
/*     */     } 
/*     */     
/*  67 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectInitialWord() {
/*  72 */     this.initialWord = this.initialWord.toUpperCase();
/*     */     
/*  74 */     IDocumentationEntry match = null;
/*  75 */     int matchDiff = Integer.MAX_VALUE;
/*     */     
/*  77 */     Documentor documentor = (Documentor)ScriptContext.MAIN.getDocumentor();
/*  78 */     for (String entry : documentor.getEntries()) {
/*     */       
/*  80 */       IDocumentationEntry documentation = documentor.getDocumentation(entry);
/*  81 */       if (documentation != null && !documentation.isHidden() && documentation.getName().toUpperCase().contains(this.initialWord)) {
/*     */         
/*  83 */         int diff = Math.abs(documentation.getName().length() - this.initialWord.length());
/*  84 */         if (diff < matchDiff) {
/*     */           
/*  86 */           matchDiff = diff;
/*  87 */           match = documentation;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     if (match != null) {
/*     */       
/*  94 */       this.commandList.selectData(match);
/*  95 */       updateSelectedCommand();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void initList() {
/* 101 */     Documentor documentor = (Documentor)ScriptContext.MAIN.getDocumentor();
/* 102 */     int id = 0;
/* 103 */     for (String entry : documentor.getEntries()) {
/*     */       
/* 105 */       IDocumentationEntry documentation = documentor.getDocumentation(entry);
/* 106 */       if (documentation != null && !documentation.isHidden() && applyFilter(documentation)) {
/*     */         
/* 108 */         this.commandList.addItem((IListEntry)new ListEntry(id++, entry.toUpperCase(), documentation));
/* 109 */         if (documentation.getName().equalsIgnoreCase(this.filterText))
/*     */         {
/* 111 */           this.commandList.selectData(documentation);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     updateSelectedCommand();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSelectedCommand() {
/*     */     try {
/* 123 */       a((bja)this.commandList);
/*     */     }
/* 125 */     catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean applyFilter(IDocumentationEntry documentation) {
/* 130 */     if (this.filterText == null)
/*     */     {
/* 132 */       return true;
/*     */     }
/*     */     
/* 135 */     return documentation.getName().toUpperCase().contains(this.filterText.toUpperCase());
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyFilterToList() {
/* 140 */     IListEntry<IDocumentationEntry> selectedItem = this.commandList.getSelectedItem();
/* 141 */     Object oldSelection = (selectedItem != null) ? selectedItem.getData() : null;
/* 142 */     this.commandList.clear();
/* 143 */     initList();
/* 144 */     if (selectedItem != null)
/*     */     {
/* 146 */       this.commandList.selectData(oldSelection);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void m() {
/* 153 */     super.m();
/*     */     
/* 155 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseWheelScrolled(int mouseWheelDelta) throws IOException {
/* 162 */     mouseWheelDelta /= 120;
/*     */     
/* 164 */     while (mouseWheelDelta > 0) {
/*     */       
/* 166 */       this.commandList.up();
/* 167 */       a((bja)this.commandList);
/* 168 */       mouseWheelDelta--;
/*     */     } 
/*     */     
/* 171 */     while (mouseWheelDelta < 0) {
/*     */       
/* 173 */       this.commandList.down();
/* 174 */       a((bja)this.commandList);
/* 175 */       mouseWheelDelta++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(int mouseX, int mouseY, float partialTick) {
/* 182 */     if (this.j.f == null)
/*     */     {
/* 184 */       c();
/*     */     }
/*     */     
/* 187 */     a(2, 22, 180, 38, -1607257293);
/* 188 */     a(2, 38, 180, this.m - 28, -1342177280);
/* 189 */     a(182, 22, this.l - 2, 38, -1607257293);
/* 190 */     a(182, 38, this.l - 2, 58, -1342177280);
/* 191 */     a(182, 60, this.l - 2, 76, -1607257293);
/* 192 */     a(182, 76, this.l - 2, this.m - 72, -1342177280);
/* 193 */     a(182, this.m - 70, this.l - 2, this.m - 54, -1607257293);
/* 194 */     a(182, this.m - 54, this.l - 2, this.m - 28, -1342177280);
/* 195 */     a(2, this.m - 26, this.l - 2, this.m - 2, -1342177280);
/*     */     
/* 197 */     c(this.q, I18n.get("reference.cmdlist"), 8, 26, -256);
/* 198 */     c(this.q, I18n.get("reference.syntax"), 190, 26, -256);
/* 199 */     c(this.q, I18n.get("reference.desc"), 190, 64, -256);
/* 200 */     c(this.q, I18n.get("reference.return"), 190, this.m - 66, -256);
/*     */     
/* 202 */     if (this.filterText != null) {
/*     */       
/* 204 */       c(this.q, I18n.get("reference.filtering", new Object[] { this.filterText.toUpperCase() }), 8, this.m - 18, -256);
/*     */     }
/*     */     else {
/*     */       
/* 208 */       c(this.q, I18n.get("reference.filterprompt"), 8, this.m - 18, -22016);
/*     */     } 
/*     */     
/* 211 */     if (this.selectedEntry != null) {
/*     */       
/* 213 */       c(this.q, formatUsage(), 190, 44, -43691);
/* 214 */       this.q.a(this.selectedEntry.getDescription(), 190, 82, this.l - 210, -22016);
/* 215 */       this.q.a(this.selectedEntry.getReturnType(), 190, this.m - 46, this.l - 210, -22016);
/*     */     } 
/*     */     
/* 218 */     super.a(mouseX, mouseY, partialTick);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String formatUsage() {
/* 226 */     String formattedUsage = this.selectedEntry.getUsage();
/*     */     
/* 228 */     if (formattedUsage.indexOf('(') > -1)
/*     */     {
/* 230 */       formattedUsage = "§c" + this.selectedEntry.getName().toUpperCase() + "§6" + formattedUsage.substring(formattedUsage.indexOf('('));
/*     */     }
/*     */     
/* 233 */     return formattedUsage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(char keyChar, int keyCode) throws IOException {
/* 239 */     GuiControlEx.HandledState listBoxAction = this.commandList.listBoxKeyTyped(keyChar, keyCode);
/* 240 */     if (listBoxAction == GuiControlEx.HandledState.ACTION_PERFORMED) {
/*     */       
/* 242 */       a((bja)this.commandList);
/*     */     }
/* 244 */     else if (listBoxAction == GuiControlEx.HandledState.HANDLED) {
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 249 */     if (keyCode == 1) {
/*     */       
/* 251 */       onCloseClick();
/*     */       
/*     */       return;
/*     */     } 
/* 255 */     if (keyCode == 14 && this.filterText != null) {
/*     */       
/* 257 */       if (this.filterText.length() == 1) {
/*     */         
/* 259 */         this.filterText = null;
/*     */       }
/*     */       else {
/*     */         
/* 263 */         this.filterText = this.filterText.substring(0, this.filterText.length() - 1);
/*     */       } 
/*     */       
/* 266 */       applyFilterToList();
/*     */       
/*     */       return;
/*     */     } 
/* 270 */     if (keyChar > ' ' && keyCode != 14) {
/*     */       
/* 272 */       if (this.filterText == null) {
/*     */         
/* 274 */         this.filterText = "" + keyChar;
/*     */       }
/* 276 */       else if (this.filterText.length() < 20) {
/*     */         
/* 278 */         this.filterText += keyChar;
/*     */       } 
/*     */       
/* 281 */       applyFilterToList();
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCloseClick() {
/* 289 */     this.j.a(this.parentScreen);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(bja button) throws IOException {
/* 295 */     super.a(button);
/*     */ 
/*     */     
/*     */     try {
/* 299 */       this.selectedEntry = (IDocumentationEntry)this.commandList.getSelectedItem().getData();
/*     */     }
/* 301 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 305 */     if (button != null && button.k == this.btnOk.k)
/*     */     {
/* 307 */       onCloseClick();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\screens\GuiCommandReference.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */