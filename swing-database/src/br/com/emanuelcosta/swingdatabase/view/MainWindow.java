package br.com.emanuelcosta.swingdatabase.view;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JPanel topPanel;
    private JPanel contentPanel;
    private JButton btnDepartment;
    private JButton btnEmployee;

    public MainWindow() {
        initializeComponents();
        configureWindow();
        addComponents();
        addEvents();
    }

    private void initializeComponents() {
        topPanel = new JPanel();
        contentPanel = new JPanel();

        btnDepartment = new JButton("Department");
        btnEmployee = new JButton("Employee");

        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(btnDepartment);
        topPanel.add(btnEmployee);

        contentPanel.setLayout(new BorderLayout());
    }

    private void configureWindow() {
        setTitle("Company Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void addComponents() {
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void addEvents() {
        btnDepartment.addActionListener(e -> openDepartmentForm());
        btnEmployee.addActionListener(e -> openEmployeeForm());
    }

    private void openDepartmentForm() {
        DepartmentForm form = new DepartmentForm();

        contentPanel.removeAll();
        contentPanel.add(form, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void openEmployeeForm() {
        EmployeeForm form = new EmployeeForm();

        contentPanel.removeAll();
        contentPanel.add(form, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public JButton getBtnDepartment() {
        return btnDepartment;
    }

    public JButton getBtnEmployee() {
        return btnEmployee;
    }
}
