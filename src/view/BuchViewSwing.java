package view;

import dao.BuchDAO;
import model.BuchStatus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class BuchViewSwing extends JFrame implements BuchView {

    private final String prüfling;
    private JTextField buchNrField, buchAutorField, buchTitelField;
    private JButton abfrageButton, speichernButton, löschButton, links, rechts, alleAnzeigen;
    private JRadioButton buchKaufenRB, buchInBibBB, buchGelesenBB;
    private ButtonGroup buttonGroup;
    private JComboBox jComboBox;
    public BuchViewSwing(String prüfling) {
        setTitle("Bücherverwaltung (Prüfungsversion)");
        this.prüfling = prüfling;
        setSize(500, 320);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        setLayout( new BorderLayout() );



        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // TOP
        topPanel.add( new JLabel("<" + prüfling + ">") );

        // BOTTOM
        abfrageButton =  new JButton("Abfragen");
        speichernButton = new JButton("Speichern");
        löschButton = new JButton("löschen");
        links = new JButton("<-");
        rechts = new JButton("->");
        alleAnzeigen = new JButton("Liste");
        bottomPanel.add(links);
        bottomPanel.add( abfrageButton );
        bottomPanel.add( speichernButton );
        bottomPanel.add(löschButton);
        bottomPanel.add(alleAnzeigen);
        bottomPanel.add(rechts);

        // CENTER
        buchNrField = new JTextField();
        buchAutorField = new JTextField();
        buchTitelField = new JTextField();

        jComboBox = new JComboBox<>(BuchStatus.values());

        centerPanel.setLayout(new GridLayout(5,2) );
        centerPanel.setBorder(new EmptyBorder(5,5,5,5));
        centerPanel.add( new JLabel("Buchnummer:") );
        centerPanel.add( buchNrField );
        centerPanel.add( new JLabel("Autor:") );
        centerPanel.add( buchAutorField );
        centerPanel.add( new JLabel("Titel:") );
        centerPanel.add( buchTitelField );
        centerPanel.add( new JLabel("Status:") );

        JPanel buchStatusPanel = new JPanel( new GridLayout(3,1) );
        buttonGroup = new ButtonGroup();
        buchKaufenRB = new JRadioButton("muss ich haben");
        buchInBibBB = new JRadioButton("in Bibliothek");
        buchGelesenBB = new JRadioButton("gelesen");
        buttonGroup.add(buchKaufenRB);
        buttonGroup.add(buchInBibBB);
        buttonGroup.add(buchGelesenBB);
        buchStatusPanel.add(buchKaufenRB);
        buchStatusPanel.add(buchInBibBB);
        buchStatusPanel.add(buchGelesenBB);
        centerPanel.add( buchStatusPanel );
        centerPanel.add(jComboBox);

    }

    @Override
    public void showNummer(int nr) {
        if (nr == 0){
            buchNrField.setText("");
        }
        else {
        buchNrField.setText( String.valueOf(nr) );
    }}


    @Override
    public int getBuchnummer() {
        int nr = 0;
        try {
            nr = Integer.parseInt( buchNrField.getText() );
            if (nr <= 0){
                nr = 0;
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(this,
                    "Keine gültige Buchnummer",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
        return nr;
    }

    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean confirmDialog(String message) {
        return JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, message, "Frage", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void showAutor(String autor) {
        buchAutorField.setText( autor );
    }

    @Override
    public String getAutor() {
        return buchAutorField.getText();
    }

    @Override
    public void showTitel(String titel) {
        buchTitelField.setText( titel );
    }

    @Override
    public String getTitel() {
        return buchTitelField.getText();
    }

    @Override
    public void showStatus(BuchStatus status) {
        if (status == BuchStatus.READ){
            buchGelesenBB.setSelected(true);
        }
        else if (status == BuchStatus.MUST_HAVE) {
            buchKaufenRB.setSelected(true);
        }
        else
            buchInBibBB.setSelected(true);
    }

    @Override
    public void setStatusComboBox(BuchStatus status) {
        jComboBox.setSelectedItem(status);
    }

    @Override
    public BuchStatus getStatus() {
        if (buchGelesenBB.isSelected()){
            return BuchStatus.READ;
        }
        else if (buchInBibBB.isSelected()) {
            return BuchStatus.LIBRARY;
        }
        else
            return BuchStatus.MUST_HAVE;
    }

    @Override
    public BuchStatus getStatusComboBox(){
        return (BuchStatus) jComboBox.getSelectedItem();
    }

    @Override
    public void addAbfragenHandler(ActionListener listener) {
        abfrageButton.addActionListener(listener);
    }

    @Override
    public void addSpeichernHandler(ActionListener listener) {
        speichernButton.addActionListener(listener);
    }

    @Override
    public void addLöschenHandler(ActionListener listener) {
        löschButton.addActionListener(listener);
    }

    @Override
    public void addLinksHandler(ActionListener listener) {
        links.addActionListener(listener);
    }

    @Override
    public void addRechtsHandler(ActionListener listener) {
        rechts.addActionListener(listener);
    }
    @Override
    public void addListenHandler(ActionListener listener) {
        alleAnzeigen.addActionListener(listener);
    }



}
