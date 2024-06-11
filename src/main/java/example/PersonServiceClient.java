package example;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.List;

public class PersonServiceClient {
    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            PersonService.Client client = new PersonService.Client(protocol);

            // Create a new person
            Person person = new Person();
            person.id = 1;
            person.name = "Ravi Kumar";
            person.age = 30;
            client.createPerson(person);
            System.out.println("Person created: " + person);

            // Get the person
            Person retrievedPerson = client.getPerson(1);
            System.out.println("Retrieved person: " + retrievedPerson);

            // Update the person
            retrievedPerson.name = "Shashi Kumar";
            client.updatePerson(retrievedPerson);
            System.out.println("Person updated: " + retrievedPerson);

            // List all persons
            List<Person> persons = client.listPersons();
            System.out.println("List of persons:");
            for (Person p : persons) {
                System.out.println(p);
            }

            // Delete the person
            client.deletePerson(1);
            System.out.println("Person deleted");

            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
