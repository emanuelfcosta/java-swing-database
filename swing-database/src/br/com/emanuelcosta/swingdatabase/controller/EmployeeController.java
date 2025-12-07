package br.com.emanuelcosta.swingdatabase.controller;

import br.com.emanuelcosta.swingdatabase.dao.DAOException;
import br.com.emanuelcosta.swingdatabase.dao.EmployeeDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;
import br.com.emanuelcosta.swingdatabase.model.entity.Employee;

import java.util.Date;
import java.util.List;


public class EmployeeController {
    private final EmployeeDAO employeeDAO;
    private final DepartmentController departmentController;

    public EmployeeController() {
        this.employeeDAO = new EmployeeDAO();
        this.departmentController = new DepartmentController();
    }

    public void createEmployee(String name, Date birthDate, double salary, int departmentId)
            throws DAOException {

        Department d = departmentController.getDepartment(departmentId);

        if (d == null) {
            throw new DAOException("Department not found for ID: " + departmentId);
        }

        Employee e = new Employee(name, birthDate, salary, d);
        employeeDAO.insert(e);
    }

    public void updateEmployee(int id, String name, Date birthDate, double salary, int departmentId)
            throws DAOException {

        Department d = departmentController.getDepartment(departmentId);

        if (d == null) {
            throw new DAOException("Department not found for ID: " + departmentId);
        }

        Employee e = new Employee(id, name, birthDate, salary, d);
        employeeDAO.update(e);
    }

    public void deleteEmployee(int id) throws DAOException {
        employeeDAO.deleteById(id);
    }

    public Employee getEmployee(int id) throws DAOException {
        return employeeDAO.findById(id);
    }

    public List<Employee> getAllEmployees() throws DAOException {
        return employeeDAO.findAll();
    }
}
