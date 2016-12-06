package framework.hibernate.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToMany {

    FetchType fetch() default  FetchType.LAZY;
    String mappedTable();
    String columnFrom() default "id";
    String columnTo() default "id";

}
