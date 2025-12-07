package br.com.emanuelcosta.swingdatabase.view;

import br.com.emanuelcosta.swingdatabase.dao.DepartmentDAO;
import br.com.emanuelcosta.swingdatabase.model.entity.Department;

import javax.swing.*;
import java.awt.*;

public class DepartmentEditForm extends JPanel {

    private JTextField txtName;
    private JButton btnSave;
    private JButton btnCancel;

    private int departmentId;
    private DepartmentForm parentForm;

    public DepartmentEditForm(DepartmentForm parentForm, int id, String currentName) {
        this.departmentId = id;
        this.parentForm = parentForm;
        initializeComponents(currentName);
    }

    private void initializeComponents(String currentName) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblName = new JLabel("Department Name:");

        txtName = new JTextField(20);
        txtName.setText(currentName);

        btnSave = new JButton("Update");
        btnCancel = new JButton("Cancel");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(lblName, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(btnCancel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        add(btnSave, gbc);

        btnCancel.addActionListener(e -> closeWindow());
        btnSave.addActionListener(e -> updateDepartment());
    }

    private void updateDepartment() {
        try {
            String name = txtName.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.");
                return;
            }

            Department d = new Department();
            d.setId(departmentId);
            d.setName(name);

            DepartmentDAO dao = new DepartmentDAO();
            dao.update(d);

            JOptionPane.showMessageDialog(this, "Department updated successfully!");

            parentForm.refreshTable();
            closeWindow();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error updating department: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeWindow() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }
}
