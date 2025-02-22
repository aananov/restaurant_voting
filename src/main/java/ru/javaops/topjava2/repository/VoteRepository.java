package ru.javaops.topjava2.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@CacheConfig(cacheNames = "votes")
public interface VoteRepository extends BaseRepository<Vote> {

    @Cacheable
    List<Vote> findAllByVoteDate(LocalDate localDate);

    @Query("select v from Vote v where v.voteDate =:voteDate and v.userId =:userId")
    Optional<Vote> get(int userId, LocalDate voteDate);

    default Vote save(int userId, LocalDate voteDate, int restaurantId) {
        return save(new Vote(voteDate, userId, restaurantId));
    }
}