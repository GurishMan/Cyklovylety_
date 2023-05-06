import java.time.LocalDate;

public class Cyklovylety {
    private String cil;
    private int delka;
    private LocalDate datum;

    public Cyklovylety(String cíl, int délka, LocalDate datum) {
        this.cil = cíl;
        this.delka = délka;
        this.datum = datum;
    }

    public String getCíl() {
        return cil;
    }

    public void setCíl(String cíl) {
        this.cil = cíl;
    }

    public int getDélka() {
        return delka;
    }

    public void setDélka(int délka) {
        this.delka = délka;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String toStrin(){
        return "," + cil + "," + delka + "," + datum + "\n";
    }
}

