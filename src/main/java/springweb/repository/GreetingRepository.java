package springweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springweb.model.Greeting;

public interface GreetingRepository extends JpaRepository<Greeting, Long> {
    // Custom query methods can be added if necessary
}
