package kr.rediscli.domain.like;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeServiceType1Test {

    @Autowired
    private LikeServiceType1 serviceType1;

    @Transactional
    @Test
    public void insertCacheTest() {
        Likes likes = Likes.builder()
                .userId("userID")
                .likeCount(0)
                .build();
        serviceType1.insertLike(likes);

        Likes userID = serviceType1.getLike("userID");
        Likes like = serviceType1.getLike(userID.getUserId());
    }
}
