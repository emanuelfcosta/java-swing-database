package br.com.emanuelcosta.swingdatabase.view;

import br.com.emanuelcosta.swingdatabase.dao.DepartmentDAO;
import br.com.emanuelcosta.swingdatabase.dao.EmployeeDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;
import br.com.emanuelcosta.swingdatabase.model.entity.Employee;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class EmployeeAddForm extends JPanel {

    private JTextField txtName;
    private JDateChooser dateChooserBirth;
    private JTextField txtSalary;
    private JComboBox<Department> cbDepartment;
    private JButton btnSave;

    private final EmployeeForm parent;

    public EmployeeAddForm(EmployeeForm parent) {
        this.parent = parent;
        initializeComponents();
        loadDepartments();
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

        btnSave = new JButton("Save");
        add(btnSave);

        btnSave.addActionListener(e -> saveEmployee());
    }

    private void loadDepartments() {
        try {
            List<Department> list = new DepartmentDAO().findAll();
            for (Department d : list) cbDepartment.addItem(d);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading departments.");
        }
    }

    private void saveEmployee() {
        try {

            Date birthDate = dateChooserBirth.getDate();

            if (birthDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a birth date.");
                return;
            }

            Employee emp = new Employee(
                    txtName.getText(),
                    birthDate,
                    Double.parseDouble(txtSalary.getText()),
                    (Department) cbDepartment.getSelectedItem()
            );

            new EmployeeDAO().insert(emp);

            JOptionPane.showMessageDialog(this, "Employee added!");

            parent.loadEmployees();
            SwingUtilities.getWindowAncestor(this).dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
