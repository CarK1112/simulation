package Simulation;

import Draw.DrawPanel;
import Objects.Gazelle;
import Objects.Tiger;
import Objects.Entity;
import Server.Save;
import Server.Load;
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

public class MainFrame extends JFrame {

    static final int xRow_MIN = 10;
    static final int xRow_MAX = 40;
    static final int xRow_INIT = 10;

    static final int yRow_MIN = 10;
    static final int yRow_MAX = 50;
    static final int yRow_INIT = 10;
    static Thread updateCellsThread;
    private static DrawPanel gameField = new DrawPanel();
    JPanel myEASTPanel = new JPanel();
    JSlider myRowSlider, myColSlider;

    // Settings 
    changeGridListener myNewGrid = new changeGridListener();
    newItemChangeListener myNewItem = new newItemChangeListener();
    PanelListener myPanelListener = new PanelListener();
    saveItemToJSONListener myNewFileJSON = new saveItemToJSONListener();
    loadJSONToListListener myLoadedFileJSON = new loadJSONToListListener();
    saveItemToServerListener myNewServerJSON = new saveItemToServerListener();
    loadServerToListListener myLoadServerToListListener = new loadServerToListListener();
    // Game options
    startGameAction newStartGameAction = new startGameAction();

    // Server and file loading and saving
    private static Save mySave = new Save();
    private static Load myLoad = new Load();

    JComboBox<Objects.ObjectEnumNames> ObjectNamesComboBox = new JComboBox<>();

    public MainFrame() {

        setSize(1000, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gameField.addMouseListener(myPanelListener);

        System.err.println(gameField.allCells.allEntities.size());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        // File Menu
        JMenu fileLoadMenu = new JMenu("File");
        menuBar.add(fileLoadMenu);
        // File->Reset
        JMenuItem newMenuItem = new JMenuItem("New");
        fileLoadMenu.add(newMenuItem);
        newMenuItem.addActionListener(myNewItem);
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
        // Save
        JMenu saveOptionsMenu = new JMenu("Save");
        fileLoadMenu.add(saveOptionsMenu);
        // Save->To server
        JMenuItem saveToServerMenuItem = new JMenuItem("Save to server");
        saveOptionsMenu.add(saveToServerMenuItem);
        saveToServerMenuItem.addActionListener(myNewServerJSON);

        // Save->AS file
        JMenuItem SaveToFileMenuItem = new JMenuItem("Save as File");
        saveOptionsMenu.add(SaveToFileMenuItem);
        SaveToFileMenuItem.addActionListener(myNewFileJSON);

        // Set menu
        setJMenuBar(menuBar);
        // Field editor
        // NEW WORLD
        gameField.setColsRows(xRow_INIT, yRow_INIT);
        // EAST Layout
        BorderLayout eastLayout = new BorderLayout();
        JPanel editorPanel = new JPanel();
        editorPanel.setPreferredSize(new Dimension(200, 20));
        editorPanel.setLayout(eastLayout);

        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));

        JLabel myRowLabel = new JLabel("Rows: ");
        myRowSlider = new JSlider(JSlider.HORIZONTAL, xRow_MIN, xRow_MAX, xRow_INIT);
        myRowSlider.addChangeListener(myNewGrid);
        myRowSlider.setMajorTickSpacing(20);
        myRowSlider.setPaintTicks(true);
        myRowSlider.setPaintLabels(true);

        JLabel myColLabel = new JLabel("Cols: ");

        myColSlider = new JSlider(JSlider.HORIZONTAL, yRow_MIN, yRow_MAX, yRow_INIT);
        myColSlider.addChangeListener(myNewGrid);
        myColSlider.setMajorTickSpacing(20);
        myColSlider.setPaintTicks(true);
        myColSlider.setPaintLabels(true);

        add(gameField, BorderLayout.CENTER);

        JLabel cellTitles = new JLabel("Entities");

        ObjectNamesComboBox.setModel(new DefaultComboBoxModel<>(Objects.ObjectEnumNames.values()));

