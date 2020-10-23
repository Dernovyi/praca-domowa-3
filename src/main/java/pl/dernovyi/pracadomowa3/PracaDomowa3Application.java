package pl.dernovyi.pracadomowa3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class PracaDomowa3Application {

    public static void main(String[] args) {
        SpringApplication.run(PracaDomowa3Application.class, args);
    }

    //http://localhost:8080/v2/api-docs       swagger
    //http://localhost:8080/swagger-ui.html   swagger-ui

    @Bean
    public Docket get(){
       return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.ant("/api/cars/**"))
        .build()
               .apiInfo( new ApiInfoBuilder()
                       .description("Car Application")
                       .license("1.00")
                       .version("0.0.1")
                       .contact(new Contact("Serhii Dernovyi", "https://ladadetal.com.ua", "ladadetal@ukr.net"))
                       .build());
    }


}
