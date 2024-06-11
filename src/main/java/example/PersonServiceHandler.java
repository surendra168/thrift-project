package example;

import org.apache.thrift.TException;
import java.util.List;

public class PersonServiceHandler implements PersonService.Iface {
    private final PersonDAO personDAO = new PersonDAO();

    @Override
    public void createPerson(Person person) throws TException {
        personDAO.createPerson(person);
    }

    @Override
    public Person getPerson(int id) throws TException {
        return personDAO.getPerson(id);
    }

    @Override
    public void updatePerson(Person person) throws TException {
        personDAO.updatePerson(person);
    }

    @Override
    public void deletePerson(int id) throws TException {
        personDAO.deletePerson(id);
    }

    @Override
    public List<Person> listPersons() throws TException {
        return personDAO.listPersons();
    }
}
