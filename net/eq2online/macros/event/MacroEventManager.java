/*     */ package net.eq2online.macros.event;
/*     */ 
/*     */ import bib;
/*     */ import com.mumfrey.liteloader.util.render.Icon;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.eq2online.console.Log;
/*     */ import net.eq2online.macros.compatibility.IconTiled;
/*     */ import net.eq2online.macros.core.MacroTriggerType;
/*     */ import net.eq2online.macros.core.Macros;
/*     */ import net.eq2online.macros.gui.layout.LayoutButton;
/*     */ import net.eq2online.macros.gui.layout.LayoutPanelEvents;
/*     */ import net.eq2online.macros.interfaces.IObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsObserver;
/*     */ import net.eq2online.macros.interfaces.ISettingsStore;
/*     */ import net.eq2online.macros.permissions.MacroModPermissions;
/*     */ import net.eq2online.macros.res.ResourceLocations;
/*     */ import net.eq2online.macros.scripting.api.IMacroEvent;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDefinition;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventManager;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventProvider;
/*     */ import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
/*     */ import net.eq2online.macros.scripting.api.IVariableProvider;
/*     */ import net.eq2online.macros.scripting.parser.ScriptContext;
/*     */ import org.apache.commons.io.IOUtils;
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
/*     */ public class MacroEventManager
/*     */   implements IMacroEventManager, ISettingsObserver
/*     */ {
/*     */   public static final int PRIORITY_SYNCHRONOUS = 100;
/*  56 */   private static Pattern eventPattern = Pattern.compile("^Event\\[([0-9]{1,4})\\]\\.([a-z0-9_\\-\\[\\]]+)=(.+)$", 2);
/*     */   
/*     */   private boolean haveLoadedIDs = false;
/*     */   
/*  60 */   private int nextEventID = MacroTriggerType.EVENT.getMinId();
/*     */   
/*  62 */   private Map<String, Integer> eventIDs = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private Map<String, IMacroEvent> eventsByName = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private Map<Integer, IMacroEvent> eventsByID = new TreeMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Macros macros;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private PriorityQueue<MacroEventQueueEntry> queue = new PriorityQueue<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private List<IMacroEventProvider> providers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   private List<IMacroEventDispatcher> dispatchers = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private Object queueLock = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IMacroEventProvider activeProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MacroEventManager(Macros macros, bib minecraft) {
/* 113 */     this.macros = macros;
/* 114 */     this.macros.getSettingsHandler().registerObserver((IObserver)this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEventProvider(IMacroEventProvider provider) {
/* 120 */     if (!this.providers.contains(provider)) {
/*     */       
/* 122 */       this.providers.add(provider);
/* 123 */       registerEventsFrom(provider);
/*     */       
/* 125 */       IMacroEventDispatcher dispatcher = provider.getDispatcher();
/* 126 */       if (dispatcher != null)
/*     */       {
/* 128 */         registerDispatcher(dispatcher);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerDispatcher(IMacroEventDispatcher dispatcher) {
/* 135 */     if (!this.dispatchers.contains(dispatcher))
/*     */     {
/* 137 */       this.dispatchers.add(dispatcher);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initPermissions() {
/* 146 */     Set<String> eventGroups = new HashSet<>();
/* 147 */     MacroModPermissions.registerPermission("events.*");
/*     */     
/* 149 */     for (String eventName : this.eventsByName.keySet()) {
/*     */       
/* 151 */       IMacroEvent event = getEvent(eventName);
/*     */       
/* 153 */       if (event != null && event.isPermissible()) {
/*     */         
/* 155 */         String eventGroup = event.getPermissionGroup();
/* 156 */         if (!eventGroups.contains(eventGroup)) {
/*     */           
/* 158 */           eventGroups.add(eventGroup);
/* 159 */           MacroModPermissions.registerPermission("events." + eventGroup + ".*");
/*     */         } 
/*     */         
/* 162 */         MacroModPermissions.registerPermission(event.getPermissionName());
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
/*     */ 
/*     */   
/*     */   public boolean checkPermission(int eventId) {
/* 176 */     IMacroEvent event = getEvent(eventId);
/* 177 */     return (event != null && checkPermission(event));
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
/*     */   public boolean checkPermission(String eventName) {
/* 189 */     IMacroEvent event = getEvent(eventName);
/* 190 */     return (event != null && checkPermission(event));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkPermission(IMacroEvent event) {
/* 195 */     return (!event.isPermissible() || MacroModPermissions.hasPermission(event.getPermissionName()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventID(String name) {
/* 201 */     if (!this.eventIDs.containsKey(name)) {
/*     */       
/* 203 */       this.eventIDs.put(name, Integer.valueOf(this.nextEventID));
/* 204 */       this.nextEventID++;
/*     */     } 
/*     */     
/* 207 */     return ((Integer)this.eventIDs.get(name)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEventID(IMacroEvent event) {
/* 213 */     return getEventID(event.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IMacroEvent> getEvents() {
/* 219 */     List<IMacroEvent> eventList = new ArrayList<>();
/*     */     
/* 221 */     eventList.addAll(this.eventsByID.values());
/*     */     
/* 223 */     return eventList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick(bib minecraft) {
/* 233 */     for (IMacroEventDispatcher dispatcher : this.dispatchers) {
/*     */ 
/*     */       
/*     */       try {
/* 237 */         dispatcher.onTick(this, minecraft);
/*     */       }
/* 239 */       catch (IllegalStateException ex) {
/*     */         
/* 241 */         throw ex;
/*     */       }
/* 243 */       catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 248 */       synchronized (this.queueLock)
/*     */       {
/* 250 */         MacroEventQueueEntry nextEvent = this.queue.poll();
/*     */         
/* 252 */         if (nextEvent != null)
/*     */         {
/* 254 */           nextEvent.dispatch(this);
/*     */         }
/*     */       }
/*     */     
/* 258 */     } catch (NullPointerException ex) {
/*     */       
/* 260 */       if (this.queue != null)
/*     */       {
/* 262 */         purgeQueue();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void purgeQueue() {
/* 272 */     synchronized (this.queueLock) {
/*     */       
/* 274 */       this.queue.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEvent(IMacroEvent event, String... eventArgs) {
/* 281 */     sendEvent(event.getName(), 50, eventArgs);
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
/*     */   public void sendEvent(String eventName, int priority, String... eventArgs) {
/* 295 */     boolean synchronous = (priority == Integer.MAX_VALUE);
/*     */     
/* 297 */     if (priority == 100 || synchronous) {
/*     */       
/* 299 */       if (checkPermission(eventName))
/*     */       {
/* 301 */         dispatchEvent(eventName, synchronous, eventArgs);
/*     */       }
/*     */     }
/* 304 */     else if (priority < 0 || priority > 100) {
/*     */       
/* 306 */       Log.info("Event {0} was not dispatched because an invalid priority was specified: " + priority);
/*     */     }
/* 308 */     else if (this.eventsByName.containsKey(eventName)) {
/*     */       
/* 310 */       if (checkPermission(eventName)) {
/*     */         
/* 312 */         synchronized (this.queueLock)
/*     */         {
/* 314 */           this.queue.add(new MacroEventQueueEntry(eventName, priority, eventArgs));
/*     */         }
/*     */       
/* 317 */       } else if ((this.macros.getSettings()).enableDebug) {
/*     */         
/* 319 */         Log.info("Event {0} was denied by the server", new Object[] { eventName });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 324 */       Log.info("Event {0} was not dispatched because no mapping was found");
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
/*     */   void dispatchEvent(String eventName, boolean synchronous, String... eventArgs) {
/* 336 */     if (this.eventsByName.containsKey(eventName)) {
/*     */       
/* 338 */       IMacroEvent event = this.eventsByName.get(eventName);
/* 339 */       IMacroEventVariableProvider eventVariableProvider = event.getVariableProvider(eventArgs);
/*     */       
/* 341 */       if ((this.macros.getSettings()).enableDebug)
/*     */       {
/* 343 */         Log.info("Dispatching event {0}", new Object[] { eventName });
/*     */       }
/*     */       
/* 346 */       event.onDispatch();
/* 347 */       this.macros.playMacro(getEventID(event.getName()), false, ScriptContext.MAIN, (IVariableProvider)eventVariableProvider, synchronous);
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
/*     */   public IMacroEvent getEvent(int eventId) {
/* 361 */     return this.eventsByID.get(Integer.valueOf(eventId));
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
/*     */   public IMacroEvent getEvent(String eventName) {
/* 373 */     return this.eventsByName.get(eventName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshGui() {
/* 381 */     LayoutPanelEvents panel = this.macros.getLayoutPanels().getEventLayout();
/* 382 */     if (panel != null)
/*     */     {
/* 384 */       panel.loadPanelLayout("");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadEvents() {
/* 391 */     clearEventMappings();
/* 392 */     reloadProviders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void clearEvents() {
/* 400 */     this.eventIDs.clear();
/* 401 */     this.nextEventID = MacroTriggerType.EVENT.getMinId();
/* 402 */     clearEventMappings();
/*     */   }
/*     */ 
/*     */   
/*     */   private void clearEventMappings() {
/* 407 */     this.eventsByID.clear();
/* 408 */     this.eventsByName.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void reloadProviders() {
/* 413 */     for (IMacroEventProvider provider : this.providers)
/*     */     {
/* 415 */       registerEventsFrom(provider);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerEventsFrom(IMacroEventProvider provider) {
/*     */     try {
/* 423 */       this.activeProvider = provider;
/* 424 */       provider.registerEvents(this);
/*     */     }
/*     */     finally {
/*     */       
/* 428 */       this.activeProvider = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMacroEvent registerEvent(IMacroEventProvider provider, IMacroEventDefinition info) {
/* 435 */     if (this.activeProvider != provider)
/*     */     {
/* 437 */       throw new IllegalStateException("Attempted to register an event externally, register events via registerEvents() in your provider");
/*     */     }
/*     */     
/* 440 */     if (!this.providers.contains(provider) || provider == null)
/*     */     {
/* 442 */       throw new IllegalArgumentException("Attempted to register an event with an invalid provider, call registerProvider() first!");
/*     */     }
/*     */     
/* 445 */     int eventId = getEventID(info.getName());
/*     */     
/* 447 */     if (this.eventsByName.containsKey(info.getName())) {
/*     */       
/* 449 */       IMacroEvent existingEvent = this.eventsByName.get(info.getName());
/*     */       
/* 451 */       this.eventsByID.put(Integer.valueOf(eventId), existingEvent);
/* 452 */       existingEvent.setIcon(getEventIcon(eventId));
/*     */       
/* 454 */       refreshGui();
/* 455 */       return existingEvent;
/*     */     } 
/*     */ 
/*     */     
/* 459 */     IMacroEvent newEvent = new MacroEvent(provider, info.getName(), (info.getPermissionGroup() != null), info.getPermissionGroup(), getEventIcon(eventId));
/* 460 */     this.eventsByName.put(info.getName(), newEvent);
/* 461 */     this.eventsByID.put(Integer.valueOf(eventId), newEvent);
/*     */     
/* 463 */     refreshGui();
/* 464 */     return newEvent;
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
/*     */   public IMacroEvent registerEvent(IMacroEvent event) {
/* 477 */     if (!this.providers.contains(event.getProvider()) || event.getProvider() == null)
/*     */     {
/* 479 */       throw new IllegalArgumentException("Attempted to register an event with an invalid provider, call registerProvider() first!");
/*     */     }
/*     */     
/* 482 */     String name = event.getName();
/* 483 */     int eventId = getEventID(name);
/*     */     
/* 485 */     if (this.eventsByName.containsKey(name)) {
/*     */       
/* 487 */       if (this.eventsByName.get(name) == event) {
/*     */         
/* 489 */         this.eventsByID.put(Integer.valueOf(eventId), event);
/* 490 */         event.setIcon(getEventIcon(eventId));
/*     */         
/* 492 */         refreshGui();
/* 493 */         return event;
/*     */       } 
/*     */       
/* 496 */       throw new IllegalArgumentException("An instance of the specified event " + name + " is already registered");
/*     */     } 
/*     */     
/* 499 */     this.eventsByName.put(name, event);
/* 500 */     this.eventsByID.put(Integer.valueOf(eventId), event);
/*     */     
/* 502 */     refreshGui();
/* 503 */     return event;
/*     */   }
/*     */ 
/*     */   
/*     */   private Icon getEventIcon(int eventId) {
/* 508 */     eventId = MacroTriggerType.EVENT.getRelativeId(eventId);
/* 509 */     int iconU = eventId % 10 * 24;
/* 510 */     int iconV = eventId / 10 * 24;
/* 511 */     return (Icon)new IconTiled(ResourceLocations.EXT, eventId, iconU, iconV, 24, 24, 256, 256);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSettings() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLoadSettings(ISettingsStore settings) {
/* 522 */     if (!this.haveLoadedIDs) {
/*     */       
/* 524 */       BufferedReader bufferedreader = null;
/*     */ 
/*     */       
/*     */       try {
/* 528 */         File eventsFile = this.macros.getFile(".events.txt");
/* 529 */         if (!eventsFile.exists())
/* 530 */           return;  clearEvents();
/*     */         
/* 532 */         bufferedreader = new BufferedReader(new FileReader(eventsFile));
/*     */         
/* 534 */         for (String configLine = ""; (configLine = bufferedreader.readLine()) != null;) {
/*     */ 
/*     */           
/*     */           try {
/* 538 */             Matcher eventConfigLineMatcher = eventPattern.matcher(configLine);
/*     */             
/* 540 */             if (eventConfigLineMatcher.matches())
/*     */             {
/* 542 */               int eventId = Integer.parseInt(eventConfigLineMatcher.group(1));
/*     */               
/* 544 */               if (MacroTriggerType.EVENT.supportsId(eventId)) {
/*     */                 
/* 546 */                 if (eventConfigLineMatcher.group(2).equalsIgnoreCase("name")) {
/*     */                   
/* 548 */                   String eventName = eventConfigLineMatcher.group(3);
/* 549 */                   this.eventIDs.put(eventName, Integer.valueOf(eventId));
/*     */                 } 
/*     */                 
/*     */                 continue;
/*     */               } 
/* 554 */               Log.info("Skipping bad event ID: " + eventId);
/*     */             }
/*     */           
/*     */           }
/* 558 */           catch (Exception ex) {
/*     */             
/* 560 */             Log.info("Skipping bad event mapping: " + configLine);
/*     */           } 
/*     */         } 
/*     */         
/* 564 */         this.haveLoadedIDs = true;
/* 565 */         recalcNextEventId();
/*     */       }
/* 567 */       catch (Exception ex) {
/*     */         
/* 569 */         Log.printStackTrace(ex);
/*     */       }
/*     */       finally {
/*     */         
/* 573 */         IOUtils.closeQuietly(bufferedreader);
/*     */       } 
/*     */       
/* 576 */       reloadEvents();
/*     */     } 
/*     */     
/* 579 */     LayoutButton.notifySettingsLoaded(settings);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void recalcNextEventId() {
/* 585 */     boolean resolved = true;
/*     */     
/*     */     do {
/* 588 */       resolved = true;
/* 589 */       for (Integer val : this.eventIDs.values())
/*     */       {
/* 591 */         if (this.nextEventID == val.intValue())
/*     */         {
/* 593 */           this.nextEventID++;
/* 594 */           resolved = false;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 599 */     } while (!resolved);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveSettings(ISettingsStore settings) {
/*     */     try {
/* 609 */       File eventsFile = this.macros.getFile(".events.txt");
/*     */       
/* 611 */       PrintWriter printwriter = new PrintWriter(new FileWriter(eventsFile));
/*     */       
/* 613 */       printwriter.println("#");
/* 614 */       printwriter.println("# events.txt");
/* 615 */       printwriter.println("# This file stores mapping of hookable event names event ID's to event names, DO NOT modify the");
/* 616 */       printwriter.println("# contents of this file unless you know what you are doing, in fact, not even then since all your");
/* 617 */       printwriter.println("# event bindings will be messed up if you edit this file, seriously, just don't. Event names are");
/* 618 */       printwriter.println("# CASE SENSITIVE and event ID's must fall within the defined event ID range.");
/* 619 */       printwriter.println("#\n");
/*     */       
/* 621 */       for (Map.Entry<String, Integer> event : this.eventIDs.entrySet()) {
/*     */         
/* 623 */         printwriter.println(String.format("Event[%s].Name=%s", new Object[] { ((Integer)event.getValue()).toString(), event.getKey() }));
/*     */       } 
/*     */       
/* 626 */       printwriter.close();
/*     */     }
/* 628 */     catch (Exception ex) {
/*     */       
/* 630 */       Log.printStackTrace(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\event\MacroEventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */