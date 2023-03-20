package sg.edu.nus.iss.restpractice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.restpractice.model.Person;
import sg.edu.nus.iss.restpractice.repo.PersonRepo;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personrepo;


    public void save(final Person person){
       personrepo.save(person);
       
    }

    public Optional<Person> findById(final String dataId) throws IOException{
        return personrepo.findById(dataId);
    }


    public List<Person> findAll(int startIndex) throws IOException{
        return personrepo.findAll(startIndex);
    }
    
    


}
