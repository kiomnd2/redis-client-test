package kr.rediscli.domain.like;

import kr.rediscli.infrastructure.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class LikeServiceType1 {
    private final LikeRepository likeRepository;
    private final static String LIKE_COUNT = "LIKE_COUNT";

    @CacheEvict(cacheNames = LIKE_COUNT, key = "#like.userId", cacheManager = "cacheManager")
    public void insertLike(Likes like) {
        likeRepository.save(like);
    }

    @Cacheable(cacheNames = LIKE_COUNT, key = "#userId",cacheManager = "cacheManager")
    public Likes getLike(String userId) {
        return likeRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("찾을 수 없음"));
    }


}
