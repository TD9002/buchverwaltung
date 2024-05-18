package view;

import model.Buch;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ListView extends JFrame {

   private List<Buch> buchList;
   private JTable table;
   private JTextField suchFeld;
    private TableRowSorter<TableModel> rowSorter;
    private DefaultTableModel tableModel;

    public ListView(List<Buch> buchList) {
        setTitle("Bücher");
        setSize(500, 320);
        setDefaultCloseOperation(2);
        this.buchList = buchList;
        addComponents();
        setLocation(500,0);
        setVisible(true);
    }
    private void addComponents() {
        this.setLayout(new BorderLayout());
        suchFeld = new JTextField();
        JPanel jPanel = new JPanel(new GridLayout(1,2));
        JLabel jLabel = new JLabel("Suche");
        jPanel.add(jLabel);
        jPanel.add(suchFeld);
        this.add(jPanel,BorderLayout.NORTH);


       tableModel = new DefaultTableModel(new Object[]{"BuchNr.", "Titel", "Autor"}, 0);
        for (Buch buch : buchList) {
            tableModel.addRow(new Object[]{buch.getNr(), buch.getTitel(), buch.getAutor()});
        }

        table = new JTable(tableModel);
        this.add(new JScrollPane(table), BorderLayout.CENTER);


        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);
        table.setDefaultEditor(Object.class, null);
        suchFeld.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }

            private void updateFilter() {
                String text = suchFeld.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

   /* private void addComponents() {

        this.setLayout(new BorderLayout());
        suchFeld = new JTextField();
        this.add(suchFeld,BorderLayout.NORTH);


        table = new JTable(buchList.size(),3);
        this.add(table, BorderLayout.SOUTH);
        JTableHeader header = table.getTableHeader();


        TableColumnModel tableColumnModel = header.getColumnModel();
        TableColumn tableColumnA = tableColumnModel.getColumn(0);
        tableColumnA.setHeaderValue("BuchNr.");
        TableColumn tableColumnB = tableColumnModel.getColumn(1);
        tableColumnB.setHeaderValue("Titel");
        TableColumn tableColumnC = tableColumnModel.getColumn(2);
        tableColumnC.setHeaderValue("Autor");


        table.setDefaultEditor(Object.class,null);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        for (int i = 0; i <buchList.size() ; i++) {
            table.setValueAt(buchList.get(i).getNr(),i,0);
            table.setValueAt(buchList.get(i).getTitel(),i,1);
            table.setValueAt(buchList.get(i).getAutor(),i,2);
        }
    }*/
    public void addAuswahlHandler(MouseListener mouseListener){
        table.addMouseListener(mouseListener);
    }
    public int getBuchNr(){
      return (Integer) table.getValueAt(table.getSelectedRow(),0);
    }

    public  void  setSelection(int i){
        table.setRowSelectionInterval(i,i);
    }
    public void updateBuchList(List<Buch> buchList) {
        this.buchList = buchList;
        tableModel.setRowCount(0);
        for (Buch buch : buchList) {
            tableModel.addRow(new Object[]{buch.getNr(), buch.getTitel(), buch.getAutor()});
        }
    }
}
