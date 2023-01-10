package eshop.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import eshop.config.JpaConfig;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "eshop.restcontroller")
@Import(JpaConfig.class)
public class WebConfig {

    

}
