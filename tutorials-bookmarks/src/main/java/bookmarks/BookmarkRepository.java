package bookmarks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by ling on 17/7/18.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
