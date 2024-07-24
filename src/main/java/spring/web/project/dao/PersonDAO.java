package spring.web.project.dao;

import org.springframework.stereotype.Component;
import spring.web.project.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int COUNT;

    public static final String URL = "jdbc:postgresql://localhost:5432/people";
    public static final String USER = "postgres";
    public static final String PASSWORD = "12341234";

    private static Connection connection;

    static {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSurname(resultSet.getString("surname"));
                person.setEmail(resultSet.getString("email"));
                person.setPhoto(resultSet.getString("photo"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public void add(Person person) {
        // SQL to get the current maximum ID
        String maxIdSQL = "SELECT COALESCE(MAX(id), 0) FROM person";
        // SQL to insert a new person
        String insertSQL = "INSERT INTO person (id, name, surname, email, photo) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Get the current maximum ID
            int newId;
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(maxIdSQL)) {

                if (resultSet.next()) {
                    newId = resultSet.getInt(1) + 1; // Increment the maximum ID
                } else {
                    newId = 1; // If no IDs are found, start with 1
                }
            }

            // Insert the new person with the new ID
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, newId);
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getSurname());
                preparedStatement.setString(4, person.getEmail());
                preparedStatement.setString(5, "http://surl.li/tzttyg"); // Set the photo URL

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting person into database", e);
        }
    }


    public Person one(int id) {
        String SQL = "SELECT * FROM person WHERE id = ?";
        Person person = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setName(resultSet.getString("name"));
                    person.setSurname(resultSet.getString("surname"));
                    person.setEmail(resultSet.getString("email"));
                    person.setPhoto(resultSet.getString("photo"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }


    public void delete(int id) {
        String SQL = "DELETE FROM person WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A row was deleted successfully.");
            } else {
                System.out.println("No row found with the provided id.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        String SQL = "UPDATE person SET name = ?, surname = ?, email = ?, photo = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setString(2, updatedPerson.getSurname());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setString(4, updatedPerson.getPhoto());
            preparedStatement.setInt(5, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A row was updated successfully.");
            } else {
                System.out.println("No row found with the provided id.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Boolean checkEmail(String email) {
        String SQL = "SELECT COUNT(*) FROM person WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true; // Якщо сталася помилка, повертаємо true, щоб не блокувати введення нових email
    }

}
