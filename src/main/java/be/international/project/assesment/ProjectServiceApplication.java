package be.international.project.assesment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import be.international.project.assesment.config.RibbonConfiguration;
import io.micrometer.core.instrument.MeterRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("be.international.project.assesment.controller"))
				.paths(PathSelectors.any()).build().apiInfo(new ApiInfoBuilder().version("1.0").title("PROJECT API")
						.description("Documentation PROJECT API v1.0").build());
	}

	@Bean
	MeterRegistryCustomizer meterRegistryCustomizer(MeterRegistry meterRegistry) {
		return registry -> {
			meterRegistry.config().commonTags("application", "project");
		};
	}
}

