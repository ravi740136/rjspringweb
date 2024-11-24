 package springweb.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import springweb.model.Greeting;
import springweb.service.GreetingService;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    private final GreetingService greetingService;
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
   
    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // Create a new Greeting
    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
        Greeting createdGreeting = greetingService.createGreeting(greeting);
        logger.info("created greeting "+createdGreeting);
        return new ResponseEntity<>(createdGreeting, HttpStatus.CREATED);
    }
    
 // Endpoint to fetch greetings by exact content
    @GetMapping("/by-content")
    public List<Greeting> getGreetingsByContent(@RequestParam String content) {
        return greetingService.getGreetingsByContent(content);
    }

    // Endpoint to fetch greetings containing a keyword
    @GetMapping("/by-keyword")
    public List<Greeting> getGreetingsByKeyword(@RequestParam String keyword) {
        return greetingService.getGreetingsByKeyword(keyword);
    }

    // Get all Greetings
    @GetMapping
    public ResponseEntity<List<Greeting>> getAllGreetings() {
        List<Greeting> greetings = greetingService.getAllGreetings();
        return new ResponseEntity<>(greetings, HttpStatus.OK);
    }

    // Get Greeting by ID
    @GetMapping("/{id}")
    public ResponseEntity<Greeting> getGreetingById(@PathVariable long id) {
        Greeting greeting = greetingService.getGreetingById(id);
        if (greeting != null) {
            return new ResponseEntity<>(greeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable long id, @RequestBody Greeting greeting) {
        try {
            Greeting updatedGreeting = greetingService.updateGreeting(id, greeting);
            return new ResponseEntity<>(updatedGreeting, HttpStatus.OK);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Delete a Greeting
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGreeting(@PathVariable long id) {
        boolean isDeleted = greetingService.deleteGreeting(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
