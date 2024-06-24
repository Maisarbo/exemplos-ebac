package br.com.maisa.categoria_meme;

import br.com.maisa.categoria_meme.controllers.ControllerCategoriaMeme;
import br.com.maisa.categoria_meme.entidades.CategoriaMeme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.maisa.categoria_meme.repositorios")
@EnableDiscoveryClient
public class CategoriaMemeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CategoriaMemeApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	}

