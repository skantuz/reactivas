package co.com.bancolombia.reactivas.configuration;

import co.com.bancolombia.reactivas.repository.TestRepository;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Log4j2
@Configuration
@EnableR2dbcRepositories(basePackageClasses = TestRepository.class)
public class DBConnection extends AbstractR2dbcConfiguration {

    @Value("${db.host}")
    private String host;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Value("${db.database}")
    private String dbName;
    @Value("${db.port}")
    private String port;



    @Bean
    @Override
    public ConnectionFactory connectionFactory() {

        final PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder() //
                .host(host)
                .port(Integer.parseInt(port))
                .database(dbName)
                .username(username)
                .password(password)
                .build();
        PostgresqlConnectionFactory connection = new PostgresqlConnectionFactory(config);
        connection.create().doOnError(throwable -> {
            log.fatal(throwable);
            System.exit(1);

        }).subscribe();
        return connection;
    }
}

