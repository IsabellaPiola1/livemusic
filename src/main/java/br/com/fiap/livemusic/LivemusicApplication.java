package br.com.fiap.livemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class LivemusicApplication {

	

	public static void main(String[] args) {
		SpringApplication.run(LivemusicApplication.class, args);
	}

	

	
}