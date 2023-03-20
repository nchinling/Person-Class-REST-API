package sg.edu.nus.iss.restpractice.repo;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.restpractice.model.Person;

@Repository
public class PersonRepo {
    
    //autowired in a bean.
    @Autowired @Qualifier("personbean")

    private RedisTemplate<String, String> template;


  public void save(Person person){
      this.template.opsForValue().set(person.getDataId(), person.toJSON().toString());
  }

  public List<Person> findAll(int startIndex) throws IOException{
    Set<String> allKeys = template.keys("*");
    List<Person> personarray = new LinkedList<>();
    for (String key : allKeys) {
        String result = template.opsForValue().get(key);

        personarray.add(Person.createPersonObjectFromRedis(result));
    }

    return personarray;

  }

  public Optional<Person> findById(String dataId) throws IOException{
    String json = template.opsForValue().get(dataId);
    if(null == json|| json.trim().length() <= 0){
        return Optional.empty();
    }

    return Optional.of(Person.createPersonObjectFromRedis(json));
  }

  


}
