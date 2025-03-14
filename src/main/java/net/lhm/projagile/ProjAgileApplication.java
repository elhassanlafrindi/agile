package net.lhm.projagile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class ProjAgileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjAgileApplication.class, args);
    }

}
