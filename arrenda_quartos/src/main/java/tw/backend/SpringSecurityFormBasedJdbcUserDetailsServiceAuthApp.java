package tw.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tw.backend.dao.AdDao;

import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages = "tw.backend")
public class SpringSecurityFormBasedJdbcUserDetailsServiceAuthApp {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(SpringSecurityFormBasedJdbcUserDetailsServiceAuthApp.class, args);
                System.out.println("\nOPEN:  http://localhost:8080/");

	}

}
