/*     */ package net.eq2online.macros.core.params.providers;
/*     */ 
/*     */ import bib;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.core.MacroParamProvider;
/*     */ import net.eq2online.macros.core.MacroParams;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.core.params.MacroParam;
/*     */ import net.eq2online.macros.core.params.MacroParamList;
/*     */ import net.eq2online.macros.interfaces.IMacroParamTarget;
/*     */ import net.eq2online.macros.scripting.parser.ScriptCore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MacroParamProviderList<TItem>
/*     */   extends MacroParamProvider<TItem>
/*     */ {
/*  21 */   private static final Pattern PATTERN = Pattern.compile("(?<![\\x5C])\\x24\\x24\\[([a-z0-9\\x20_\\-\\.]*)\\[([^\\]\\[\\x24\\|]+)\\](([iu]?)(:d)?)\\]", 2);
/*     */ 
/*     */ 
/*     */   
/*  25 */   protected int nextListPos = -1;
/*  26 */   protected int nextListEnd = 0;
/*  27 */   protected String nextListName = "";
/*  28 */   protected String[] nextListOptions = new String[0];
/*  29 */   protected MacroParam.Type nextListType = MacroParam.Type.NORMAL;
/*     */   
/*     */   protected boolean nextListIncludeDamage = false;
/*     */   
/*     */   public MacroParamProviderList(Macros macros, bib mc, MacroParam.Type type) {
/*  34 */     super(macros, mc, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/*  40 */     super.reset();
/*  41 */     clearNextList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Matcher find(String script) {
/*  47 */     Matcher matcher = super.find(script);
/*     */     
/*  49 */     if (this.found) {
/*     */       
/*  51 */       this.nextListPos = matcher.start();
/*  52 */       this.nextListEnd = matcher.end();
/*  53 */       this.nextListOptions = ScriptCore.tokenize(matcher.group(2), ',', '"', '"', '\\', null);
/*     */       
/*  55 */       String typeHint = matcher.group(4);
/*     */       
/*  57 */       if ("i".equalsIgnoreCase(typeHint)) {
/*     */         
/*  59 */         this.nextListType = MacroParam.Type.ITEM;
/*  60 */         this.nextListName = "Item";
/*     */         
/*  62 */         String includeDamage = matcher.group(5);
/*  63 */         this.nextListIncludeDamage = ":d".equals(includeDamage);
/*     */       }
/*  65 */       else if ("u".equalsIgnoreCase(typeHint)) {
/*     */         
/*  67 */         this.nextListType = MacroParam.Type.USER;
/*  68 */         this.nextListName = "User";
/*     */       }
/*     */       else {
/*     */         
/*  72 */         this.nextListType = MacroParam.Type.NORMAL;
/*  73 */         this.nextListName = "Choice";
/*     */       } 
/*     */       
/*  76 */       if (matcher.group(1).length() > 0) this.nextListName = matcher.group(1);
/*     */     
/*     */     } 
/*  79 */     return matcher;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearNextList() {
/*  84 */     this.nextListPos = -1;
/*  85 */     this.nextListEnd = 0;
/*  86 */     this.nextListName = "";
/*  87 */     this.nextListOptions = new String[0];
/*  88 */     this.nextListType = MacroParam.Type.NORMAL;
/*  89 */     this.nextListIncludeDamage = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNextListName() {
/*  94 */     return this.nextListName;
/*     */   }
/*     */ 
/*     */   
/*     */   public MacroParam.Type getNextListType() {
/*  99 */     return this.nextListType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getNextListOptions() {
/* 104 */     return this.nextListOptions;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextListPos() {
/* 109 */     return this.nextListPos;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextListEnd() {
/* 114 */     return this.nextListEnd;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getNextListIncludeDamage() {
/* 119 */     return this.nextListIncludeDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pattern getPattern() {
/* 125 */     return PATTERN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroParam<TItem> getMacroParam(IMacroParamTarget target, MacroParams params) {
/* 131 */     return (MacroParam<TItem>)new MacroParamList(this.macros, this.mc, getType(), target, params, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\params\providers\MacroParamProviderList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */