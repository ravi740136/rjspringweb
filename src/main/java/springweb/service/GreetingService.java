package springweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springweb.model.Greeting;
import springweb.repository.GreetingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    // Create a new Greeting
    public Greeting createGreeting(String content) {
        Greeting greeting = new Greeting(content);
        return greetingRepository.save(greeting);
    }

    // Get all Greetings
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }

    // Get Greeting by ID
    public Greeting getGreetingById(long id) {
        Optional<Greeting> greeting = greetingRepository.findById(id);
        return greeting.orElse(null);  // Returns null if not found
    }

    // Update a Greeting
    public Greeting updateGreeting(long id, String content) {
        Optional<Greeting> existingGreeting = greetingRepository.findById(id);
        if (existingGreeting.isPresent()) {
            Greeting greeting = existingGreeting.get();
            greeting.setContent(content);
            return greetingRepository.save(greeting);
        }
        return null; // Return null if not found
    }

    // Delete a Greeting by ID
    public boolean deleteGreeting(long id) {
        Optional<Greeting> greeting = greetingRepository.findById(id);
        if (greeting.isPresent()) {
            greetingRepository.deleteById(id);
            return true;
        }
        return false; // Return false if not found
    }
}
