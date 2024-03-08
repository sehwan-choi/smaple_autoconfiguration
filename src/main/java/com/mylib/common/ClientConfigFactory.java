package com.mylib.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.annotation.PathVariableParameterProcessor;
import org.springframework.cloud.openfeign.annotation.QueryMapParameterProcessor;
import org.springframework.cloud.openfeign.annotation.RequestHeaderParameterProcessor;
import org.springframework.cloud.openfeign.annotation.RequestParamParameterProcessor;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientConfigFactory {

    public static Encoder encoder() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(Collections.singletonList(jackson2HttpMessageConverter));

        return new SpringEncoder(objectFactory);
    }

    public static Decoder decoder() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(Collections.singletonList(jackson2HttpMessageConverter));

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    /**
     * @throw FeignClientException : feignClient 요청시 응답으로 httpStatus 400번대 에러인 경우 발생하는 예외
     *        FeignServerException : feignClient 요청시 응답으로 httpStatus 500번대 에러인 경우 발생하는 예외
     */
    public static ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default();
    }

    public static Contract contract() {
        List<AnnotatedParameterProcessor> parameterProcessors = Arrays.asList(
                new QueryMapParameterProcessor(),
                new PathVariableParameterProcessor(),
                new RequestParamParameterProcessor(),
                new RequestHeaderParameterProcessor()
        );
        return new SpringMvcContract(parameterProcessors);
    }
}
