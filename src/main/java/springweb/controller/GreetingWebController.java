package springweb.controller;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import springweb.model.Greeting;

@Controller
@RequestMapping("/greetings")
public class GreetingWebController {

	private final static Logger logger = LoggerFactory.getLogger(GreetingWebController.class);
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://localhost:8080/rjspringweb/api/greetings";

    @Autowired
    public GreetingWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
 // View a single greeting
    @GetMapping("/{id}")
    public String viewGreeting(@PathVariable long id, Model model) {
        String url = BASE_URL + "/" + id;
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        if (greeting != null) {
            model.addAttribute("greeting", greeting);
            return "view-greeting"; // Name of the Thymeleaf template for viewing a single greeting
        } else {
            return "redirect:/greetings"; // Redirect to list if not found
        }
    }


    // Display all greetings
    @GetMapping
    public String getAllGreetings(Model model) {
    	
    	logger.info("request to get all greetings");
        Greeting[] greetingsArray = restTemplate.getForObject(BASE_URL, Greeting[].class);
        model.addAttribute("greetings", Arrays.asList(greetingsArray));
        return "greetings";
    }

    // Show form for creating a new greeting
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "create-greeting";
    }
    
    @PostMapping
    public String createGreeting(@ModelAttribute Greeting greeting) {
        // Log object to confirm correct binding
        logger.info("Greeting object: " + greeting);
        logger.info("ID: " + greeting.getId());
        logger.info("Content: " + greeting.getContent());
        logger.info("Header: " + greeting.getHeader());

        // POST the object
        restTemplate.postForObject(BASE_URL, greeting, Greeting.class);
        return "redirect:/greetings";
    }

    // Show form for editing an existing greeting
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        String url = BASE_URL + "/" + id;
        
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        logger.info("greeting to be edited "+greeting);
        model.addAttribute("greeting", greeting);
        return "edit-greeting";
    }

    // Update an existing greeting
    @PostMapping("/{id}")
    public String updateGreeting(@PathVariable long id, @ModelAttribute Greeting greeting) {
        String url = BASE_URL + "/" + id;
        restTemplate.put(url, greeting);
        return "redirect:/greetings";
    }

    // Show confirmation for deleting a greeting
    @GetMapping("/{id}/delete")
    public String confirmDelete(@PathVariable long id, Model model) {
        String url = BASE_URL + "/" + id;
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        model.addAttribute("greeting", greeting);
        return "delete-greeting";
    }

    // Delete a greeting
    @PostMapping("/{id}/delete")
    public String deleteGreeting(@PathVariable long id) {
        String url = BASE_URL + "/" + id;
        restTemplate.delete(url);
        return "redirect:/greetings";
    }
}
