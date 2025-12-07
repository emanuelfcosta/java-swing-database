package br.com.emanuelcosta.swingdatabase.view;

import br.com.emanuelcosta.swingdatabase.dao.DAOException;
import br.com.emanuelcosta.swingdatabase.dao.DepartmentDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DepartmentForm extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    public DepartmentForm() {
        initializeComponents();
        loadDepartments();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Department List");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        btnAdd = new JButton("Add Department");
        btnAdd.setPreferredSize(new Dimension(160, 30));

        btnEdit = new JButton("Edit Department");
        btnEdit.setPreferredSize(new Dimension(160, 30));

        btnDelete = new JButton("Delete Department");
        btnDelete.setPreferredSize(new Dimension(160, 30));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(buttonPanel, BorderLayout.EAST);


        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);


        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        btnAdd.addActionListener(e -> openAddForm());
        btnEdit.addActionListener(e -> openEditForm());
        btnDelete.addActionListener(e -> deleteSelectedDepartment());
    }


    private void openAddForm() {
        JFrame frame = new JFrame("Add Department");
        frame.setContentPane(new DepartmentAddForm(this));
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    private void openEditForm() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a department to edit.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        JFrame frame = new JFrame("Edit Department");
        frame.setContentPane(new DepartmentEditForm(this, id, name));
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }


    private void deleteSelectedDepartment() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a department to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String name = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the department:\n\n" +
                        "ID: " + id + "\nName: " + name + "\n",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            DepartmentDAO dao = new DepartmentDAO();
            dao.deleteById(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Department deleted successfully!"
            );

            refreshTable();

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting department: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    public void refreshTable() {
        loadDepartments();
    }


    private void loadDepartments() {
        try {
            DepartmentDAO dao = new DepartmentDAO();
            List<Department> departments = dao.findAll();

            tableModel.setRowCount(0);

            for (Department d : departments) {
                tableModel.addRow(new Object[]{
                        d.getId(),
                        d.getName()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading departments: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
