public class MataKuliah {
    private String kodeMatkul;
    private String namaMatkul;
    private int sks;
    private String nidn;

    public MataKuliah(String kodeMatkul, String namaMatkul, int sks, String nidn) {
        this.kodeMatkul = kodeMatkul;
        this.namaMatkul = namaMatkul;
        this.sks = sks;
        this.nidn = nidn;
    }

    public String getKodeMatkul() { return kodeMatkul; }
    public String getNamaMatkul() { return namaMatkul; }
    public int getSks() { return sks; }
    public String getNidn() { return nidn; }
}
