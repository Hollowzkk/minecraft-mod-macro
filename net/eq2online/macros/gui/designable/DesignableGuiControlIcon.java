/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import ain;
/*     */ import aip;
/*     */ import aow;
/*     */ import bhz;
/*     */ import bib;
/*     */ import bzw;
/*     */ import com.google.common.base.Strings;
/*     */ import com.mumfrey.liteloader.gl.GL;
/*     */ import com.mumfrey.liteloader.gl.GLClippingPlanes;
/*     */ import java.awt.Rectangle;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.compatibility.I18n;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.GuiScreenEx;
/*     */ import net.eq2online.macros.gui.IconResourcePack;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxControlProperties;
/*     */ import net.eq2online.macros.gui.designable.editor.GuiDialogBoxIconProperties;
/*     */ import net.eq2online.macros.scripting.VariableExpander;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ import net.eq2online.util.Colour;
/*     */ import nf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Icon(u = 12, v = 80)
/*     */ public class DesignableGuiControlIcon
/*     */   extends DesignableGuiControlAligned
/*     */ {
/*  36 */   private static final Pattern PATTERN_ITEM_WITH_META = Pattern.compile("^(.*?)[: ]([0-9]+)$");
/*     */   
/*     */   private final bzw itemRenderer;
/*     */   
/*     */   private final IconResourcePack iconResources;
/*     */   private boolean evalIcon;
/*     */   private boolean evalDurability;
/*     */   private String icon;
/*     */   private String durability;
/*     */   private aip itemStack;
/*     */   private nf texture;
/*  47 */   private float scale = 1.0F;
/*     */ 
/*     */   
/*     */   public DesignableGuiControlIcon(Macros macros, bib mc, int id) {
/*  51 */     super(macros, mc, id, "middle centre");
/*     */     
/*  53 */     this.padding = 2;
/*  54 */     this.itemRenderer = mc.ad();
/*  55 */     this.iconResources = macros.getLayoutManager().getIconResources();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initProperties() {
/*  61 */     super.initProperties();
/*     */     
/*  63 */     setProperty("background", "01000000");
/*  64 */     setProperty("icon", this.icon);
/*  65 */     setProperty("scale", 1);
/*  66 */     setProperty("durability", "-1");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onTick(int tickNumber) {
/*  72 */     if (this.evalIcon)
/*     */     {
/*  74 */       setIcon(getProperty("icon", ""));
/*     */     }
/*     */     
/*  77 */     if (this.evalDurability)
/*     */     {
/*  79 */       updateDurability();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiDialogBoxControlProperties getPropertiesDialog(GuiScreenEx parentScreen) {
/*  86 */     return (GuiDialogBoxControlProperties)new GuiDialogBoxIconProperties(this.mc, parentScreen, this, this.iconResources);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropertyWithValidation(String property, String stringValue, int intValue, boolean boolValue) {
/*  92 */     super.setPropertyWithValidation(property, stringValue, intValue, boolValue);
/*     */     
/*  94 */     if ("scale".equals(property)) {
/*     */       
/*  96 */       setProperty(property, Math.min(Math.max(intValue, 1), 10));
/*     */     }
/*  98 */     else if ("damage".equals(property)) {
/*     */       
/* 100 */       setProperty(property, Math.min(Math.max(intValue, 0), 999999));
/*     */     }
/* 102 */     else if ("background".equals(property)) {
/*     */       
/* 104 */       setProperty(property, Colour.sanitiseColour(stringValue, 16777216));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void update() {
/* 111 */     this.backColour = Colour.parseColour(getProperty("background", "01000000"), 16777216);
/*     */     
/* 113 */     String durability = getProperty("durability", "-1");
/* 114 */     this.evalDurability = (durability.indexOf('%') > -1);
/*     */     
/* 116 */     String iconName = getProperty("icon", "");
/* 117 */     this.evalIcon = (iconName.indexOf('%') > -1);
/* 118 */     setIcon(iconName);
/*     */     
/* 120 */     this.scale = Math.min(Math.max(getProperty("scale", 1), 1), 10);
/*     */     
/* 122 */     super.update();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setIcon(String iconName) {
/* 127 */     IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/*     */     
/* 129 */     if (!this.evalIcon || provider == null) {
/*     */       
/* 131 */       parseIcon(iconName);
/*     */       
/*     */       return;
/*     */     } 
/* 135 */     String expanded = (new VariableExpander(provider, null, iconName, false)).toString();
/* 136 */     parseIcon(expanded);
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseIcon(String icon) {
/* 141 */     String durability = getProperty("durability", "-1");
/* 142 */     if (icon.equals(this.icon) && durability.equals(this.durability)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 147 */     this.icon = icon.trim();
/* 148 */     this.durability = durability;
/* 149 */     if (this.icon.endsWith(".png")) {
/*     */       
/* 151 */       setIconResource("macros:textures/custom/" + this.icon);
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     Matcher itemWithMeta = PATTERN_ITEM_WITH_META.matcher(this.icon);
/* 156 */     if (itemWithMeta.matches()) {
/*     */       
/* 158 */       int meta = Integer.parseInt(itemWithMeta.group(2));
/* 159 */       setIconStack(itemWithMeta.group(1), meta);
/*     */       
/*     */       return;
/*     */     } 
/* 163 */     setIconStack(this.icon, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setIconStack(String icon, int meta) {
/* 168 */     this.texture = null;
/*     */     
/* 170 */     ain item = ain.b(icon);
/* 171 */     if (item == null) {
/*     */       
/* 173 */       aow block = aow.b(icon);
/* 174 */       if (block == null) {
/*     */         
/* 176 */         this.itemStack = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       try {
/* 182 */         item = block.a(null, new Random(), 1);
/*     */       }
/* 184 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 188 */       if (item == null) {
/*     */         
/* 190 */         this.itemStack = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 195 */     int maxDamage = item.l();
/* 196 */     if (maxDamage > 0) {
/*     */       
/* 198 */       meta = 0;
/* 199 */       int durability = getDurability();
/* 200 */       if (durability > -1)
/*     */       {
/* 202 */         meta = maxDamage - Math.min(durability, maxDamage);
/*     */       }
/*     */     } 
/*     */     
/* 206 */     this.itemStack = new aip(item, 1, meta);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setIconResource(String resourceName) {
/* 211 */     this.texture = new nf(resourceName);
/* 212 */     this.itemStack = null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateDurability() {
/* 217 */     if (this.itemStack == null || !this.itemStack.f()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 222 */     int meta = 0;
/* 223 */     int durability = getDurability();
/* 224 */     if (durability > -1)
/*     */     {
/* 226 */       meta = this.itemStack.k() - Math.min(durability, this.itemStack.k());
/*     */     }
/*     */     
/* 229 */     if (meta != this.itemStack.i())
/*     */     {
/* 231 */       this.itemStack.b(meta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int getDurability() {
/* 237 */     String durability = getProperty("durability", "-1");
/* 238 */     if (this.evalDurability) {
/*     */       
/* 240 */       IScriptActionProvider provider = ScriptContext.MAIN.getScriptActionProvider();
/* 241 */       if (provider == null)
/*     */       {
/* 243 */         return -1;
/*     */       }
/* 245 */       durability = (new VariableExpander(provider, null, durability, false)).toString().trim();
/*     */     } 
/*     */     
/* 248 */     return Math.max(ScriptCore.tryParseInt(durability, -1), -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void draw(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY) {
/* 254 */     if (isVisible())
/*     */     {
/* 256 */       drawIcon(parent, boundingBox, mouseX, mouseY, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWidget(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, int drawColour) {
/* 263 */     Rectangle innerBoundingBox = new Rectangle(boundingBox.x + 1, boundingBox.y + 1, boundingBox.width - 2, boundingBox.height - 2);
/* 264 */     drawIcon(parent, innerBoundingBox, mouseX, mouseY, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawIcon(DesignableGuiLayout parent, Rectangle boundingBox, int mouseX, int mouseY, boolean widget) {
/* 272 */     float size = 16.0F;
/* 273 */     float yPos = getYPosition(boundingBox, size * this.scale);
/* 274 */     float xPos = getXPosition(boundingBox, size * this.scale);
/*     */     
/* 276 */     if (this.itemStack != null && !this.itemStack.b()) {
/*     */       
/* 278 */       renderBackground(boundingBox, widget);
/* 279 */       GLClippingPlanes.glEnableClipping(boundingBox.x, boundingBox.x + boundingBox.width, boundingBox.y, boundingBox.y + boundingBox.height);
/* 280 */       renderItemStack(xPos, yPos, size);
/* 281 */       GLClippingPlanes.glDisableClipping();
/*     */     }
/* 283 */     else if (this.texture != null) {
/*     */       
/* 285 */       renderBackground(boundingBox, widget);
/* 286 */       renderMask(boundingBox);
/* 287 */       renderIcon(xPos, yPos, size);
/*     */     }
/* 289 */     else if (widget) {
/*     */       
/* 291 */       String message = Strings.isNullOrEmpty(this.icon) ? "control.error.noicon" : "control.error.badicon";
/* 292 */       String text = I18n.get(message, new Object[] { this.icon });
/* 293 */       int textSize = this.fontRenderer.a(text) / 2;
/*     */       
/* 295 */       int xMid = boundingBox.x + Math.min(10 + textSize, boundingBox.width / 2);
/* 296 */       int yMid = boundingBox.y + Math.min(10, boundingBox.height / 2);
/*     */       
/* 298 */       a(xMid - textSize - this.padding, yMid - 4 - this.padding, xMid + textSize + this.padding, yMid + 4 + this.padding, 0xFF000000 | this.backColour);
/*     */ 
/*     */       
/* 301 */       this.fontRenderer.a(text, xMid - textSize, yMid - 4, -65536);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBackground(Rectangle boundingBox, boolean widget) {
/* 307 */     if (!widget) {
/*     */       
/* 309 */       GL.glAlphaFunc(516, 0.0F);
/* 310 */       drawRect(boundingBox, this.backColour);
/* 311 */       GL.glAlphaFunc(516, 0.1F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderItemStack(float xPos, float yPos, float size) {
/*     */     try {
/* 319 */       bhz.c();
/* 320 */       GL.glEnableLighting();
/* 321 */       GL.glEnableCulling();
/* 322 */       GL.glEnableDepthTest();
/* 323 */       GL.glPushMatrix();
/* 324 */       GL.glTranslatef(xPos, yPos, 0.0F);
/* 325 */       GL.glScalef(this.scale, this.scale, 1.0F);
/* 326 */       this.itemRenderer.b(this.itemStack, 0, 0);
/* 327 */       this.itemRenderer.a(this.fontRenderer, this.itemStack, 0, 0);
/* 328 */       GL.glPopMatrix();
/* 329 */       GL.glBlendFunc(770, 771);
/* 330 */       GL.glDisableLighting();
/* 331 */       GL.glDepthFunc(515);
/*     */     }
/* 333 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 336 */       this.itemStack = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderIcon(float xPos, float yPos, float size) {
/*     */     try {
/* 344 */       GL.glDepthFunc(516);
/* 345 */       GL.glEnableDepthTest();
/* 346 */       GL.glAlphaFunc(516, 0.01F);
/* 347 */       GL.glEnableAlphaTest();
/* 348 */       GL.glPushMatrix();
/* 349 */       GL.glTranslatef(xPos, yPos, -200.0F);
/* 350 */       GL.glScalef(this.scale, this.scale, 1.0F);
/* 351 */       GL.glBlendFunc(770, 771);
/* 352 */       GL.glEnableBlend();
/* 353 */       int iSize = (int)size;
/* 354 */       drawTexturedModalIcon(this.texture, iSize, 0, 0, iSize, iSize, 0, 0, iSize, iSize);
/* 355 */       GL.glPopMatrix();
/* 356 */       GL.glDepthFunc(515);
/* 357 */       GL.glAlphaFunc(516, 0.1F);
/*     */     
/*     */     }
/* 360 */     catch (Exception ex) {
/*     */ 
/*     */       
/* 363 */       this.texture = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderMask(Rectangle boundingBox) {
/* 369 */     GL.glAlphaFunc(516, 0.0F);
/* 370 */     GL.glPushMatrix();
/* 371 */     GL.glTranslatef(0.0F, 0.0F, 200.0F);
/* 372 */     GL.glEnableDepthTest();
/* 373 */     GL.glColorMask(false, false, false, true);
/* 374 */     drawRect(boundingBox, -1);
/* 375 */     GL.glColorMask(true, true, true, true);
/* 376 */     GL.glPopMatrix();
/* 377 */     GL.glAlphaFunc(516, 0.1F);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControlIcon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */