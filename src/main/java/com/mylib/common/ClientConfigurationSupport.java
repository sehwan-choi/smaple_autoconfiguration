package com.mylib.common;

import feign.Client;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

public abstract class ClientConfigurationSupport implements ImportAware, ApplicationContextAware {

    protected final String annotationName;

    protected ApplicationContext context;

    protected AnnotationInfo annotationInfo;

    public ClientConfigurationSupport(String annotationName) {
        this.annotationName = annotationName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map<String, Object> metadata = importMetadata.getAnnotationAttributes(annotationName);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata);

        if (attributes != null) {
            this.annotationInfo = new AnnotationInfo(attributes.getString("encoder"),
                                                    attributes.getString("decoder"),
                                                    attributes.getString("errorDecoder"),
                                                    attributes.getString("client"),
                                                    attributes.getString("credentialsInterceptor"));

            Object level = attributes.get("level");
            if (level != null) {
                this.annotationInfo = new AnnotationInfo(this.annotationInfo, ((Logger.Level) level));
            }
        }
    }

    protected Decoder getDecoder() {
        if (StringUtils.hasText(this.annotationInfo.getDecoderName())) {
            return this.context.getBean(this.annotationInfo.getDecoderName(), Decoder.class);
        } else {
            return ClientConfigFactory.decoder();
        }
    }

    protected Encoder getEncoder() {
        if (StringUtils.hasText(this.annotationInfo.getEncoderName())) {
            return this.context.getBean(this.annotationInfo.getEncoderName(), Encoder.class);
        } else {
            return ClientConfigFactory.encoder();
        }
    }

    protected ErrorDecoder getErrorDecoder() {
        if (StringUtils.hasText(this.annotationInfo.getErrorDecoderName())) {
            return this.context.getBean(this.annotationInfo.getErrorDecoderName(), ErrorDecoder.class);
        } else {
            return ClientConfigFactory.errorDecoder();
        }
    }


    protected Client getClient() {
        if (StringUtils.hasText(this.annotationInfo.getClientName())) {
            return this.context.getBean(this.annotationInfo.getClientName(), Client.class);
        } else {
            return new Client.Default(null, null);
        }
    }


    public static class AnnotationInfo {
        private final String encoderName;

        private final String decoderName;

        private final String errorDecoderName;

        private final String clientName;

        private final Logger.Level level;

        private final String credentialsInterceptorName;

        public AnnotationInfo(String encoderName, String decoderName, String errorDecoderName, String clientName, Logger.Level level, String credentialsInterceptorName) {
            this.encoderName = encoderName;
            this.decoderName = decoderName;
            this.errorDecoderName = errorDecoderName;
            this.clientName = clientName;
            this.level = level;
            this.credentialsInterceptorName = credentialsInterceptorName;
        }

        public AnnotationInfo(String encoderName, String decoderName, String errorDecoderName, String clientName, String credentialsInterceptorName) {
            this(encoderName, decoderName, errorDecoderName, clientName, null, credentialsInterceptorName);
        }

        public AnnotationInfo(AnnotationInfo annotationInfo, Logger.Level level) {
            this(annotationInfo.getEncoderName(), annotationInfo.getDecoderName(), annotationInfo.getErrorDecoderName(), annotationInfo.getClientName(), level, annotationInfo.getCredentialsInterceptorName());
        }

        public String getEncoderName() {
            return encoderName;
        }

        public String getDecoderName() {
            return decoderName;
        }

        public String getErrorDecoderName() {
            return errorDecoderName;
        }

        public String getClientName() {
            return clientName;
        }

        public Logger.Level getLevel() {
            return level;
        }

        public String getCredentialsInterceptorName() {
            return credentialsInterceptorName;
        }
    }
}
