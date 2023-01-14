package tw.backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

	@Bean
	public DataSource dataSource() throws Exception {
		Properties properties = new Properties();
		InputStream input = new FileInputStream("src/main/resources/application.properties");

		properties.load(input);

		DriverManagerDataSource driver = new DriverManagerDataSource();
		driver.setDriverClassName("org.postgresql.Driver");
		driver.setUrl(properties.getProperty("spring.datasource.url"));
		System.out.println(properties.getProperty("spring.datasource.username"));
		driver.setUsername(properties.getProperty("spring.datasource.username"));
		driver.setPassword(properties.getProperty("spring.datasource.password"));
		return driver;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource());
	}
}