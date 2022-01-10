package ru.vsu.cs.kunakhova_a_y;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ru.vsu.cs.kunakhova_a_y.utils.ArrayUtils;
import ru.vsu.cs.kunakhova_a_y.utils.SwingUtils;
import ru.vsu.cs.kunakhova_a_y.utils.JTableUtils;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable tableBigArray;
    private JButton buttonInputBigArrayFromFile;
    private JTable tableSmallArray;
    private JButton buttonOutputSmallArray;
    private JButton buttonCreateRandomBigArray;
    private JButton buttonCreateRandomSmallArray;
    private JButton buttonIfSmallArrayInBigArray;
    private JButton buttonOutputBigArray;
    private JButton buttonInputSmallArrayFromFile;
    private JLabel textResult;
    private JTextField textFieldRowBigArray;
    private JTextField textFieldColumnBigArray;
    private JButton buttonCreateModelBigArray;
    private JTextField textFieldRowSmallArray;
    private JTextField textFieldColumnSmallArray;
    private JButton buttonCreateModelSmallArray;
    private JButton buttonOutputResult;
    private final JFileChooser fileChooserOpen;
    private final JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;
    int[] result;

    public FrameMain() {

        this.setTitle("Task 8");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);

        menuLookAndFeel = new JMenu();
        menuLookAndFeel.setText("Вариант 26");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        this.pack();

        buttonInputBigArrayFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputArrayFromFile(tableBigArray);
            }
        });

        buttonInputSmallArrayFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inputArrayFromFile(tableSmallArray);
            }
        });
        buttonCreateRandomBigArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createRandomArray(tableBigArray);
            }
        });
        buttonCreateRandomSmallArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createRandomArray(tableSmallArray);
            }
        });
        buttonOutputBigArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                outputArrayToFile(tableBigArray);
            }
        });

        buttonIfSmallArrayInBigArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    int[][] bigArray = JTableUtils.readIntMatrixFromJTable(tableBigArray);
                    int[][] smallArray = JTableUtils.readIntMatrixFromJTable(tableSmallArray);
                    try {
                        assert bigArray != null && smallArray != null;
                        ifSmallArrayInBigArray.solution(bigArray, smallArray);
                    } catch (Exception e) {
                        SwingUtils.showMyErrorMessageBox("Отсутствует один из массивов.", "Error");
                    }
                    result = ifSmallArrayInBigArray.solution(bigArray, smallArray);
                    textResult.setText("Результат: " + result[0] + "; " + result[1]);
                } catch (Exception e) {
                    SwingUtils.showMyErrorMessageBox("Ошибка чтения массива из таблицы.", "Error");
                }
            }
        });
        buttonCreateModelBigArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSizeOfJFrame(tableBigArray, Integer.parseInt(textFieldRowBigArray.getText()), Integer.parseInt(textFieldColumnBigArray.getText()));
            }
        });
        buttonCreateModelSmallArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSizeOfJFrame(tableSmallArray, Integer.parseInt(textFieldRowSmallArray.getText()), Integer.parseInt(textFieldColumnSmallArray.getText()));
            }
        });
        buttonOutputSmallArray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArrayToFile(tableSmallArray);
            }
        });
        buttonOutputResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                outputResultToFile(result);
            }
        });
    }

    private void setSizeOfJFrame(JTable myJTable, int row, int column) {
        DefaultTableModel dtm = (DefaultTableModel) myJTable.getModel();
        dtm.setRowCount(row);
        dtm.setColumnCount(column);
        myJTable.setModel(dtm);
        myJTable.updateUI();
    }

    private void createRandomArray(JTable myJTable) {
        try {
            int[][] matrix = ArrayUtils.createRandomIntMatrix(
                    myJTable.getRowCount(), myJTable.getColumnCount(), 100);
            JTableUtils.writeArrayToJTable(myJTable, matrix);
        } catch (Exception e) {
            SwingUtils.showMyErrorMessageBox("Ошибка создания случайного массива", "Error");
        }
    }

    private void inputArrayFromFile(JTable myJTable) {
        try {
            if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                int[][] arr = ArrayUtils.readIntArray2FromFile(fileChooserOpen.getSelectedFile().getPath());
                assert arr != null;
                JTableUtils.writeArrayToJTable(myJTable, arr, "%d");
            }
        } catch (Exception e) {
            SwingUtils.showMyErrorMessageBox("Не удалось загрузить массив из файла", "Error");
        }
    }

    public void outputArrayToFile(JTable myJTable) {
        try {
            if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                int[][] arrayToFile = JTableUtils.readIntMatrixFromJTable(myJTable);
                String file = fileChooserSave.getSelectedFile().getPath();
                if (!file.toLowerCase().endsWith(".txt")) {
                    file += ".txt";
                }
                ArrayUtils.writeArrayToFile(file, arrayToFile);
            }
        } catch (Exception e) {
            SwingUtils.showMyErrorMessageBox("Не удалось сохранить массив.", "Error");
        }
    }

    public void outputResultToFile(int[] result) {
        try {
            if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                String file = fileChooserSave.getSelectedFile().getPath();
                if (!file.toLowerCase().endsWith(".txt")) {
                    file += ".txt";
                }
                ArrayUtils.writeArrayToFile(file, result);
            }
        } catch (Exception e) {
            SwingUtils.showMyErrorMessageBox("Не удалось сохранить результат.", "Error");
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 7, new Insets(10, 10, 10, 10), 10, 10));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tableBigArray = new JTable();
        tableBigArray.setAutoResizeMode(2);
        tableBigArray.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        tableBigArray.putClientProperty("Table.isFileList", Boolean.FALSE);
        tableBigArray.putClientProperty("html.disable", Boolean.FALSE);
        tableBigArray.putClientProperty("terminateEditOnFocusLost", Boolean.FALSE);
        panel1.add(tableBigArray, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 100), null, 0, false));
        tableSmallArray = new JTable();
        panel1.add(tableSmallArray, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 100), null, 0, false));
        buttonInputBigArrayFromFile = new JButton();
        buttonInputBigArrayFromFile.setText("Загрузить массив");
        panel1.add(buttonInputBigArrayFromFile, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCreateRandomBigArray = new JButton();
        buttonCreateRandomBigArray.setText("Заполнить случайными числами");
        panel1.add(buttonCreateRandomBigArray, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOutputBigArray = new JButton();
        buttonOutputBigArray.setText("Сохранить ");
        panel1.add(buttonOutputBigArray, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonInputSmallArrayFromFile = new JButton();
        buttonInputSmallArrayFromFile.setText("Загрузить массив");
        panel1.add(buttonInputSmallArrayFromFile, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCreateRandomSmallArray = new JButton();
        buttonCreateRandomSmallArray.setText("Заполнить случайными числами");
        panel1.add(buttonCreateRandomSmallArray, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOutputSmallArray = new JButton();
        buttonOutputSmallArray.setText("Сохранить ");
        panel1.add(buttonOutputSmallArray, new com.intellij.uiDesigner.core.GridConstraints(5, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Кол-во строк");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldRowSmallArray = new JTextField();
        panel1.add(textFieldRowSmallArray, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Кол-во столбцов");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldColumnSmallArray = new JTextField();
        panel1.add(textFieldColumnSmallArray, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonCreateModelSmallArray = new JButton();
        buttonCreateModelSmallArray.setText("Создать модель массива");
        panel1.add(buttonCreateModelSmallArray, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("МАЛЫЙ МАССИВ");
        panel1.add(label3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Кол-во строк");
        panelMain.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldRowBigArray = new JTextField();
        panelMain.add(textFieldRowBigArray, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Кол-во столбцов");
        panelMain.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldColumnBigArray = new JTextField();
        panelMain.add(textFieldColumnBigArray, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        buttonCreateModelBigArray = new JButton();
        buttonCreateModelBigArray.setText("Создать модель массива");
        panelMain.add(buttonCreateModelBigArray, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("БОЛЬШОЙ МАССИВ");
        panelMain.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonIfSmallArrayInBigArray = new JButton();
        buttonIfSmallArrayInBigArray.setText("Выполнить");
        panelMain.add(buttonIfSmallArrayInBigArray, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(508, 30), null, 0, false));
        textResult = new JLabel();
        textResult.setText("");
        panelMain.add(textResult, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonOutputResult = new JButton();
        buttonOutputResult.setText("Сохранить результат");
        panelMain.add(buttonOutputResult, new com.intellij.uiDesigner.core.GridConstraints(3, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