        Button startGameButton = new Button("start Sim");
        startGameButton.addActionListener(newStartGameAction);
        editorPanel.add(startGameButton);
        editorPanel.add(cellTitles);
        editorPanel.add(ObjectNamesComboBox);
        editorPanel.add(myRowLabel);
        editorPanel.add(myRowSlider);
        editorPanel.add(myColLabel);
        editorPanel.add(myColSlider);

        /*Entity cellTest = new Tiger(new Point(0, 1));
        Entity cellTest1 = new Gazelle(new Point(0, 5));
        Entity cellTest3 = new Tiger(new Point(5, 4));
        gameField.allCells.allEntities.add(cellTest);
        gameField.allCells.allEntities.add(cellTest1);
        gameField.allCells.allEntities.add(cellTest3);*/
        //gameField.allCells.loadFromJSONFile();
        gameField.repaint();

        repaint();
        add(editorPanel, BorderLayout.EAST);
        pack();

        setVisible(true);

    }

    private static class newItemChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField xField = new JTextField(3);
            JTextField yField = new JTextField(3);

            JPanel myPanel = new JPanel();
            myPanel.add(new JLabel("Rows:"));
            myPanel.add(xField);
            myPanel.add(Box.createHorizontalStrut(15)); // a spacer
            myPanel.add(new JLabel("Columns :"));
            myPanel.add(yField);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Please Enter the desired rows and columns Values", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {

                try {
                    int xRow = Integer.parseInt(xField.getText());
                    int yCol = Integer.parseInt(yField.getText());
                    if (xRow_MAX < xRow || yRow_MAX < yCol) {
                        JOptionPane.showMessageDialog(null,
                                "Error\n"
                                + "The number is greater than your input.\n"
                                + "Allowed numbers are:\n"
                                + "\nRows : " + xRow_MAX + "\n"
                                + "\nColumns : " + yRow_MAX + "\n", "NaN error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        gameField.allCells.allEntities.clear();
                        gameField.setColsRows(xRow, yCol);
                        gameField.repaint();
                    }

                } catch (NumberFormatException nan) {
                    JOptionPane.showMessageDialog(null,
                            "Error\n Invalid input. Either row or column number was not a valid number!", "NaN error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static class startGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            updateCellsThread = new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        gameField.doSteps();
                        gameField.repaint();
                        System.err.println("LEL");
                    }
                } catch (InterruptedException e1) {
                    System.out.println("my thread interrupted");
                }
            });
            updateCellsThread.start();

            System.err.println("Loaded");
        }
    }

    // FALSE -> SAVES TO LOCAL
    private static class saveItemToJSONListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                gameField.allCells.saveToJSONFile(false);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("Saved");
        }
    }

    // TRUE -> SAVE TO REMOTE
    private static class saveItemToServerListener implements ActionListener {
// Save to server

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                mySave.sendData(gameField.allCells.saveToJSONFile(true));

            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("Saved");
        }
    }

    private static class loadJSONToListListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameField.allCells.loadFromJSONFile(false,"");
            gameField.repaint();
            System.err.println("Loaded");
        }
    }

    private static class loadServerToListListener implements ActionListener {
        // Load JSON from Server

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String JSONData = myLoad.readData();
                gameField.allCells.loadFromJSONFile(true,JSONData);
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameField.repaint();
        }
    }

    class changeGridListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            //System.err.println(gameField.allCells.allEntities.size());
            int xSlider = myRowSlider.getValue();
            int ySlider = myColSlider.getValue();
            gameField.setColsRows(xSlider, ySlider);
            gameField.repaint();
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

                    System.err.println("Click found at: " + newX + " " + newY);
                    if (gameField.allCells.allEntities.size() > 0) {
                        for (Iterator<Entity> iterator = gameField.allCells.allEntities.iterator(); iterator.hasNext();) {
                            Entity cellKey = iterator.next();
                            int cellX = cellKey.getCellPointX();
                            int cellY = cellKey.getCellPointY();
                            // Find matching cell if found delete it
                            System.err.println(cellX + "-" + newX + " " + cellY + "-" + newY);
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
                        Class className = Class.forName("Objects." + selectedType.getClassName());
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
