/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Set;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxSliderProperties;
/*     */ import net.eq2online.macros.scripting.Variable;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.xml.Xml;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ @Icon(u = 12, v = 88)
/*     */ public class DesignableGuiControlSlider
/*     */   extends DesignableGuiControlRanged
/*     */ {
/*     */   private String bindingName;
/*     */   private boolean dragging;
/*     */   private int updateCounter;
/*     */   private boolean dirty;
/*     */   
/*     */   static class ValueProvider
/*     */     implements IVariableProvider {
/*     */     private final Integer value;
/*     */     
/*     */     ValueProvider(int value) {
/*  35 */       this.value = Integer.valueOf(value);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateVariables(boolean clock) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Object getVariable(String variableName) {
/*  51 */       if ("VALUE".equals(variableName))
/*     */       {
/*  53 */         return this.value;
/*     */       }
/*  55 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<String> getVariables() {
/*  61 */       return (Set<String>)ImmutableSet.of("VALUE");
/*     */     }
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
/*     */   protected DesignableGuiControlSlider(Macros macros, bib mc, int id) {
/*  76 */     super(macros, mc, id);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  82 */     super.initProperties();
/*     */     
/*  84 */     setProperty("binding", "");
/*  85 */     setProperty("hotkeydec", 0);
/*  86 */     setProperty("hotkeyinc", 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindable() {
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchOnClick() {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/* 104 */     if ("hotkeydec".equals(property) || "hotkeyinc".equals(property)) setProperty(property, intValue); 
/* 105 */     if ("binding".equals(property)) setProperty(property, stringValue); 
/* 106 */     if ("hide".equals(property)) setProperty(property, boolValue);
/*     */     
/* 108 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/* 114 */     this.updateCounter++;
/*     */     
/* 116 */     if (!this.dragging) {
/*     */       
/* 118 */       IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */       
/* 120 */       if (provider != null) {
/*     */         
/* 122 */         calcMinMax(provider);
/* 123 */         if (isBindingSet()) {
/*     */           
/* 125 */           if (isBindingValid()) {
/*     */             
/* 127 */             Object oVariableValue = provider.getVariable(this.bindingName, null);
/* 128 */             setValueClamped((oVariableValue instanceof Integer) ? ((Integer)oVariableValue).intValue() : 0);
/*     */             
/*     */             return;
/*     */           } 
/* 132 */           setValueClamped(0);
/*     */         }
/* 134 */         else if (this.dirty && this.updateCounter % 10 == 0) {
/*     */           
/* 136 */           this.dirty = false;
/* 137 */           this.macros.getLayoutManager().saveSettings();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBindingSet() {
/* 145 */     this.bindingName = getProperty("binding", "");
/* 146 */     return !this.bindingName.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBindingValid() {
/* 151 */     return (Variable.couldBeInt(this.bindingName) && this.bindingName.startsWith("@"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 157 */     this.bindingName = getProperty("binding", "");
/* 158 */     super.update();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void load(Node xml) {
/* 164 */     this.currentValue = Xml.xmlGetAttribute(xml, "value", 0);
/* 165 */     super.load(xml);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void loadProperty(String name, String content) {
/* 171 */     if (!"value".equals(name))
/*     */     {
/* 173 */       super.loadProperty(name, content);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Document xml, Element controlNode, boolean export) {
/* 180 */     controlNode.setAttribute("value", String.valueOf(this.currentValue));
/* 181 */     super.save(xml, controlNode, export);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleClick(Rectangle boundingBox, int mouseX, int mouseY, DesignableGuiControl.Listener listener) {
/* 187 */     this.dragging = true;
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseMove(Rectangle boundingBox, int mouseX, int mouseY) {
/* 194 */     if (this.dragging) {
/*     */       
/* 196 */       float pos = (mouseX - boundingBox.x - this.padding);
/* 197 */       float fSize = (this.currentMax - this.currentMin);
/* 198 */       float fValue = this.currentMin + pos / (boundingBox.width - (this.padding * 2) - 2.0F) * fSize;
/* 199 */       setValue((int)fValue);
/* 200 */       if (!isBindingSet())
/*     */       {
/* 202 */         this.dirty = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseReleased(Rectangle boundingBox, int mouseX, int mouseY) {
/* 210 */     if (this.dragging) {
/*     */       
/* 212 */       handleMouseMove(boundingBox, mouseX, mouseY);
/* 213 */       dispatch((DesignableGuiControl.Listener)null);
/*     */     } 
/* 215 */     this.dragging = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleKeyTyped(char keyChar, int keyCode, DesignableGuiControl.Listener listener) {
/* 221 */     int hotkeyinc = getProperty("hotkeyinc", 0);
/* 222 */     if (hotkeyinc != 0 && hotkeyinc == keyCode) {
/*     */       
/* 224 */       setValue(this.currentValue + 1);
/* 225 */       dispatch((DesignableGuiControl.Listener)null);
/* 226 */       return true;
/*     */     } 
/*     */     
/* 229 */     int hotkeydec = getProperty("hotkeydec", 0);
/* 230 */     if (hotkeydec != 0 && hotkeydec == keyCode) {
/*     */       
/* 232 */       setValue(this.currentValue - 1);
/* 233 */       dispatch((DesignableGuiControl.Listener)null);
/* 234 */       return true;
/*     */     } 
/*     */     
/* 237 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setValue(int value) {
/* 242 */     setValueClamped(value);
/*     */     
/* 244 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */     
/* 246 */     if (provider != null && isBindingSet() && isBindingValid())
/*     */     {
/* 248 */       provider.setVariable(null, this.bindingName, String.valueOf(this.currentValue), this.currentValue, (this.currentValue != 0));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected IVariableProvider getScriptContextProvider() {
/* 255 */     return new ValueProvider(this.currentValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 261 */     if (drawSlider(boundingBox, this.foreColour, this.backColour, true))
/*     */     {
/* 263 */       drawRectOutline(boundingBox, this.foreColour, 1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 270 */     drawSlider(boundingBox, -8355712, 0, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean drawSlider(Rectangle boundingBox, int foreColour, int backColour, boolean playback) {
/* 279 */     if (playback && !isVisible())
/*     */     {
/* 281 */       return false;
/*     */     }
/*     */     
/* 284 */     this.e = -this.zIndex;
/*     */     
/* 286 */     if (backColour != 0) {
/*     */       
/* 288 */       GL.glAlphaFunc(516, 0.0F);
/* 289 */       a(boundingBox.x, boundingBox.y, boundingBox.x + boundingBox.width, boundingBox.y + boundingBox.height, backColour);
/* 290 */       GL.glAlphaFunc(516, 0.1F);
/*     */     } 
/*     */     
/* 293 */     float fValue = (this.currentValue - this.currentMin);
/* 294 */     float fSize = (this.currentMax - this.currentMin);
/*     */     
/* 296 */     if (fSize > 0.0F) {
/*     */       
/* 298 */       int padding = (boundingBox.height > this.padding * 2 + 1) ? this.padding : 0;
/*     */       
/* 300 */       float innerWidth = Math.max(boundingBox.width - padding * 2 - 4, 1);
/* 301 */       int innerHeight = Math.max(boundingBox.height - padding * 2, 1);
/* 302 */       int innerMid = boundingBox.y + innerHeight / 2 + padding;
/* 303 */       int tickTop = innerMid - innerHeight / 4;
/* 304 */       int tickBottom = innerMid + innerHeight / 4;
/*     */       
/* 306 */       int barPos = (int)(fValue / fSize * innerWidth);
/*     */       
/* 308 */       int foreAlpha = (foreColour >> 24 & 0xFF) / 2;
/* 309 */       int blended = foreColour & 0xFFFFFF;
/* 310 */       int tickColour = (foreAlpha << 24) + blended;
/*     */       
/* 312 */       int innerTop = boundingBox.y + padding;
/* 313 */       int innerLeft = boundingBox.x + padding;
/* 314 */       int innerRight = boundingBox.x + boundingBox.width - padding;
/*     */       
/* 316 */       GL.glEnableDepthTest();
/* 317 */       GL.glPushMatrix();
/* 318 */       GL.glTranslatef(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */       
/* 321 */       a(innerLeft + 1, tickTop, innerLeft + 2, tickBottom, tickColour);
/* 322 */       a(innerRight - 1, tickTop, innerRight - 2, tickBottom, tickColour);
/*     */       
/* 324 */       float left = (innerLeft + 2);
/* 325 */       float increment = (boundingBox.width - padding * 2 - 2) / fSize;
/*     */ 
/*     */       
/* 328 */       if (fSize < 11.0F || increment > 8.0F)
/*     */       {
/* 330 */         for (int i = 1; i < fSize; i++) {
/*     */           
/* 332 */           left += increment;
/* 333 */           a((int)(left - 1.0F), tickTop, (int)(left + 0.0F), tickBottom, tickColour);
/*     */         } 
/*     */       }
/* 336 */       GL.glPopMatrix();
/*     */ 
/*     */       
/* 339 */       a(innerLeft + 1, innerMid - 1, innerRight - 1, innerMid + 1, tickColour);
/* 340 */       GL.glDisableDepthTest();
/*     */ 
/*     */       
/* 343 */       a(innerLeft + barPos, innerTop, innerLeft + barPos + 4, innerTop + innerHeight, foreColour);
/*     */     } 
/*     */     
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/* 352 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxSliderProperties(this.mc, parentScreen, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */