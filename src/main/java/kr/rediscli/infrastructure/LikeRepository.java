package kr.rediscli.infrastructure;

import kr.rediscli.domain.like.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserId(String userId);
}
