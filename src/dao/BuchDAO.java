package dao;

import model.Autor;
import model.Buch;

import java.util.List;

public interface BuchDAO {

    Autor getAutorByNr(int nr);
    Autor getAutorByName(String autorName);
    List<Autor> getAlleAutoren();
    boolean saveAutor(Autor autor);
    boolean deleteAutor(int nr);
    int getNextAutorNr();

    Buch getBuchByNr(int nr);
    List<Buch> getAlleBücher();
    boolean saveBuch(Buch buch);
    boolean deleteBuch(int nr);


}
