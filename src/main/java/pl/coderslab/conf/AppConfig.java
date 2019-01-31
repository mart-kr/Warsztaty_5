package pl.coderslab.conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"pl.coderslab.controller", "pl.coderslab.service.impl", "pl.coderslab.repository.impl"})
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        //return new DriverManagerDataSource("jdbc:mysql://localhost:3306/booksworkshop5?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8", "root", "coderslab");

        BasicDataSource dataSource = new BasicDataSource(); //pula połączeń do bazy, kiedy wywołujemy close na connection, to połączenie wraca do puli, a nie jest usuwane
        dataSource.setUrl("jdbc:mysql://localhost:3306/booksworkshop5?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("coderslab");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return dataSource;
    }

}
