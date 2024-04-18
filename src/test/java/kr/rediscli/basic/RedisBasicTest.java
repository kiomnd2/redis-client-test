package kr.rediscli.basic;

import kr.rediscli.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testList() {
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        String key = "myList";
        listOperations.leftPush(key, "E");
        listOperations.rightPush(key, "R");
        List<Object> range = listOperations.range(key, 0, -1);

        assertThat(range).contains("E", "R");
    }

    @Test
    void limitMax10CountList() {
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();
        String key = "myList2";

        for (int i=0 ; i<100 ; i++) {
            listOperations.leftPush(key, i);
            listOperations.trim(key, 0, 9);
        }
        List<Object> range = listOperations.range(key, 0, -1);

        assertThat(range.size()).isEqualTo(10);
        assertThat(range).containsExactlyInAnyOrder(90,91,92,93,94,95,96,97,98,99);
    }

    @Test
    void testHash() {
        String key = "myHash";
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();
        String hashKey = "product:123";
        String hashValue = "Hello world";
        hashOperations.put(key, hashKey, hashValue);

        assertThat(hashValue).isEqualTo(hashOperations.get(key, hashKey));
    }

    @Test
    void testSet() {
        String key = "mySet";
        SetOperations<Object, Object> setOperations = redisTemplate.opsForSet();
        setOperations.add(key+1, "A");
        setOperations.add(key+1, "B");
        setOperations.add(key+1, "C");
        setOperations.add(key+1, "D");

        Set<Object> members = setOperations.members(key+1);
        assertThat(members).contains("A","B","C","D");

        setOperations.add(key+2, "C");
        setOperations.add(key+2, "D");
        setOperations.add(key+2, "F");
        setOperations.add(key+2, "G");

        Set<Object> difference = setOperations.difference(key + 1, key + 2);
        assertThat(difference).contains("B", "A");

        Set<Object> union = setOperations.union(key + 1, key + 2);
        assertThat(union).containsExactly("A", "B", "C","D","F","G");
    }

    @Test
    void sortedSetTest() {
        String key = "mySortedSet";
        ZSetOperations<Object, Object> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key, "user:A", 100);
        zSetOperations.add(key, "user:B", 0);
        zSetOperations.add(key, "user:C", 50);

        Set<Object> range = zSetOperations.reverseRange(key, 0, -1);
        assertThat(range).containsExactly("user:A", "user:C", "user:B");
    }
}
