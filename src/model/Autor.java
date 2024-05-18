package model;

public class Autor {
    private int nr;
    private String name;

    public Autor(int nr, String name) {
        this.nr = nr;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    @Override
    public String toString() {
        return name ;
    }
}
