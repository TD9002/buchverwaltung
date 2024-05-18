package dao;

import java.util.ArrayList;
import java.util.List;
import model.Autor;
import model.Buch;
import model.BuchStatus;

public class TempBuchDAO implements BuchDAO {
    private final List<Buch> buchList;
    private final List<Autor> autorList;

    public TempBuchDAO() {
        buchList = new ArrayList<>();
        autorList = new ArrayList<>();

        // Testdaten
        Autor a1 = new Autor(1, "Johann Wolfgang Goethe");
        Autor a2 = new Autor(2, "William Shakespeare");
        Autor a3 = new Autor(3, "J. K. Rowling");
        Autor a4 = new Autor(4, "George R. R. Martin");

        saveAutor(a1);
        saveAutor(a2);
        saveAutor(a3);
        saveAutor(a4);

        saveBuch( new Buch(1, a1, "Faust I", BuchStatus.READ) );
        saveBuch( new Buch(2, a1, "Faust II", BuchStatus.LIBRARY) );
        saveBuch( new Buch(3, a2, "Romeo und Julia", BuchStatus.MUST_HAVE) );
        saveBuch( new Buch(4, a2, "Viel Lärm um nichts", BuchStatus.READ) );
        saveBuch( new Buch(5, a2, "Der Kaufmann von Venedig", BuchStatus.MUST_HAVE) );
        saveBuch( new Buch(6, a3, "Harry Potter 1", BuchStatus.READ) );
        saveBuch( new Buch(7, a4, "Das Lied von Eis und Feuer", BuchStatus.LIBRARY) );
    }

    private static Autor cloneAutor(Autor autor) {
        if (autor == null) return null;
        return new Autor(autor.getNr(), autor.getName());
    }

    @Override
    public Autor getAutorByNr(int nr) {
        for (Autor autor : autorList) {
            if (autor.getNr() == nr)
                return cloneAutor(autor);
        }
        return null;
    }

    @Override
    public Autor getAutorByName(String autorName) {
        for (Autor autor : autorList) {
            if (autor.getName().equals(autorName))
                return cloneAutor(autor);
        }
        return null;
    }

    @Override
    public List<Autor> getAlleAutoren() {
        List<Autor> copyList = new ArrayList<>( autorList.size() );
        for (Autor autor : autorList) {
            copyList.add( cloneAutor(autor) );
        }
        return  copyList;
    }

    @Override
    public boolean saveAutor(Autor autor) {
        if (autor == null) return false;
        for (Autor a : autorList) {
            if (a.getNr() == autor.getNr())
                return false;
        }
        autorList.add(autor);
        return true;
    }

    @Override
    public boolean deleteAutor(int nr) {
        for (int i = 0; i < autorList.size(); i++) {
            if (autorList.get(i).getNr() == nr) {
                autorList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNextAutorNr() {
        int maxAutorNummer = 0;
        for (Autor autor : autorList) {
            if (autor.getNr() > maxAutorNummer) maxAutorNummer = autor.getNr();
        }
        return maxAutorNummer+1;
    }

    private static Buch cloneBuch(Buch buch) {
        if (buch == null) return null;
        return new Buch(buch.getNr(), cloneAutor(buch.getAutor()), buch.getTitel(), buch.getStatus());
    }

    @Override
    public Buch getBuchByNr(int nr) {
        for (Buch buch : buchList) {
            if (buch.getNr() == nr)
                return cloneBuch(buch);
        }
        return null;
    }

    @Override
    public List<Buch> getAlleBücher() {
        List<Buch> copyList = new ArrayList<>( buchList.size() );
        for (Buch buch : buchList) {
            copyList.add( cloneBuch(buch) );
        }
        return copyList;
    }

    @Override
    public boolean saveBuch(Buch buch) {
        if (buch == null) return false;

        for (Buch value : buchList) {
            if (value.getNr() == buch.getNr())
                return false;
        }
        buchList.add( cloneBuch(buch) );
        return true;
    }

    @Override
    public boolean deleteBuch(int nr) {
        for (int i = 0; i < buchList.size(); i++) {
            if (buchList.get(i).getNr() == nr) {
                buchList.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexAuslesen(int nr) {
        for (int i = 0; i <buchList.size() ; i++) {
            if (nr == buchList.get(i).getNr())
                return i;
        }
        return -1;
    }
}
