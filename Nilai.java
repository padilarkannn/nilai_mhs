public class Nilai {
    private int id;
    private String nim;
    private String kodeMatkul;
    private double uts;
    private double uas;
    private double tugas;

    public Nilai(int id, String nim, String kodeMatkul, double uts, double uas, double tugas) {
        this.id = id;
        this.nim = nim;
        this.kodeMatkul = kodeMatkul;
        this.uts = uts;
        this.uas = uas;
        this.tugas = tugas;
    }

    public int getId() { return id; }
    public String getNim() { return nim; }
    public String getKodeMatkul() { return kodeMatkul; }
    public double getUts() { return uts; }
    public double getUas() { return uas; }
    public double getTugas() { return tugas; }
}
