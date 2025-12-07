package br.com.emanuelcosta.swingdatabase.view;

import br.com.emanuelcosta.swingdatabase.dao.DepartmentDAO;
import br.com.emanuelcosta.swingdatabase.dao.EmployeeDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;
import br.com.emanuelcosta.swingdatabase.model.entity.Employee;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeEditForm extends JPanel {

    private JTextField txtName;
    private JDateChooser dateChooserBirth;
    private JTextField txtSalary;
    private JComboBox<Department> cbDepartment;
    private JButton btnUpdate;

    private final EmployeeForm parent;
    private Employee employee;

    public EmployeeEditForm(EmployeeForm parent, int employeeId) {
        this.parent = parent;
        initializeComponents();
        loadDepartments();
        loadEmployee(employeeId);
    }

    private void initializeComponents() {
        setLayout(new GridLayout(5, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(new JLabel("Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Birth Date:"));
        dateChooserBirth = new JDateChooser();
        dateChooserBirth.setDateFormatString("yyyy-MM-dd");
        add(dateChooserBirth);

        add(new JLabel("Salary:"));
        txtSalary = new JTextField();
        add(txtSalary);

        add(new JLabel("Department:"));
        cbDepartment = new JComboBox<>();
        add(cbDepartment);

        btnUpdate = new JButton("Update");
        add(btnUpdate);

        btnUpdate.addActionListener(e -> updateEmployee());
    }

    private void loadDepartments() {
        try {
            List<Department> list = new DepartmentDAO().findAll();
            for (Department d : list) cbDepartment.addItem(d);
        } catch (Exception ignored) {}
    }

    private void loadEmployee(int id) {
        try {
            employee = new EmployeeDAO().findById(id);

            txtName.setText(employee.getName());
            dateChooserBirth.setDate(employee.getBirthDate());
            txtSalary.setText(String.valueOf(employee.getSalary()));

            cbDepartment.setSelectedItem(employee.getDepartment());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employee.");
        }
    }

    private void updateEmployee() {
        try {
            Date birthDate = dateChooserBirth.getDate();

            if (birthDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a valid birth date.");
                return;
            }

            employee.setName(txtName.getText());
            employee.setBirthDate(birthDate);
            employee.setSalary(Double.parseDouble(txtSalary.getText()));
            employee.setDepartment((Department) cbDepartment.getSelectedItem());

            new EmployeeDAO().update(employee);

            JOptionPane.showMessageDialog(this, "Employee updated!");
            parent.loadEmployees();
            SwingUtilities.getWindowAncestor(this).dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
