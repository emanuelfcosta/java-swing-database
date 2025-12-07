package br.com.emanuelcosta.swingdatabase.controller;

import br.com.emanuelcosta.swingdatabase.dao.DAOException;
import br.com.emanuelcosta.swingdatabase.dao.DepartmentDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;

import java.util.List;

public class DepartmentController {

    private final DepartmentDAO departmentDAO;

    public DepartmentController() {
        this.departmentDAO = new DepartmentDAO();
    }

    public void createDepartment(String name) throws DAOException {
        Department d = new Department(name);
        departmentDAO.insert(d);
    }

    public void updateDepartment(int id, String name) throws DAOException {
        Department d = new Department(id, name);
        departmentDAO.update(d);
    }

    public void deleteDepartment(int id) throws DAOException {
        departmentDAO.deleteById(id);
    }

    public Department getDepartment(int id) throws DAOException {
        return departmentDAO.findById(id);
    }

    public List<Department> getAllDepartments() throws DAOException {
        return departmentDAO.findAll();
    }
}
