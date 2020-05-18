package me.proggy.proggywebservices;


import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotazione per rendere una risorsa sicura
 * Specifica i ruoli necessari per accedere a una risorsa
 * Può essere messa sia sulla classe che sul singolo metodo,
 * nel caso ci fossero entrambi verrà considerato quello sul metodo
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {
    Role[] value() default {};
}
