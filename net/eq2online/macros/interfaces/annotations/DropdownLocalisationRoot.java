package net.eq2online.macros.interfaces.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DropdownLocalisationRoot {
  String value();
}


/* Location:              C:\Users\abner\Downloads\mod_macros_0.15.4_for_1.12.1.jar!\net\eq2online\macros\interfaces\annotations\DropdownLocalisationRoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */