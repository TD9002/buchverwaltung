package model;

public class Buch {
    private int nr;
    private Autor autor;
    private String titel;
    private BuchStatus status;

    public Buch(int nr, Autor autor, String titel, BuchStatus status) {
        this.nr = nr;
        this.autor = autor;
        this.titel = titel;
        this.status = status;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public BuchStatus getStatus() {
        return status;
    }

    public void setStatus(BuchStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Buch{" +
                "nr=" + nr +
                ", autor=" + autor +
                ", titel='" + titel + '\'' +
                ", status=" + status +
                '}';
    }
}
