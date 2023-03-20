package sg.edu.nus.iss.restpractice.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.restpractice.model.Person;
import sg.edu.nus.iss.restpractice.service.PersonService;

@RestController
@RequestMapping("/response")
public class AppController implements Serializable {
    
    @Autowired
    private PersonService personService;

    @PostMapping(
        path = "/postbody",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> postBody(@RequestBody String payload) throws IOException{
        
        Person person = Person.createPersonObject(payload);
        personService.save(person);

        //to return object. Method needs to be ResponseEntity<Person>
        return ResponseEntity.ok(person);

        //to return customised message. Method name needs to be ResponseEntity<String>
        // return new ResponseEntity<>("Person saved successfully!", HttpStatus.OK);
    }

    //using path variable approach.
    @GetMapping(path="{userId}")
    public ResponseEntity<String> getUser(@PathVariable(name="userId") String userId) throws IOException{
        Optional<Person> person = personService.findById(userId);

        if(person.isEmpty()){
            JsonObject error = Json.createObjectBuilder()
                    .add("message", "Person %s not found".formatted(userId))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error.toString());
        }
        return ResponseEntity.ok(person.get().toJSON().toString());
        // return ResponseEntity.ok("All is well");
    
    }

    @GetMapping(path="/list")
    public ResponseEntity<List<Person>> getAll(Model model, @RequestParam(defaultValue = "0") Integer startIndex) throws IOException{
        List<Person> listOfPeople = personService.findAll(startIndex);
       
        //both methods work
        return ResponseEntity.ok().body(listOfPeople);
        // return ResponseEntity.ok(listOfPeople);
    }
}
