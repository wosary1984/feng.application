package feng.app.controller.person;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feng.app.controller.BaseController;
import feng.app.controller.RequestPath;
import feng.app.repository.person.Person;
import feng.app.service.person.PersonService;

@RestController
public class PersonController extends BaseController implements RequestPath {

	@Autowired
	PersonService personService;

	/***
	 * Get all companies
	 * 
	 * @return
	 */
	@RequestMapping(path = PATH_GET_ALL_PERSON, method = RequestMethod.GET)
	public Iterable<Person> getPersons() {
		 return personService.getPersons();
	}
	
	@RequestMapping(path = RequestPath.PATH_GET_CREATE_EDIT_PERSON, method = RequestMethod.POST)
	public Person createCompany(@RequestBody String data) {
		return personService.createPerson(data);
	}
	
	@RequestMapping(path = PATH_GET_CREATE_EDIT_PERSON + "/{personid}", method = RequestMethod.GET)
	public Person getPerson(@PathVariable Long personid) {
		return personService.getPerson(personid);
	}
	
	@RequestMapping(path = PATH_GET_CREATE_EDIT_PERSON + "/{personid}", method = RequestMethod.POST)
	public Person updatePerson(@PathVariable Long personid, @RequestBody String data) throws JSONException {
		personService.updatePerson(personid, data);
		return personService.getPerson(personid);
	}
}
