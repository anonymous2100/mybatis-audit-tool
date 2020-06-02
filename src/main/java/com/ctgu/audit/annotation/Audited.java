package com.ctgu.audit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Audited
 * @Description: 注解用在类上面，表示某张表是否需要被审计；只有添加此注解的实体类增删改操作才会被拦截。
 * @author lh2
 * @date 2020年6月2日 下午3:04:00
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Audited
{
	String auditTable() default "";
}
