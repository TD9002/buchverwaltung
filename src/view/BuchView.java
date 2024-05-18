package view;

import model.BuchStatus;

import java.awt.*;
import java.awt.event.ActionListener;

public interface BuchView {

    void showNummer(int nr);
    int getBuchnummer();

    void showAutor(String autor);
    String getAutor();

    void showTitel(String titel);
    String getTitel();

    void showStatus(BuchStatus status);
    BuchStatus getStatus();
    BuchStatus getStatusComboBox();

    void setStatusComboBox(BuchStatus status);

    void addAbfragenHandler(ActionListener listener);
    void addSpeichernHandler(ActionListener listener);
    void addLöschenHandler(ActionListener listener);
    void addLinksHandler(ActionListener listener);
    void addRechtsHandler(ActionListener listener);
    void addListenHandler(ActionListener listener);



    void showInfoMessage(String message);
    void showErrorMessage(String message);
    boolean confirmDialog(String message);

}
