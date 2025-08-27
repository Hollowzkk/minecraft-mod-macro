package net.eq2online.macros.core.overlays;

import akq;
import akt;
import fi;

public interface IVanillaRecipe extends akt {
  int getWidth();
  
  int getHeight();
  
  fi<akq> getItems();
  
  boolean requiresCraftingTable();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\core\overlays\IVanillaRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */