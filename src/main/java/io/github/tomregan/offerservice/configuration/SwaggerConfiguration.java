package io.github.tomregan.offerservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@SuppressWarnings("unused")
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @SuppressWarnings("Guava")
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(not(basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build();
    }
}
