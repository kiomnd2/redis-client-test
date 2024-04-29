package kr.rediscli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RedisClientTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisClientTestApplication.class, args);
    }
}
