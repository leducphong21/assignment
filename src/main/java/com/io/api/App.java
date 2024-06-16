package com.io.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {App.class},
        scanBasePackages = {"com.io.api"}
)
//@PropertySource(value = {"classpath:/application.properties.properties"}, ignoreResourceNotFound = true)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
