package br.com.emanuelcosta.swingdatabase.view;

import br.com.emanuelcosta.swingdatabase.dao.EmployeeDAO;
import br.com.emanuelcosta.swingdatabase.dao.DAOException;
import br.com.emanuelcosta.swingdatabase.model.entity.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public EmployeeForm() {
        initializeComponents();
        loadEmployees();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Employee List");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        btnAdd = new JButton("Add Employee");
        btnEdit = new JButton("Edit Employee");
        btnDelete = new JButton("Delete Employee");

        btnAdd.setPreferredSize(new Dimension(150, 30));
        btnEdit.setPreferredSize(new Dimension(150, 30));
        btnDelete.setPreferredSize(new Dimension(150, 30));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);


        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Birth Date", "Salary", "Department"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);


        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        btnAdd.addActionListener(e -> openAddForm());
        btnEdit.addActionListener(e -> openEditForm());
        btnDelete.addActionListener(e -> deleteEmployee());
    }


    public void loadEmployees() {
        try {
            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> employees = dao.findAll();

            tableModel.setRowCount(0);

            for (Employee e : employees) {
                tableModel.addRow(new Object[]{
                        e.getId(),
                        e.getName(),
                        sdf.format(e.getBirthDate()),
                        e.getSalary(),
                        e.getDepartment().getName()
                });
            }

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading employees: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void openAddForm() {
        JFrame frame = new JFrame("Add Employee");
        frame.setContentPane(new EmployeeAddForm(this));
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    private void openEditForm() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to edit.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        JFrame frame = new JFrame("Edit Employee");
        frame.setContentPane(new EmployeeEditForm(this, id));
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    private void deleteEmployee() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an employee to delete.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Really delete this employee?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                new EmployeeDAO().deleteById(id);
                loadEmployees();
            } catch (DAOException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting employee: " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
