package example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PersonDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private PersonDAO personDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testCreatePerson() throws SQLException {
        Person person = new Person(1, "Ravi Kumar", 30);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        try (MockedStatic<DBUtil> dummy = Mockito.mockStatic(DBUtil.class)) {
            dummy.when(DBUtil::getConnection)
                    .thenReturn(connection);
            personDAO.createPerson(person);
        }


        verify(preparedStatement).setInt(1, person.id);
        verify(preparedStatement).setString(2, person.name);
        verify(preparedStatement).setInt(3, person.age);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetPersonIfExists() throws SQLException {
        int personId = 1;
        Person expectedPerson = new Person(personId, "Ravi Kumar", 30);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("id")).thenReturn(expectedPerson.id);
        when(resultSet.getString("name")).thenReturn(expectedPerson.name);
        when(resultSet.getInt("age")).thenReturn(expectedPerson.age);

        try (MockedStatic<DBUtil> dummy = Mockito.mockStatic(DBUtil.class)) {
            dummy.when(DBUtil::getConnection)
                    .thenReturn(connection);

            Person actualPerson = personDAO.getPerson(personId);
            verify(preparedStatement).setInt(1, personId);
            verify(preparedStatement).executeQuery();

            assertEquals(expectedPerson.id, actualPerson.id);
            assertEquals(expectedPerson.name, actualPerson.name);
            assertEquals(expectedPerson.age, actualPerson.age);
        }

    }

    @Test
    void testUpdatePerson() throws SQLException {

        Person person = new Person(1, "Shashi Kumar", 30);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        try (MockedStatic<DBUtil> dummy = Mockito.mockStatic(DBUtil.class)) {
            dummy.when(DBUtil::getConnection)
                    .thenReturn(connection);
            personDAO.updatePerson(person);
        }


        verify(preparedStatement).setString(1, person.name);
        verify(preparedStatement).setInt(2, person.age);
        verify(preparedStatement).setInt(3, person.id);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDeletePerson() throws SQLException {
        int personId = 1;

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        try (MockedStatic<DBUtil> dummy = Mockito.mockStatic(DBUtil.class)) {
            dummy.when(DBUtil::getConnection)
                    .thenReturn(connection);
            personDAO.deletePerson(personId);
        }

        verify(connection).prepareStatement("DELETE FROM persons WHERE id = ?");
        verify(preparedStatement).setInt(1, personId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testListPersons() throws SQLException {
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false); // Simulate two rows of data
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Ravi Kumar", "Shashi Kumar");
        when(resultSet.getInt("age")).thenReturn(30, 25);

        List<Person> expectedPersons = Arrays.asList(
                new Person(1, "Ravi Kumar", 30),
                new Person(2, "Shashi Kumar", 25)
        );

        try (MockedStatic<DBUtil> dummy = Mockito.mockStatic(DBUtil.class)) {
            dummy.when(DBUtil::getConnection)
                    .thenReturn(connection);
            List<Person> actualPersons = personDAO.listPersons();

            verify(connection).createStatement();
            verify(statement).executeQuery(anyString());
            verify(resultSet, times(3)).next(); // Called three times: two rows + one false

            // Assert the results
            assertEquals(expectedPersons, actualPersons);
        }



    }
}