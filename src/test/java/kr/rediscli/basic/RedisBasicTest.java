package kr.rediscli.basic;

import kr.rediscli.config.RedisConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@Import(RedisConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RedisBasicTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    void testString() {

        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
        String key = "hello";
        String value = "world";
        valueOperations.set(key, value);

        assertThat(value).isEqualTo(valueOperations.get(key));
    }
}
