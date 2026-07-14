package com.baidu.baselibrary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageConfig {

    /**
     * @return 是否需要设置 paddingTop = StatusBar.height
     */
    boolean needPaddingTop() default true;

    /**
     * @return 是否需要设置 paddingBottom = NavigationBar.height
     */
    boolean needPaddingBottom() default true;

    boolean transparencyBar() default true;

    boolean isFullScreen() default false;
}
