public class Mahasiswa {
    private String nim;
    private int id;
    private String kelas;

    public Mahasiswa(String nim, int id, String kelas) {
        this.nim = nim;
        this.id = id;
        this.kelas = kelas;
    }

    public String getNim() { return nim; }
    public int getId() { return id; }
    public String getKelas() { return kelas; }
}
