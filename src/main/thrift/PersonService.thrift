namespace java example

struct Person {
    1: i32 id,
    2: string name,
    3: i32 age
}

service PersonService {
    void createPerson(1: Person person),
    Person getPerson(1: i32 id),
    void updatePerson(1: Person person),
    void deletePerson(1: i32 id),
    list<Person> listPersons()
}