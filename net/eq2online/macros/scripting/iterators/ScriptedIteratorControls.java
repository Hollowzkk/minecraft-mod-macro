/*     */ package net.eq2online.macros.scripting.iterators;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.MacroTemplate;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiControl;
/*     */ import net.eq2online.macros.gui.designable.DesignableGuiLayout;
/*     */ import net.eq2online.macros.gui.designable.LayoutManager;
/*     */ import net.eq2online.macros.scripting.ScriptedIterator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IMacroTemplate;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
/*     */ 
/*     */ 
/*     */ public class ScriptedIteratorControls
/*     */   extends ScriptedIterator
/*     */ {
/*     */   private static final String WILDCARD = "*";
/*  22 */   private static final Pattern PATTERN_SPECIFIER_OUTER = Pattern.compile("^controls\\((.+)\\)$");
/*     */   
/*  24 */   private static final Pattern PATTERN_SPECIFIER = Pattern.compile("^(.*?):(.+?)$");
/*     */   
/*     */   private final String specifier;
/*     */ 
/*     */   
/*     */   public ScriptedIteratorControls(IScriptActionProvider provider, IMacro macro, String iteratorName) {
/*  30 */     super(provider, macro);
/*  31 */     this.specifier = getSpecifier(iteratorName);
/*  32 */     populate(getControls());
/*     */   }
/*     */ 
/*     */   
/*     */   private void populate(List<DesignableGuiControl> controls) {
/*  37 */     for (DesignableGuiControl control : controls) {
/*     */       
/*  39 */       begin();
/*  40 */       add("CONTROLID", Integer.valueOf(control.getId()));
/*  41 */       add("CONTROLNAME", control.getName());
/*  42 */       add("CONTROLTYPE", control.getType());
/*  43 */       end();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<DesignableGuiControl> getControls() {
/*  49 */     List<DesignableGuiControl> controls = new ArrayList<>();
/*  50 */     LayoutManager manager = getLayoutManager();
/*  51 */     if (manager != null) {
/*     */       
/*  53 */       String layoutName = getLayoutName();
/*  54 */       String controlType = getControlType();
/*     */       
/*  56 */       for (String name : manager.getLayoutNames()) {
/*     */         
/*  58 */         if (layoutName == null || layoutName.equals(name))
/*     */         {
/*  60 */           addControlsFrom(manager.getLayout(name), controlType, controls);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     return controls;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addControlsFrom(DesignableGuiLayout layout, String controlType, List<DesignableGuiControl> controls) {
/*  70 */     for (DesignableGuiControl control : layout.getControls()) {
/*     */       
/*  72 */       String type = control.toString();
/*  73 */       if (("*".equals(controlType) || controlType.equals(type)) && !controls.contains(control))
/*     */       {
/*  75 */         controls.add(control);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LayoutManager getLayoutManager() {
/*  82 */     IMacroTemplate template = this.macro.getTemplate();
/*  83 */     if (template instanceof MacroTemplate)
/*     */     {
/*  85 */       return ((MacroTemplate)template).getMacroManager().getLayoutManager();
/*     */     }
/*     */     
/*  88 */     return ((Macros)this.provider.getMacroEngine()).getLayoutManager();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLayoutName() {
/*  93 */     if (this.specifier != null) {
/*     */       
/*  95 */       Matcher matcher = PATTERN_SPECIFIER.matcher(this.specifier);
/*  96 */       if (matcher.matches()) {
/*     */         
/*  98 */         String layoutName = matcher.group(1);
/*  99 */         return (layoutName.length() > 0 && !"*".equals(layoutName)) ? layoutName : null;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     return this.specifier;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getControlType() {
/* 108 */     if (this.specifier != null) {
/*     */       
/* 110 */       Matcher matcher = PATTERN_SPECIFIER.matcher(this.specifier);
/* 111 */       if (matcher.matches())
/*     */       {
/* 113 */         return matcher.group(2);
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return "*";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getSpecifier(String iteratorName) {
/* 122 */     Matcher matcher = PATTERN_SPECIFIER_OUTER.matcher(iteratorName);
/* 123 */     if (matcher.matches())
/*     */     {
/* 125 */       return matcher.group(1).trim().toLowerCase();
/*     */     }
/*     */     
/* 128 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\iterators\ScriptedIteratorControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */