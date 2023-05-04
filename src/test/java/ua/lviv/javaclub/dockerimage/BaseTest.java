package ua.lviv.javaclub.dockerimage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
public abstract class BaseTest {

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:5.7.34")
        .withUsername("user")
        .withPassword("password")
        .withUrlParam("serverTimezone", "UTC")
        .withUrlParam("TC_REUSABLE", "true");

    @Container
    private static final GenericContainer<?> REDIS = new GenericContainer<>(DockerImageName.parse("redis:7.0.7"))
        .withExposedPorts(6379);

    @DynamicPropertySource
    public static void seProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> {
            var jdbcUrl = MYSQL.getJdbcUrl();
            log.info("spring.datasource.url:{}", jdbcUrl);
            return jdbcUrl;
        });

        registry.add("spring.data.redis.host", () -> {
            var host = REDIS.getHost();
            log.info("spring.data.redis.host:{}", host);
            return host;
        });

        registry.add("spring.data.redis.port", () -> {
            var mappedPort = REDIS.getMappedPort(6379).toString();
            log.info("spring.data.redis.port:{}", mappedPort);
            return mappedPort;
        });
    }
}
