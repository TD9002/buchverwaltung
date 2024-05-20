package controller;

import dao.BuchDAO;
import dao.TempBuchDAO;
import model.Autor;
import model.Buch;
import model.BuchStatus;
import view.BuchView;
import view.BuchViewSwing;
import view.ListView;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Objects;

public class MainController {

    private static final String prüfungling = "M. Muster";
    private final BuchView mainView;
    private final BuchDAO buchDAO;
    private ListView listView;

    public MainController(BuchView mainView, BuchDAO buchDAO) {
        this.mainView = mainView;
        this.buchDAO = buchDAO;
        listView = null;

        mainView.addAbfragenHandler(this::performAbfrage);
        mainView.addSpeichernHandler(this::performSpeichern);
        mainView.addLöschenHandler(this::performLöschen);
        mainView.addLinksHandler(this::perfomLinksBlättern);
        mainView.addRechtsHandler(this::perfomRechtsBlättern);
        mainView.addListenHandler(this::perfomListeAnzeigen);

    }

    private void perfomListeAnzeigen(ActionEvent e) {
        List<Buch> listeBücher = buchDAO.getAlleBücher();
        if (listView == null  || !listView.isVisible()){
        listView = new ListView(listeBücher);}
        listView.addAuswahlHandler(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 ){
                int gewähltesBuch = listView.getBuchNr();
                int index = buchDAO.indexAuslesen(gewähltesBuch);
                showBuch(buchDAO.getBuchByNr(listeBücher.get(index).getNr()));
                    }
                }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void perfomLinksBlättern(ActionEvent e) {
        List<Buch> listeBücher = buchDAO.getAlleBücher();
        int indexAngezeigtesBuch = buchDAO.indexAuslesen(mainView.getBuchnummer());
        try {
            showBuch(buchDAO.getBuchByNr(listeBücher.get(indexAngezeigtesBuch-1).getNr()));
            mainView.setStatusComboBox(buchDAO.getBuchByNr(listeBücher.get(indexAngezeigtesBuch-1).getNr()).getStatus());
            if (listView !=null){
                listView.setSelection(indexAngezeigtesBuch-1);}

        }
        catch (IndexOutOfBoundsException exception){
            mainView.showErrorMessage("Keine weiteren Bücher vorhanden");
        }
    }

    private void perfomRechtsBlättern(ActionEvent e) {
        List<Buch> listeBücher = buchDAO.getAlleBücher();
        int indexAngezeigtesBuch = buchDAO.indexAuslesen(mainView.getBuchnummer());

        try {
            showBuch(buchDAO.getBuchByNr(listeBücher.get(indexAngezeigtesBuch+1).getNr()));
            mainView.setStatusComboBox(buchDAO.getBuchByNr(listeBücher.get(indexAngezeigtesBuch+1).getNr()).getStatus());
            if (listView !=null){
            listView.setSelection(indexAngezeigtesBuch+1);}


        }
        catch (IndexOutOfBoundsException exception){
            mainView.showErrorMessage("Keine weiteren Bücher vorhanden");
        }
    }

    public void performAbfrage(ActionEvent e) {
        int nr = mainView.getBuchnummer();
        Buch b = buchDAO.getBuchByNr(nr);
        if (b != null) {
            showBuch(b);
        }
        else {
            mainView.showErrorMessage("Fehler beim Laden des Buches");
            clearBuch();
        }
    }

    public void performLöschen(ActionEvent e) {
        int buchnummer = mainView.getBuchnummer();
        int index = buchDAO.indexAuslesen(mainView.getBuchnummer())-1;


        //unnötig hier über schleife zu gehen
        for (var book : buchDAO.getAlleBücher()) {
            if (buchnummer == book.getNr()) {
                if (mainView.confirmDialog("Löschen?")) {
                    buchDAO.deleteBuch(book.getNr());
                    mainView.showInfoMessage("Buch wurde gelöscht");
                    if (listView != null) {
                        updateListView();
                    }
                    if (index < 0){
                        showBuch(buchDAO.getBuchByNr(buchDAO.getAlleBücher().get(0).getNr()));
                        clearBuch();

                        if (listView != null)
                        listView.setSelection(0);
                    }
                    else {
                        showBuch(buchDAO.getBuchByNr(buchDAO.getAlleBücher().get(index).getNr()));
                        clearBuch();

                        if (listView != null)
                        listView.setSelection(index);
                    }

                }
            }
        }
    }

    public void performSpeichern(ActionEvent e) {
        String autorName = mainView.getAutor();
        String titel = mainView.getTitel();
        BuchStatus status = mainView.getStatus();
        int nr = mainView.getBuchnummer();

        if (autorName.equals("") || titel.equals("") || nr == 0) {
            mainView.showErrorMessage("Bitte füllen Sie alle Felder (korrekt) aus");
            return;
        }

        if (nr > 0 && buchDAO.getBuchByNr(nr) != null) {
            if ( !mainView.confirmDialog("Buch existiert, überschreiben?") )
                return;
            buchDAO.deleteBuch(nr);
        }

        Autor autor = buchDAO.getAutorByName(autorName);
        if (autor == null) {
            autor = new Autor(buchDAO.getNextAutorNr(), autorName);
            if ( !buchDAO.saveAutor(autor) ) {
                mainView.showErrorMessage("Konnte den Autor nicht speichern :(");
                return;
            }
        }

        if ( ! buchDAO.saveBuch(new Buch(nr, autor, titel, status)) ) {
            mainView.showErrorMessage("Fehler beim Speichern des Buches");
        }
        else{
            mainView.showInfoMessage("Buch erfolgreich hinzugefügt");
        if (listView != null) {
            updateListView();
            listView.setSelection(buchDAO.indexAuslesen(nr));

        }
    }}

    private void clearBuch() {
        mainView.showAutor( "" );
        mainView.showTitel( "" );
        mainView.showStatus( BuchStatus.MUST_HAVE );
        mainView.showNummer(0);
    }

    private void showBuch(Buch b) {
        mainView.showNummer( b.getNr() );
        mainView.showAutor( b.getAutor().getName() );
        mainView.showTitel( b.getTitel() );
        mainView.showStatus( b.getStatus() );
        mainView.setStatusComboBox(b.getStatus());
    }
    public void updateListView(){
        listView.updateBuchList(buchDAO.getAlleBücher());
    }


    public static void main(String[] args) {
        new MainController(new BuchViewSwing(prüfungling), new TempBuchDAO());
    }
}
