package br.com.emanuelcosta.swingdatabase.dao;

import br.com.emanuelcosta.swingdatabase.database.DatabaseConnection;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;
import br.com.emanuelcosta.swingdatabase.model.entity.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    public void insert(Employee employee) throws DAOException {
        final String sql = "INSERT INTO employee (name, birth_date, salary, department_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, employee.getName());
            stmt.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
            stmt.setDouble(3, employee.getSalary());
            stmt.setInt(4, employee.getDepartment().getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DAOException("Error inserting employee.", e);
        }
    }

    public void update(Employee employee) throws DAOException {
        final String sql = "UPDATE employee SET name = ?, birth_date = ?, salary = ?, department_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getName());
            stmt.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
            stmt.setDouble(3, employee.getSalary());
            stmt.setInt(4, employee.getDepartment().getId());
            stmt.setInt(5, employee.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error updating employee.", e);
        }
    }

    public void deleteById(int id) throws DAOException {
        final String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error deleting employee.", e);
        }
    }

    public Employee findById(int id) throws DAOException {
        final String sql =
                "SELECT e.*, d.name AS department_name " +
                        "FROM employee e " +
                        "INNER JOIN department d ON e.department_id = d.id " +
                        "WHERE e.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Department d = new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );

                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setBirthDate(new java.util.Date(rs.getDate("birth_date").getTime()));
                emp.setSalary(rs.getDouble("salary"));
                emp.setDepartment(d);

                return emp;
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error finding employee.", e);
        }
    }

    public List<Employee> findAll() throws DAOException {
        final String sql =
                "SELECT e.*, d.name AS department_name " +
                        "FROM employee e " +
                        "INNER JOIN department d ON e.department_id = d.id " +
                        "ORDER BY e.name";

        List<Employee> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Department d = new Department(
                        rs.getInt("department_id"),
                        rs.getString("department_name")
                );

                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setBirthDate(new java.util.Date(rs.getDate("birth_date").getTime()));
                emp.setSalary(rs.getDouble("salary"));
                emp.setDepartment(d);

                list.add(emp);
            }

            return list;

        } catch (SQLException e) {
            throw new DAOException("Error listing employees.", e);
        }
    }
}
