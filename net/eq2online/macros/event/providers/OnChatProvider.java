/*     */ package net.eq2online.macros.event.providers;
/*     */ 
/*     */ import aed;
/*     */ import bib;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
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
/*     */ public class OnChatProvider
/*     */   implements IMacroEventVariableProvider
/*     */ {
/*     */   private static final long GUESS_LIFESPAN = 500L;
/*  29 */   private static final Pattern PATTERN_VANILLAQUOTE = Pattern.compile("^\\<([a-z0-9_]{2,16})\\>", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final Pattern PATTERN_LIKELYQUOTE = Pattern.compile("^([a-z0-9_]{2,16}):|^\\<(.+?)\\>", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static final Pattern PATTERN_BESTGUESSQUOTE = Pattern.compile("\\<(.+?)\\>|\\[(.+?)\\]|\\((.+?)\\)", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private static final Pattern PATTERN_ACTUALNAMEQUOTE = Pattern.compile("([a-z0-9_]{2,16})", 2);
/*     */ 
/*     */ 
/*     */   
/*     */   private String lastChatPlayerName;
/*     */ 
/*     */ 
/*     */   
/*     */   private aed lastChatPlayer;
/*     */ 
/*     */ 
/*     */   
/*     */   private long lastChatPlayerTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean followOnLikely = false;
/*     */ 
/*     */   
/*     */   String chat;
/*     */ 
/*     */   
/*     */   String chatClean;
/*     */ 
/*     */   
/*     */   String chatMessage;
/*     */ 
/*     */ 
/*     */   
/*     */   public OnChatProvider(IMacroEvent event) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateVariables(boolean clock) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getVariable(String variableName) {
/*  83 */     if ("CHAT".equals(variableName)) return this.chat; 
/*  84 */     if ("CHATCLEAN".equals(variableName)) return (this.chatClean != null) ? this.chatClean : ""; 
/*  85 */     if ("CHATPLAYER".equals(variableName)) return (this.lastChatPlayerName != null) ? this.lastChatPlayerName : ""; 
/*  86 */     if ("CHATMESSAGE".equals(variableName)) return (this.chatMessage != null) ? this.chatMessage : "";
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getVariables() {
/*  94 */     Set<String> variables = new HashSet<>();
/*  95 */     variables.add("CHAT");
/*  96 */     variables.add("CHATCLEAN");
/*  97 */     variables.add("CHATPLAYER");
/*  98 */     variables.add("CHATMESSAGE");
/*  99 */     return variables;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onInit() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void initInstance(String[] instanceVariables) {
/* 110 */     this.chat = instanceVariables[0];
/* 111 */     this.chatClean = instanceVariables[1];
/*     */ 
/*     */     
/*     */     try {
/* 115 */       this.chatMessage = guessPlayer(this.chatClean);
/*     */     }
/* 117 */     catch (Exception ex) {
/*     */       
/* 119 */       this.chatMessage = this.chatClean;
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
/*     */   protected String guessPlayer(String message) {
/* 133 */     String guess = null;
/*     */ 
/*     */     
/* 136 */     if (this.followOnLikely && this.lastChatPlayerName != null && System.currentTimeMillis() < this.lastChatPlayerTime + 500L) {
/*     */       
/* 138 */       guess = this.lastChatPlayerName;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 143 */       this.lastChatPlayerName = null;
/* 144 */       this.lastChatPlayer = null;
/* 145 */       this.lastChatPlayerTime = 0L;
/*     */     } 
/*     */ 
/*     */     
/* 149 */     this.followOnLikely = (message.length() >= 256);
/*     */ 
/*     */     
/* 152 */     if (this.chatClean.startsWith("<") && this.chatClean.indexOf('>') > -1) {
/*     */       
/* 154 */       Matcher vanillaMatcher = PATTERN_VANILLAQUOTE.matcher(this.chatClean);
/*     */       
/* 156 */       if (vanillaMatcher.find()) {
/*     */         
/* 158 */         this.lastChatPlayerName = vanillaMatcher.group(1);
/* 159 */         this.lastChatPlayer = findGuessedPlayer(this.lastChatPlayerName);
/* 160 */         this.lastChatPlayerTime = System.currentTimeMillis();
/* 161 */         return vanillaMatcher.replaceFirst("").trim();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 166 */     String bestGuess = null;
/*     */     
/* 168 */     Matcher likelyQuotePatternMatcher = PATTERN_LIKELYQUOTE.matcher(this.chatClean);
/* 169 */     Matcher bestGuessMatcher = PATTERN_BESTGUESSQUOTE.matcher(this.chatClean);
/*     */ 
/*     */     
/* 172 */     Matcher successfulMatcher = null;
/*     */ 
/*     */     
/* 175 */     if (likelyQuotePatternMatcher.find()) {
/*     */       
/* 177 */       successfulMatcher = likelyQuotePatternMatcher;
/*     */       
/* 179 */       bestGuess = (likelyQuotePatternMatcher.group(1) != null) ? likelyQuotePatternMatcher.group(1) : likelyQuotePatternMatcher.group(2);
/*     */     }
/* 181 */     else if (bestGuessMatcher.find()) {
/*     */       
/* 183 */       successfulMatcher = bestGuessMatcher;
/*     */ 
/*     */       
/* 186 */       if (bestGuessMatcher.group(1) != null) bestGuess = bestGuessMatcher.group(1); 
/* 187 */       if (bestGuessMatcher.group(2) != null) bestGuess = bestGuessMatcher.group(2); 
/* 188 */       if (bestGuessMatcher.group(3) != null) bestGuess = bestGuessMatcher.group(3);
/*     */     
/*     */     } 
/*     */     
/* 192 */     if (bestGuess != null && successfulMatcher != null) {
/*     */       
/* 194 */       Matcher matchingPlayerName = PATTERN_ACTUALNAMEQUOTE.matcher(bestGuess);
/*     */       
/* 196 */       if (matchingPlayerName.find()) {
/*     */         
/* 198 */         bestGuess = matchingPlayerName.group(1);
/* 199 */         this.lastChatPlayerTime = System.currentTimeMillis();
/*     */         
/* 201 */         message = successfulMatcher.replaceFirst("").trim();
/*     */       }
/* 203 */       else if (guess != null) {
/*     */         
/* 205 */         bestGuess = guess;
/*     */       } 
/*     */       
/* 208 */       this.lastChatPlayerName = bestGuess;
/* 209 */       this.lastChatPlayer = findGuessedPlayer(bestGuess);
/* 210 */       return message;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     if (guess != null) {
/*     */       
/* 216 */       this.lastChatPlayer = findGuessedPlayer(guess);
/*     */       
/* 218 */       if (this.lastChatPlayer != null && this.followOnLikely)
/*     */       {
/* 220 */         this.lastChatPlayerTime = System.currentTimeMillis();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 225 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected aed findGuessedPlayer(String guess) {
/* 236 */     bib minecraft = bib.z();
/*     */     
/* 238 */     if (minecraft.f != null && minecraft.f.i != null)
/*     */     {
/* 240 */       for (Object entity : minecraft.f.i) {
/*     */         
/* 242 */         aed playerEntity = (aed)entity;
/*     */         
/* 244 */         if (playerEntity.h_().equalsIgnoreCase(guess)) {
/*     */           
/* 246 */           this.lastChatPlayerName = playerEntity.h_();
/* 247 */           return playerEntity;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 252 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\providers\OnChatProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */