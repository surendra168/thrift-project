package example;

import org.apache.thrift.TException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceHandlerTest {

    @Mock
    private PersonDAO personDAO;

    @InjectMocks
    private PersonServiceHandler personServiceHandler;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreatePerson() throws TException {
        Person samplePerson = new Person(1, "Ravi Kumar", 30);
        doNothing().when(personDAO).createPerson(any(Person.class));

        personServiceHandler.createPerson(samplePerson);

        verify(personDAO, times(1)).createPerson(samplePerson);
    }

    @Test
    void testGetPerson() throws TException {
        Person samplePerson = new Person(1, "Ravi Kumar", 30);
        when(personDAO.getPerson(1)).thenReturn(samplePerson);

        Person person = personServiceHandler.getPerson(1);

        assertEquals(samplePerson, person);
        verify(personDAO, times(1)).getPerson(1);
    }

    @Test
    void testUpdatePerson() throws TException {
        Person samplePerson = new Person(1, "Ravi Kumar", 30);
        doNothing().when(personDAO).updatePerson(any(Person.class));
        personServiceHandler.updatePerson(samplePerson);

        verify(personDAO, times(1)).updatePerson(samplePerson);
    }

    @Test
    void testDeletePerson() throws TException {
        doNothing().when(personDAO).deletePerson(anyInt());
        personServiceHandler.deletePerson(1);
        verify(personDAO, times(1)).deletePerson(1);
    }

    @Test
    void testListPersons() throws TException {
        Person samplePerson = new Person(1, "Ravi Kumar", 30);
        List<Person> persons = Collections.singletonList(samplePerson);
        when(personDAO.listPersons()).thenReturn(persons);

        List<Person> result = personServiceHandler.listPersons();

        assertEquals(persons, result);
        verify(personDAO, times(1)).listPersons();
    }
}