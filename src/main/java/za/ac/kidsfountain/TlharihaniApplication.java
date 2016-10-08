package za.ac.kidsfountain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class TlharihaniApplication {

	public static void main(String[] args) {
		SpringApplication.run(TlharihaniApplication.class, args);
	}
//	@Bean
//	@Profile("dev")
//	public CommandLineRunner demo(final SuperAdminService service,final ApplicationService app){
//		return (args)-> {
//			List<SuperAdmin> ls = service.getSuperAdmins();
//			if(ls.isEmpty())
//			{
//				SuperAdmin superAdim =new SuperAdmin("12345",12345);
//				service.createAdim(superAdim);
//			}
//			List<Application> apps = app.getAll();
//			if(apps.isEmpty())
//			{
//				app.createApplication(new Application(false,false));
//			}
//
//		};
//	}
	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		final FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}
