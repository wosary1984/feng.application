package feng.app.service.person;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feng.app.controller.exception.def.NullException;
import feng.app.repository.person.Person;
import feng.app.repository.person.PersonAddress;
import feng.app.repository.person.PersonEvent;
import feng.app.repository.person.PersonPhone;
import feng.app.repository.person.rep.PersonRepository;
import feng.app.service.BaseService;
import feng.app.service.context.ContextCheckService;

@Service
public class PersonService extends BaseService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	ContextCheckService service;

	/***
	 * Get Company List
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Iterable<Person> getPersons() {
		List<Person> dbresult = personRepository.findAll();
		return (Iterable<Person>) this.ContextCheckObjectList(Person.class, dbresult, service);
	}

	public Person createPerson(String data) {
		JSONObject object = new JSONObject(data);
		Person person = new Person();
		String action = object.isNull("action") ? "" : object.getString("action");

		if (action.equalsIgnoreCase("create")) {
			JSONObject jsonPerson = object.getJSONObject("person");
			if (jsonPerson == null)
				throw new NullException("person object is null");
			person.setIdentitycard(jsonPerson.isNull("identitycard") ? "" : jsonPerson.getString("identitycard"));
			person.setPortrait(jsonPerson.isNull("portrait") ? null : jsonPerson.getString("portrait").getBytes());
			// logger.info(jsonPerson.getString("portrait"));
			// logger.info(new String(person.getPortrait()));
			person.setName(jsonPerson.isNull("name") ? "" : jsonPerson.getString("name"));
			if (person.getName().isEmpty()) {
				throw new NullException("name is null");
			}
			person.setBirthdate(jsonPerson.isNull("birthdate") ? "" : jsonPerson.getString("birthdate"));
			person.setBirthplace(jsonPerson.isNull("birthplace") ? "" : jsonPerson.getString("birthplace"));
			person.setGender(jsonPerson.isNull("gender") ? "" : jsonPerson.getString("gender"));

			// person.getPhone().clear();
			JSONArray phones = jsonPerson.getJSONArray("phone");
			for (int i = 0; i < phones.length(); i++) {
				JSONObject phone = phones.getJSONObject(i);
				PersonPhone ph = new PersonPhone();
				ph.setNumber(phone.isNull("number") ? "" : phone.getString("number"));
				ph.setValid(phone.isNull("valid") ? true : phone.getBoolean("valid"));
				ph.setPerson(person);
				person.getPhone().add(ph);
			}

			JSONArray address = jsonPerson.getJSONArray("address");
			for (int i = 0; i < address.length(); i++) {
				JSONObject ad = address.getJSONObject(i);
				PersonAddress pa = new PersonAddress();
				pa.setAddress(ad.isNull("address") ? "" : ad.getString("address"));
				pa.setCountry(ad.isNull("country") ? "" : ad.getString("country"));
				pa.setCity(ad.isNull("city") ? "" : ad.getString("city"));
				pa.setState(ad.isNull("state") ? "" : ad.getString("state"));
				pa.setPostcode(ad.isNull("postcode") ? "" : String.valueOf(ad.getDouble("postcode")));
				pa.setXcoordinate(ad.isNull("xcoordinate") ? "" : String.valueOf(ad.getDouble("xcoordinate")));
				pa.setYcoordinate(ad.isNull("ycoordinate") ? "" : String.valueOf(ad.getDouble("ycoordinate")));
				pa.setValid(ad.isNull("valid") ? true : ad.getBoolean("valid"));
				pa.setPerson(person);
				person.getAddress().add(pa);
			}
		}
		personRepository.save(person);
		return (Person) this.ContextCheckObject(Person.class, person, service);
	}

	public Person getPerson(Long personid) {
		Person person = personRepository.findByPersonid(personid);
		return (Person) this.ContextCheckObject(Person.class, person, service);
	}

	public Person updatePerson(Long personid, String data) {
		JSONObject object = new JSONObject(data);
		Person person = null;
		String action = object.isNull("action") ? "" : object.getString("action");

		if (action.equalsIgnoreCase("edit")) {

			person = personRepository.findByPersonid(personid);
			if (person == null) {
				throw new NullException("person with personid:" + personid + " is not exited!");
			}
			JSONObject jsonPerson = object.getJSONObject("person");
			if (jsonPerson == null)
				throw new NullException("person object is null");

			person.setIdentitycard(jsonPerson.isNull("identitycard") ? "" : jsonPerson.getString("identitycard"));
			person.setPortrait(jsonPerson.isNull("portrait") ? null : jsonPerson.getString("portrait").getBytes());
			person.setBirthdate(jsonPerson.isNull("birthdate") ? "" : jsonPerson.getString("birthdate"));
			person.setBirthplace(jsonPerson.isNull("birthplace") ? "" : jsonPerson.getString("birthplace"));
			person.setGender(jsonPerson.isNull("gender") ? "" : jsonPerson.getString("gender"));

			person.getPhone().clear();
			JSONArray phones = jsonPerson.getJSONArray("phone");
			for (int i = 0; i < phones.length(); i++) {
				JSONObject phone = phones.getJSONObject(i);
				PersonPhone ph = new PersonPhone();
				ph.setNumber(phone.isNull("number") ? "" : phone.getString("number"));
				ph.setValid(phone.isNull("valid") ? true : phone.getBoolean("valid"));
				ph.setPerson(person);
				person.getPhone().add(ph);
			}
			
			person.getAddress().clear();
			JSONArray address = jsonPerson.getJSONArray("address");
			for (int i = 0; i < address.length(); i++) {
				JSONObject ad = address.getJSONObject(i);
				PersonAddress pa = new PersonAddress();
				pa.setAddress(ad.isNull("address") ? "" : ad.getString("address"));
				pa.setCountry(ad.isNull("country") ? "" : ad.getString("country"));
				pa.setCity(ad.isNull("city") ? "" : ad.getString("city"));
				pa.setState(ad.isNull("state") ? "" : ad.getString("state"));
				pa.setPostcode(ad.isNull("postcode") ? "" : String.valueOf(ad.getDouble("postcode")));
				pa.setXcoordinate(ad.isNull("xcoordinate") ? "" : String.valueOf(ad.getDouble("xcoordinate")));
				pa.setYcoordinate(ad.isNull("ycoordinate") ? "" : String.valueOf(ad.getDouble("ycoordinate")));
				pa.setValid(ad.isNull("valid") ? true : ad.getBoolean("valid"));
				pa.setPerson(person);
				person.getAddress().add(pa);
			}
			
			//person.getEvents().clear();
			JSONArray years = object.getJSONArray("events");
			for (int i = 0; i < years.length(); i++) {
				JSONArray events = years.getJSONObject(i).getJSONArray("events");
				for(int j = 0;j< events.length();j++) {
					
					JSONObject one = events.getJSONObject(j);
					logger.info(one.toString());
					PersonEvent pe = new PersonEvent();
					pe.setId(one.isNull("id") ? "" : one.getString("id"));
					if (pe.getId().isEmpty())
						throw new NullException("event id is not set");
					pe.setEventdate(one.isNull("date") ? "" : one.getString("date"));
					pe.setTitle(one.isNull("title") ? "" : one.getString("title"));
					pe.setContent(one.isNull("content") ? "" : one.getString("content"));
					
					boolean exist = false;
					for (PersonEvent o : person.getEvents()) {
						if(o.getId().equalsIgnoreCase(pe.getId())){
							exist = true;
							//do nothing now
							break;
						}
					}
					if(!exist) {
						pe.setPerson(person);
						person.getEvents().add(pe);
					}
					
				}
				
			}

		}
		personRepository.save(person);
		return (Person) this.ContextCheckObject(Person.class, person, service);
	}
}
