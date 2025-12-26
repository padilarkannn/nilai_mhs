import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Manajemen Nilai Mahasiswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Identitas
        JMenu identitasMenu = new JMenu("Identitas");
        JMenuItem tambahDosen = new JMenuItem("Tambah Dosen");
        JMenuItem tambahMahasiswa = new JMenuItem("Tambah Mahasiswa");
        identitasMenu.add(tambahDosen);
        identitasMenu.add(tambahMahasiswa);

        // Menu Akademik
        JMenu akademikMenu = new JMenu("Akademik");
        JMenuItem tambahMatkul = new JMenuItem("Tambah Mata Kuliah");
        JMenuItem tambahNilai = new JMenuItem("Tambah Nilai");
        akademikMenu.add(tambahMatkul);
        akademikMenu.add(tambahNilai);

        // Menu File
        JMenu fileMenu = new JMenu("File");
        JMenuItem keluar = new JMenuItem("Keluar");
        keluar.addActionListener(e -> System.exit(0));
        fileMenu.add(keluar);

        // Tambahkan semua menu ke menu bar
        menuBar.add(fileMenu);
        menuBar.add(identitasMenu);
        menuBar.add(akademikMenu);
        setJMenuBar(menuBar);

        JLabel welcomeLabel = new JLabel("Selamat datang di Manajemen Nilai Mahasiswa", SwingConstants.CENTER);
        add(welcomeLabel);

        // Event handler untuk membuka form
        tambahDosen.addActionListener(e -> new FormDosen());
        tambahMahasiswa.addActionListener(e -> new FormMahasiswa());
        tambahMatkul.addActionListener(e -> new FormMatkul());
        tambahNilai.addActionListener(e -> new FormNilai());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
