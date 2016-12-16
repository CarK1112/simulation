package Simulation;

import Draw.DrawPanel;
import Objects.Gazelle;
import Objects.Tiger;
import Objects.Entity;
import Server.Save;
import Server.Load;
import Server.LoadFiles;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainFrame extends JFrame {

    static final int xRow_INIT = 25;

    static final int yRow_INIT = 25;
    static Thread updateCellsThread;
    private static DrawPanel gameField = new DrawPanel();

    // Settings
    PanelListener myPanelListener = new PanelListener();
    saveItemToJSONListener myNewFileJSON = new saveItemToJSONListener();
    loadJSONToListListener myLoadedFileJSON = new loadJSONToListListener();
    saveItemToServerListener myNewServerJSON = new saveItemToServerListener();
    loadServerToListListener myLoadServerToListListener = new loadServerToListListener();
    // Game options
    startGameAction newStartGameAction = new startGameAction();
    stopGameAction newStopGameAction = new stopGameAction();

    // Server and file loading and saving
    private static Save mySave = new Save();
    private static Load myLoad = new Load();

    JComboBox<Objects.ObjectEnumNames> ObjectNamesComboBox = new JComboBox<>();

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        gameField.addMouseListener(myPanelListener);
        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        // File Menu
        JMenu fileLoadMenu = new JMenu("File");
        menuBar.add(fileLoadMenu);
        JMenu actionsMenu = new JMenu("Actions");
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem stopItem = new JMenuItem("Stop");
        actionsMenu.add(startItem);
        actionsMenu.add(stopItem);
        startItem.addActionListener(newStartGameAction);
        stopItem.addActionListener(newStopGameAction);
        menuBar.add(actionsMenu);

        // Load
        JMenu loadOptionsMenu = new JMenu("Load");
        fileLoadMenu.add(loadOptionsMenu);
        // Load->From server

        JMenuItem loadFromServer = new JMenuItem("From server");
        loadOptionsMenu.add(loadFromServer);
        loadFromServer.addActionListener(myLoadServerToListListener);

        // Load->From file
        JMenuItem loadFromFile = new JMenuItem("From File");
        loadOptionsMenu.add(loadFromFile);
        loadFromFile.addActionListener(myLoadedFileJSON);

        // Set menu
        setJMenuBar(menuBar);
        // Field editor
        // NEW WORLD
        gameField.setColsRows(xRow_INIT, yRow_INIT);

        setLayout(new BorderLayout());
        gameField.repaint();
        add(gameField, BorderLayout.CENTER);
        pack();
        setVisible(true);

    }

   
    private static class startGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                updateCellsThread = new Thread(() -> {
                    try {
                        while (true) {
                            Thread.sleep(200);
                            gameField.doSteps();
                            gameField.repaint();
                        }
                    } catch (InterruptedException e1) {
                        System.out.println("my thread interrupted");
                    }
                });
                updateCellsThread.start();
                System.err.println("Loaded");
            } catch (Exception ex) {
                System.out.println("You can't do that Dave");
            }
        }
    }

    private static class stopGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (updateCellsThread != null) {
                updateCellsThread.interrupt();
            }

            System.err.println("Stopped");
        }
    }

    // FALSE -> SAVES TO LOCAL
    private static class saveItemToJSONListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            if (fileChooser.showSaveDialog(gameField) == JFileChooser.APPROVE_OPTION) {

                try {
                    gameField.allCells.saveToJSONFile(false, fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.err.println("Saved");
            }

        }
    }

    // TRUE -> SAVE TO REMOTE
    private static class saveItemToServerListener implements ActionListener {
// Save to server

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField field1 = new JTextField("simulation.json");
            Object[] message = {
                "File name", field1,};
            int option = JOptionPane.showConfirmDialog(gameField, message, "Enter the file name", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String filename = field1.getText();
                if (filename != null && !filename.isEmpty()) {
                    try {
                        mySave.sendData(gameField.allCells.saveToJSONFile(true, null), filename);

                    } catch (IOException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.err.println("Saved");
                }
            }
        }
    }

    private static class loadJSONToListListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".json", "json");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            if (fileChooser.showOpenDialog(gameField) == JFileChooser.APPROVE_OPTION) {
                gameField.allCells.loadFromJSONFile(false, "", fileChooser.getSelectedFile());
                gameField.repaint();
                System.err.println("Loaded");
            }

        }
    }
    private static LoadFiles myLoadFiles = new LoadFiles();

    private static class loadServerToListListener implements ActionListener {
        // Load JSON from Server

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JComboBox myLoadedFiles = new JComboBox();
            try {
                String json = myLoadFiles.readFiles();
                if (!json.isEmpty()) {
                    JsonParser jsonParser = new JsonParser();
                    JsonArray filesArray = jsonParser.parse(json).getAsJsonArray();
                    for (JsonElement file : filesArray) {
                        System.err.println(file.getAsString());
                        myLoadedFiles.addItem(file.getAsString());
                    }
                }
                int choosenFile = JOptionPane.showConfirmDialog(null, myLoadedFiles, "Choose a file", JOptionPane.DEFAULT_OPTION);
                if (choosenFile == JOptionPane.OK_OPTION) {
                    if (myLoadedFiles.getSelectedItem() != null) {
                        String filename = myLoadedFiles.getSelectedItem().toString();
                        if (filename != null && !filename.isEmpty()) {
                            String JSONData = myLoad.readData(filename);
                            System.err.println(JSONData);
                            if (JSONData != null) {
                                gameField.allCells.loadFromJSONFile(true, JSONData, null);
                                gameField.repaint();
                            } else {
                                JOptionPane.showMessageDialog(gameField, "File is invalid!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                //System.out.println(optionControl.getSelectedItem());

            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class PanelListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {

            final Point startPoint = e.getPoint();

            if (startPoint.x < gameField.getGameFieldSizeX() && startPoint.y < gameField.getGameFieldSizeY()) {
                try {
                    int newX = (int) (Math.floor(startPoint.x / gameField.getCellSize()) * gameField.getCellSize()) / gameField.getCellSize();
                    int newY = (int) (Math.floor(startPoint.y / gameField.getCellSize()) * gameField.getCellSize()) / gameField.getCellSize();
                    boolean cellFound = false;

                    if (gameField.allCells.allEntities.size() > 0) {
                        for (Iterator<Entity> iterator = gameField.allCells.allEntities.iterator(); iterator.hasNext();) {
                            Entity cellKey = iterator.next();
                            int cellX = cellKey.getCellPointX();
                            int cellY = cellKey.getCellPointY();
                            // Find matching cell if found delete it
                            if (cellX == newX && cellY == newY) {
                                cellFound = true;
                                iterator.remove();
                            }
                        }

                    }
                    if (!cellFound) {
                        // set new point
                        Point newCellPoint = new Point(newX, newY);
                        // Create a new instance of the selected object
                        Objects.ObjectEnumNames selectedType = (Objects.ObjectEnumNames) ObjectNamesComboBox.getSelectedItem();
                        Class className = Class.forName("Objects.Gazelle");
                        Entity cellWithClass = (Entity) className.getDeclaredConstructor(Point.class).newInstance(newCellPoint);
                        // Add the cell
                        gameField.allCells.allEntities.add(cellWithClass);
                    }

                    repaint();

                } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                new MainFrame().setVisible(true);
            }

        });

    }

}
