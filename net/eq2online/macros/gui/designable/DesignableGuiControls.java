/*     */ package net.eq2online.macros.gui.designable;
/*     */ 
/*     */ import bib;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import net.eq2online.macros.compatibility.Reflection;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
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
/*     */ public class DesignableGuiControls
/*     */ {
/*  37 */   private static final String PREFIX = DesignableGuiControl.class.getSimpleName();
/*     */ 
/*     */   
/*     */   public static class ControlType
/*     */   {
/*     */     final Class<? extends DesignableGuiControl> controlClass;
/*     */     
/*     */     public final String type;
/*     */     
/*     */     public final int iconU;
/*     */     public final int iconV;
/*     */     public final boolean hasIcon;
/*     */     
/*     */     public ControlType(String type, Class<? extends DesignableGuiControl> controlClass) {
/*  51 */       this.type = type;
/*  52 */       this.controlClass = controlClass;
/*     */       
/*  54 */       DesignableGuiControls.Icon coords = controlClass.<DesignableGuiControls.Icon>getAnnotation(DesignableGuiControls.Icon.class);
/*  55 */       this.iconU = (coords != null) ? coords.u() : -1;
/*  56 */       this.iconV = (coords != null) ? coords.v() : -1;
/*  57 */       this.hasIcon = (this.iconU > -1 && this.iconV > -1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DesignableGuiControl newInstance(Macros macros, bib minecraft, int id) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
/*  64 */       Constructor<? extends DesignableGuiControl> controlConstructor = this.controlClass.getDeclaredConstructor(new Class[] { Macros.class, bib.class, int.class });
/*  65 */       return controlConstructor.newInstance(new Object[] { macros, minecraft, Integer.valueOf(id) });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/*  71 */       return this.type;
/*     */     }
/*     */   }
/*     */   
/*  75 */   private final Map<String, ControlType> controlTypes = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private final DesignableGuiControl[] controls = new DesignableGuiControl[10000];
/*     */   
/*  82 */   private final List<DesignableGuiControl> tickControls = new ArrayList<>();
/*     */   
/*     */   private final Macros macros;
/*     */   
/*     */   private final bib minecraft;
/*     */   
/*     */   private int tickNumber;
/*     */ 
/*     */   
/*     */   public DesignableGuiControls(Macros macros, bib minecraft) {
/*  92 */     this.macros = macros;
/*  93 */     this.minecraft = minecraft;
/*  94 */     initControlRegistry();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initControlRegistry() {
/* 100 */     List<Class<? extends DesignableGuiControl>> controls = Reflection.getSubclassesFor(DesignableGuiControl.class, DesignableGuiControl.class, PREFIX, null);
/*     */     
/* 102 */     for (Class<?> control : controls) {
/*     */       
/* 104 */       if (!Modifier.isAbstract(control.getModifiers())) {
/*     */         
/* 106 */         String controlName = control.getSimpleName().substring(PREFIX.length());
/* 107 */         registerControlClass(controlName, (Class)control);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTick() {
/* 114 */     this.tickNumber++;
/* 115 */     for (DesignableGuiControl control : this.tickControls)
/*     */     {
/* 117 */       control.onTick(this.tickNumber);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiControl createControl(Node xml) {
/* 123 */     int id = -1;
/* 124 */     String strId = Xml.xmlGetAttribute(xml, "id", null);
/* 125 */     if ("*".equals(strId)) {
/*     */       
/* 127 */       id = getFreeControlId();
/*     */     }
/*     */     else {
/*     */       
/* 131 */       id = Xml.xmlGetAttribute(xml, "id", 0);
/*     */     } 
/*     */     
/* 134 */     if (!isValidControlId(id))
/*     */     {
/* 136 */       return null;
/*     */     }
/*     */     
/* 139 */     DesignableGuiControl newControl = createControl(xml.getLocalName(), id);
/*     */     
/* 141 */     if (newControl != null) {
/*     */       
/* 143 */       newControl.setName(Xml.xmlGetAttribute(xml, "name", newControl.getDefaultControlName()));
/* 144 */       newControl.load(xml);
/*     */     } 
/*     */     
/* 147 */     return newControl;
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiControl createControl(String type) {
/* 152 */     if (!this.controlTypes.containsKey(type))
/*     */     {
/* 154 */       return null;
/*     */     }
/*     */     
/* 157 */     int freeControlId = getFreeControlId();
/* 158 */     if (freeControlId > -1)
/*     */     {
/* 160 */       return createControl(type, freeControlId);
/*     */     }
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private DesignableGuiControl createControl(String type, int id) {
/* 168 */     if (!this.controlTypes.containsKey(type))
/*     */     {
/* 170 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 175 */       ControlType controlType = this.controlTypes.get(type);
/* 176 */       DesignableGuiControl newControl = controlType.newInstance(this.macros, this.minecraft, id);
/* 177 */       setControl(id, newControl);
/* 178 */       return newControl;
/*     */     }
/* 180 */     catch (Exception ex) {
/*     */       
/* 182 */       ex.printStackTrace();
/* 183 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setControl(int id, DesignableGuiControl control) {
/* 189 */     if (this.controls[id] != null)
/*     */     {
/* 191 */       this.tickControls.remove(this.controls[id]);
/*     */     }
/*     */     
/* 194 */     this.controls[id] = control;
/* 195 */     if (control != null && !control.onlyTickWhenVisible())
/*     */     {
/* 197 */       this.tickControls.add(control);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidControlId(int id) {
/* 207 */     return MacroTriggerType.CONTROL.supportsId(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeControlId() {
/* 212 */     int id = getFreeControlId(MacroTriggerType.CONTROL.getMinId(), MacroTriggerType.CONTROL.getMaxId());
/* 213 */     if (id == -1)
/*     */     {
/* 215 */       return getFreeControlId(MacroTriggerType.CONTROL.getMinExtId(), MacroTriggerType.CONTROL.getMaxExtId());
/*     */     }
/* 217 */     return id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getFreeControlId(int minId, int maxId) {
/* 222 */     for (int id = minId; id <= maxId; id++) {
/*     */       
/* 224 */       if (this.controls[id] == null)
/*     */       {
/* 226 */         return id;
/*     */       }
/*     */     } 
/*     */     
/* 230 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<DesignableGuiControl> getControls() {
/* 235 */     List<DesignableGuiControl> controls = new ArrayList<>();
/*     */     
/* 237 */     for (int id = MacroTriggerType.CONTROL.getMinId(); id <= MacroTriggerType.CONTROL.getAbsoluteMaxId(); id++) {
/*     */       
/* 239 */       if (this.controls[id] != null)
/*     */       {
/* 241 */         controls.add(this.controls[id]);
/*     */       }
/*     */     } 
/*     */     
/* 245 */     return Collections.unmodifiableCollection(controls);
/*     */   }
/*     */ 
/*     */   
/*     */   public DesignableGuiControl getControl(int id) {
/* 250 */     if (!isValidControlId(id))
/*     */     {
/* 252 */       return null;
/*     */     }
/*     */     
/* 255 */     return this.controls[id];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends DesignableGuiControl> T getControl(String name) {
/* 266 */     for (DesignableGuiControl control : this.controls) {
/*     */       
/* 268 */       if (control != null && control.getName().equals(name)) {
/*     */         
/*     */         try {
/*     */ 
/*     */ 
/*     */           
/* 274 */           return (T)control;
/*     */         
/*     */         }
/* 277 */         catch (ClassCastException ex) {
/*     */           
/* 279 */           return null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 284 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getControlName(int id) {
/* 289 */     if (!isValidControlId(id) || this.controls[id] == null)
/*     */     {
/* 291 */       return "CONTROL " + id;
/*     */     }
/*     */     
/* 294 */     return this.controls[id].getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeControl(int id) {
/* 299 */     if (isValidControlId(id)) {
/*     */       
/* 301 */       if (this.controls[id] != null) {
/*     */         
/* 303 */         this.controls[id].onRemoved();
/* 304 */         this.tickControls.remove(this.controls[id]);
/*     */       } 
/* 306 */       this.controls[id] = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerControlClass(String controlTypeName, Class<? extends DesignableGuiControl> controlClass) {
/* 312 */     this.controlTypes.put(controlTypeName.toLowerCase(), new ControlType(controlTypeName.toLowerCase(), controlClass));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ControlType> getAvailableControlTypes() {
/* 317 */     return Collections.unmodifiableCollection(this.controlTypes.values());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getControlType(DesignableGuiControl control) {
/* 322 */     return control.getClass().getSimpleName().substring(PREFIX.length()).toLowerCase();
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface Icon {
/*     */     int u();
/*     */     
/*     */     int v();
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\gui\designable\DesignableGuiControls.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */