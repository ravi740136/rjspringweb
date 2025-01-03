package springweb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import springweb.model.Greeting;
import springweb.repository.GreetingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {

	Logger logger = LoggerFactory.getLogger(GreetingService.class);
    private final GreetingRepository greetingRepository;

    @Autowired
    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }
    
    // Fetch greetings by exact content
    public List<Greeting> getGreetingsByContent(String content) {
        return greetingRepository.findByContent(content);
    }

    // Fetch greetings containing a keyword
    public List<Greeting> getGreetingsByKeyword(String keyword) {
        return greetingRepository.findByContentContaining(keyword);
    }

    // Create a new Greeting
    public Greeting createGreeting(Greeting greeting) {
     //   Greeting greeting = new Greeting(content);
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
    public Greeting updateGreeting(long id, Greeting greeting) {
    	//two ways of doing the same thing
       /* return greetingRepository.findById(id)
                .map(existingGreeting -> {
                    existingGreeting.setContent(greeting.getContent());
                    existingGreeting.setHeader(greeting.getHeader());
                    logger.info("Updated greeting: " + existingGreeting);
                    return greetingRepository.save(existingGreeting);
                })
                .orElseThrow(() -> new EntityNotFoundException("Greeting with id " + id + " not found"));*/
    	 Optional<Greeting> optionalGreeting = greetingRepository.findById(id);
    	    if (optionalGreeting.isPresent()) {
    	        Greeting existingGreeting = optionalGreeting.get();
    	        existingGreeting.setContent(greeting.getContent());
    	        existingGreeting.setHeader(greeting.getHeader());
    	        logger.info("Updated greeting: " + existingGreeting);
    	        return greetingRepository.save(existingGreeting);
    	    } else {
    	        throw new EntityNotFoundException("Greeting with id " + id + " not found");
    	    }   
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
