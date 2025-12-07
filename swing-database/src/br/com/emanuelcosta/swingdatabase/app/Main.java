package br.com.emanuelcosta.swingdatabase.app;

import br.com.emanuelcosta.swingdatabase.view.MainWindow;

public class Main {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });



    }
}