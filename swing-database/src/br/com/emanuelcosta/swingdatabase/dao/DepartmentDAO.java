package br.com.emanuelcosta.swingdatabase.dao;


import br.com.emanuelcosta.swingdatabase.database.DatabaseConnection;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    public void insert(Department department) throws DAOException {
        final String sql = "INSERT INTO department (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, department.getName());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                department.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new DAOException("Error inserting department.", e);
        }
    }

    public void update(Department department) throws DAOException {
        final String sql = "UPDATE department SET name = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error updating department.", e);
        }
    }

    public void deleteById(int id) throws DAOException {
        final String sql = "DELETE FROM department WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error deleting department.", e);
        }
    }

    public Department findById(int id) throws DAOException {
        final String sql = "SELECT * FROM department WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                return d;
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error finding department.", e);
        }
    }

    public List<Department> findAll() throws DAOException {
        final String sql = "SELECT * FROM department ORDER BY name";

        List<Department> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                list.add(d);
            }

            return list;

        } catch (SQLException e) {
            throw new DAOException("Error listing departments.", e);
        }
    }
}
