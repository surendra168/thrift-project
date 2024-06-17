package example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
//
//    private DBUtil dbUtil;
//
//    public PersonDAO(DBUtil ds) {
//        this.dbUtil = ds;
//    }

//    public Connection gst() throws SQLException {
//        return DBUtil.getConnection();
//    }

    public void createPerson(Person person) {
        String query = "INSERT INTO persons (id, name, age) VALUES (?, ?, ?)";
//        PersonDAO personDAO = new PersonDAO();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, person.id);
            statement.setString(2, person.name);
            statement.setInt(3, person.age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Person getPerson(int id) {
        String query = "SELECT * FROM persons WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Person person = new Person();
                person.id = resultSet.getInt("id");
                person.name = resultSet.getString("name");
                person.age = resultSet.getInt("age");
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePerson(Person person) {
        String query = "UPDATE persons SET name = ?, age = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, person.name);
            statement.setInt(2, person.age);
            statement.setInt(3, person.id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        String query = "DELETE FROM persons WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> listPersons() {
        List<Person> persons = new ArrayList<>();
        String query = "SELECT * FROM persons";
        try (Connection connection = DBUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Person person = new Person();
                person.id = resultSet.getInt("id");
                person.name = resultSet.getString("name");
                person.age = resultSet.getInt("age");
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }
}
