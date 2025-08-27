/*     */ package net.eq2online.macros.scripting;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.scripting.api.IExpressionEvaluator;
/*     */ import net.eq2online.macros.scripting.api.IMacro;
/*     */ import net.eq2online.macros.scripting.api.IScriptActionProvider;
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
/*     */ public final class ExpressionEvaluator
/*     */   implements IExpressionEvaluator
/*     */ {
/*     */   public static boolean TRACE = false;
/*  26 */   private static int MAX_DEPTH = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final Pattern PATTERN_OPERATOR = Pattern.compile("\\={1,2}|\\<\\=|\\>\\=|\\>|\\<|\\!\\=|\\&{2}|\\|{1,2}|\\+|\\-|\\*|\\/", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final Pattern PATTERN_STRING = Pattern.compile("\\x22([^\\x22]*)\\x22");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Pattern PATTERN_NEGATIVE_NUMBER = Pattern.compile("(?<=(^|\\())-(?=[0-9])");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private final Map<String, Integer> variables = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private final Map<String, Integer> stringLiterals = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final Map<Integer, String> stringLiteralValues = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final String originalExpression;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IScriptActionProvider provider;
/*     */ 
/*     */   
/*     */   private final IMacro macro;
/*     */ 
/*     */   
/*  72 */   private int nextStringLiteral = 2147483646;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int result;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExpressionEvaluator(String expression, IScriptActionProvider provider, IMacro macro) {
/*  86 */     this.originalExpression = expression;
/*  87 */     this.provider = provider;
/*  88 */     this.macro = macro;
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
/*     */   public void setVariable(String variableName, boolean variableValue) {
/* 100 */     this.variables.put(variableName, Integer.valueOf(variableValue ? 1 : 0));
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
/*     */   public void setVariable(String variableName, int variableValue) {
/* 113 */     this.variables.put(variableName, Integer.valueOf(variableValue));
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
/*     */   public void setVariable(String variableName, String variableValue) {
/* 125 */     this.variables.put(variableName, Integer.valueOf(addStringLiteral(variableValue)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVariables(Map<String, Object> variables) {
/* 135 */     for (Map.Entry<String, Object> variable : variables.entrySet()) {
/*     */       
/* 137 */       if (variable.getValue() instanceof Boolean) {
/*     */         
/* 139 */         setVariable(variable.getKey(), ((Boolean)variable.getValue()).booleanValue()); continue;
/*     */       } 
/* 141 */       if (variable.getValue() instanceof String) {
/*     */         
/* 143 */         setVariable(variable.getKey(), (String)variable.getValue()); continue;
/*     */       } 
/* 145 */       if (variable.getValue() instanceof Integer)
/*     */       {
/* 147 */         setVariable(variable.getKey(), ((Integer)variable.getValue()).intValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpVariables() {
/* 159 */     for (Map.Entry<String, Integer> variable : this.variables.entrySet()) {
/*     */       
/* 161 */       if (this.stringLiteralValues.containsKey(variable.getValue())) {
/*     */         
/* 163 */         Log.info("dumpVariables() {0}={1}", new Object[] { variable.getKey(), this.stringLiteralValues.get(variable.getValue()) });
/*     */         
/*     */         continue;
/*     */       } 
/* 167 */       Log.info("dumpVariables() {0}={1}", new Object[] { variable.getKey(), variable.getValue() });
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
/*     */   public int addStringLiteral(String literalString) {
/* 179 */     if (literalString.length() == 0) return 0;
/*     */     
/* 181 */     String cleanedLiteralString = literalString.replaceAll("\\x20\\|\\&\\!\\>\\<\\=", "");
/*     */     
/* 183 */     if (this.stringLiterals.containsKey(cleanedLiteralString))
/*     */     {
/* 185 */       return ((Integer)this.stringLiterals.get(cleanedLiteralString)).intValue();
/*     */     }
/*     */     
/* 188 */     int literalStringIndex = this.nextStringLiteral--;
/* 189 */     this.stringLiterals.put(cleanedLiteralString, Integer.valueOf(literalStringIndex));
/* 190 */     this.stringLiteralValues.put(Integer.valueOf(literalStringIndex), literalString);
/* 191 */     return literalStringIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int evaluate() {
/*     */     try {
/* 202 */       this.result = evaluate(prepare(), 0);
/*     */     }
/* 204 */     catch (Exception ex) {
/*     */       
/* 206 */       this.result = 0;
/*     */     } 
/*     */     
/* 209 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getResult() {
/* 218 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String prepare() {
/* 229 */     String expression = this.originalExpression;
/*     */     
/* 231 */     Matcher stringLiteralPatternMatcher = PATTERN_STRING.matcher(expression);
/*     */     
/* 233 */     while (stringLiteralPatternMatcher.find()) {
/*     */       
/* 235 */       int stringLiteralIndex = addStringLiteral(stringLiteralPatternMatcher.group(1));
/*     */       
/* 237 */       expression = expression.substring(0, stringLiteralPatternMatcher.start()) + stringLiteralIndex + expression.substring(stringLiteralPatternMatcher.end());
/* 238 */       stringLiteralPatternMatcher.reset(expression);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     expression = expression.replaceAll("(?<!\\&)\\& ", "\\&\\&").replaceAll("\\s", "").replaceAll("[Tt][Rr][Uu][Ee]", "1").replaceAll("[Ff][Aa][Ll][Ss][Ee]", "0");
/*     */     
/* 247 */     if (TRACE)
/*     */     {
/* 249 */       Log.info("[LOG]   Prepared [{0}]", new Object[] { expression });
/*     */     }
/*     */     
/* 252 */     return expression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int evaluate(String expression, int depth) {
/* 263 */     if (expression == null || expression.length() < 1 || depth >= MAX_DEPTH) return 0;
/*     */     
/* 265 */     if (TRACE) Log.info("[LOG]    Evaluating [{0}]", new Object[] { expression });
/*     */     
/* 267 */     Matcher negativeNumberPatternMatcher = PATTERN_NEGATIVE_NUMBER.matcher(expression);
/* 268 */     expression = negativeNumberPatternMatcher.replaceAll("¬");
/*     */     
/* 270 */     if (TRACE) Log.info("[LOG]     Evaluating [{0}]", new Object[] { expression });
/*     */ 
/*     */     
/* 273 */     if (containsParentheses(expression)) {
/*     */       
/* 275 */       int startPos = expression.indexOf('(');
/* 276 */       int endPos = startPos + 1;
/* 277 */       int stackCount = 0;
/*     */       
/* 279 */       for (; endPos < expression.length(); endPos++) {
/*     */         
/* 281 */         if (expression.charAt(endPos) == '(') stackCount++; 
/* 282 */         if (expression.charAt(endPos) == ')') {
/*     */           
/* 284 */           stackCount--;
/* 285 */           if (stackCount < 0)
/*     */             break; 
/*     */         } 
/*     */       } 
/* 289 */       String subExpression = expression.substring(startPos + 1, endPos);
/* 290 */       int result = evaluate(subExpression, depth + 1);
/*     */       
/* 292 */       expression = ((startPos > 0) ? expression.substring(0, startPos) : "") + result + ((endPos < expression.length()) ? expression.substring(endPos + 1) : "");
/* 293 */       return evaluate(expression, depth);
/*     */     } 
/* 295 */     if (containsOperator(expression)) {
/*     */       
/* 297 */       Matcher operatorMatcher = PATTERN_OPERATOR.matcher(expression);
/* 298 */       operatorMatcher.find();
/* 299 */       String sLHS = expression.substring(0, operatorMatcher.start());
/* 300 */       String sRHS = expression.substring(operatorMatcher.end());
/* 301 */       int expressionResult = evaluate(sLHS, sRHS, operatorMatcher.group(), depth);
/* 302 */       if (TRACE) Log.info("[LOG]       Calculated [{0}]", new Object[] { Integer.valueOf(expressionResult) }); 
/* 303 */       return expressionResult;
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return evaluateSingle(expression, depth + 1);
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
/*     */   protected static boolean containsOperator(String expression) {
/* 319 */     return PATTERN_OPERATOR.matcher(expression).find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean containsParentheses(String expression) {
/* 330 */     return (expression.indexOf('(') > -1 && expression.indexOf(')') > -1);
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
/*     */   protected int evaluate(String sLHS, String sRHS, String operator, int depth) {
/* 343 */     int lhs = getValue(sLHS, depth);
/* 344 */     int rhs = getValue(sRHS, depth);
/*     */     
/* 346 */     if (TRACE)
/*     */     {
/* 348 */       Log.info("[LOG]      Calculating [{0}] with {1} {2} at depth {3} values [{4}] [{5}]", new Object[] { operator, sLHS, sRHS, Integer.valueOf(depth), Integer.valueOf(lhs), Integer.valueOf(rhs) });
/*     */     }
/*     */     
/* 351 */     if ("+".equals(operator)) return lhs + rhs; 
/* 352 */     if ("-".equals(operator)) return lhs - rhs; 
/* 353 */     if ("*".equals(operator)) return lhs * rhs; 
/* 354 */     if ("/".equals(operator)) return lhs / rhs;
/*     */ 
/*     */     
/* 357 */     return evaluateBoolean(lhs, rhs, operator, depth) ? 1 : 0;
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
/*     */   protected boolean evaluateBoolean(int lhs, int rhs, String operator, int depth) {
/* 371 */     if ("=".equals(operator)) return (lhs == rhs); 
/* 372 */     if ("==".equals(operator)) return (lhs == rhs); 
/* 373 */     if ("!=".equals(operator)) return (lhs != rhs); 
/* 374 */     if ("<=".equals(operator)) return (lhs <= rhs); 
/* 375 */     if (">=".equals(operator)) return (lhs >= rhs); 
/* 376 */     if ("<".equals(operator)) return (lhs < rhs); 
/* 377 */     if (">".equals(operator)) return (lhs > rhs); 
/* 378 */     if ("&".equals(operator)) return (lhs > 0 && lhs < this.nextStringLiteral && rhs > 0 && rhs < this.nextStringLiteral); 
/* 379 */     if ("&&".equals(operator)) return (lhs > 0 && lhs < this.nextStringLiteral && rhs > 0 && rhs < this.nextStringLiteral); 
/* 380 */     if ("|".equals(operator)) return ((lhs > 0 && lhs < this.nextStringLiteral) || (rhs > 0 && rhs < this.nextStringLiteral)); 
/* 381 */     if ("||".equals(operator)) return ((lhs > 0 && lhs < this.nextStringLiteral) || (rhs > 0 && rhs < this.nextStringLiteral));
/*     */     
/* 383 */     return false;
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
/*     */   protected int evaluateSingle(String single, int depth) {
/* 395 */     int value = getValue(single, depth);
/* 396 */     if (TRACE) Log.info("[LOG]       Single [{0}]", new Object[] { Integer.valueOf(value) }); 
/* 397 */     return (value < this.nextStringLiteral) ? value : 1;
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
/*     */   protected int getValue(String expression, int depth) {
/* 409 */     boolean not = false;
/* 410 */     int intValue = 0;
/*     */ 
/*     */     
/* 413 */     if (expression.startsWith("!") && expression.length() > 1) {
/*     */       
/* 415 */       not = true;
/* 416 */       expression = expression.substring(1);
/*     */     } 
/*     */     
/* 419 */     if (Variable.isValidVariableName(expression)) {
/*     */       
/* 421 */       Object variableValue = this.provider.getVariable(expression, this.macro);
/*     */       
/* 423 */       if (variableValue != null)
/*     */       {
/* 425 */         if (variableValue instanceof String) {
/*     */           
/* 427 */           setVariable(expression, (String)variableValue);
/*     */         }
/* 429 */         else if (variableValue instanceof Integer) {
/*     */           
/* 431 */           setVariable(expression, ((Integer)variableValue).intValue());
/*     */         }
/* 433 */         else if (variableValue instanceof Boolean) {
/*     */           
/* 435 */           setVariable(expression, ((Boolean)variableValue).booleanValue());
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 440 */     if (this.variables.containsKey(expression)) {
/*     */       
/* 442 */       intValue = ((Integer)this.variables.get(expression)).intValue();
/*     */     }
/* 444 */     else if (containsOperator(expression) || containsParentheses(expression)) {
/*     */       
/* 446 */       intValue = evaluate(expression, depth + 1);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 452 */         intValue = Integer.parseInt(expression.replace('¬', '-'));
/*     */       }
/* 454 */       catch (NumberFormatException ex) {
/*     */         
/* 456 */         intValue = 0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 461 */     if (not)
/*     */     {
/* 463 */       intValue = (intValue < 1) ? 1 : 0;
/*     */     }
/*     */     
/* 466 */     return intValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(int value) {
/* 471 */     return (value != 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\scripting\ExpressionEvaluator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */