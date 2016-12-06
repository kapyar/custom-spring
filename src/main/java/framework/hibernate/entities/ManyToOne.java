package framework.hibernate.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ManyToOne {

    FetchType fetch() default  FetchType.LAZY;
    String mappedTable();
    String mappedColumn() default "id";
}
