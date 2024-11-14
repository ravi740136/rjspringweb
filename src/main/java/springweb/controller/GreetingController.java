package springweb.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import springweb.model.Greeting;
import springweb.service.GreetingService;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // Create a new Greeting
    @PostMapping
    public ResponseEntity<Greeting> createGreeting(@RequestBody String content) {
        Greeting createdGreeting = greetingService.createGreeting(content);
        return new ResponseEntity<>(createdGreeting, HttpStatus.CREATED);
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

    // Update an existing Greeting
    @PutMapping("/{id}")
    public ResponseEntity<Greeting> updateGreeting(@PathVariable long id, @RequestBody String content) {
        Greeting updatedGreeting = greetingService.updateGreeting(id, content);
        if (updatedGreeting != null) {
            return new ResponseEntity<>(updatedGreeting, HttpStatus.OK);
        } else {
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
