package springweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import springweb.model.Greeting;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {
    // Custom query methods can be added if necessary

    // Method 1: Find by exact content
    List<Greeting> findByContent(String content);

    // Method 2: Find by content containing a keyword (case-sensitive)
    List<Greeting> findByContentContaining(String keyword);

    // Method 3: Find by content starting with a prefix
    List<Greeting> findByContentStartingWith(String prefix);

    // Method 4: Find by content ending with a suffix
    List<Greeting> findByContentEndingWith(String suffix);

    // Method 5: Custom JPQL query to find greetings with content longer than a specific length
    @Query("SELECT g FROM Greeting g WHERE LENGTH(g.content) > :length")
    List<Greeting> findGreetingsByContentLengthGreaterThan(@Param("length") int length);

    // Method 6: Custom SQL query to find greetings with specific content (native query)
    @Query(value = "SELECT * FROM GREETING WHERE content LIKE %:keyword%", nativeQuery = true)
    List<Greeting> findGreetingsByContentKeyword(@Param("keyword") String keyword);

}
