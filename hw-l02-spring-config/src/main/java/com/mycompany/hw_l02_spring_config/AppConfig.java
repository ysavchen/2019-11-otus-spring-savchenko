package com.mycompany.hw_l02_spring_config;

import com.mycompany.hw_l02_spring_config.app.TestingApp;
import lombok.val;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@PropertySource("classpath:application.properties")
@ComponentScan
@Configuration
public class AppConfig {

    public static void main(String[] args) {
        val context = new AnnotationConfigApplicationContext(AppConfig.class);
        TestingApp app = context.getBean(TestingApp.class);
        app.go();
    }

    @Bean
    public MessageSource messageSource() {
        val ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
