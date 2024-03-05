package com.mylib.user;

import com.mylib.common.ClientConfigFactory;
import feign.Logger;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(UserApiConfiguration.class)
public @interface EnableUserApiClients {

    /**
     * API 서버에 요청시 사용할 인증 토큰을 설정하는 인터셉터의 스프링 빈 이름을 설정한다.
     * 만약 빈 값(Empty)이거나 <code>NULL</code>인 경우 기본 인터셉터를 사용 한다.
     *
     * @return 사용할 인터셉터 빈 이름
     * @see com.mylib.common.AuthClientRequestInterceptor 기본으로 사용되는 인터셉터
     */
    String credentialsInterceptor() default "";

    /**
     * 사용할 인코더의 스프링 빈 이름을 설정 한다.
     * 만약 빈 값(Empty)이거나 <code>NULL</code>인 경우 기본 인코더를 사용 한다.
     *
     * @return 사용할 인코더의 빈 이름
     * @see ClientConfigFactory#encoder() () 기본값으로 사용되는 인코더
     */
    String encoder() default "";

    /**
     * 사용할 디코더의 스프링 빈 이름을 설정한다.
     * 만약 빈 값(Empty)이거나 <code>NULL</code>인 경우 기본 디코더를 사용 한다.
     *
     * @return 사용할 디코더의 빈 이름
     * @see ClientConfigFactory#decoder() () 기본값으로 사용되는 디코더
     */
    String decoder() default "";

    /**
     * 사용할 에러 디코더의 스프링 빈 이름을 설정한다.
     * 만약 빈 값(Empty)이거나 <code>NULL</code>인 경우 기본 에러 디코더를 사용 한다.
     *
     * @return 사용할 디코더의 빈 이름
     * @see ClientConfigFactory#errorDecoder() () 기본값으로 사용되는 디코더
     */
    String errorDecoder() default "";

    /**
     * <code>OpenFeign</code>에서 사용할 클라이언트의 스프링 빈 이름을 설정한다.
     * 만약 빈 값(Empty)이거나 <code>NULL</code>인 경우 기본 클라이언트를 사용 한다.
     *
     * @return 사용할 클리이언트 빈 이름
     */
    String client() default "";

    /**
     * 디버깅 레벨을 설정 한다.
     *
     * @return 디버깅 레벨
     */
    Logger.Level level() default Logger.Level.FULL;
}
