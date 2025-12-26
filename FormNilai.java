import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormNilai extends JFrame {
    private DefaultTableModel model;

    public FormNilai() {
        setTitle("Tambah Nilai");
        setSize(550, 500);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel nimLabel = new JLabel("NIM:");
        JLabel matkulLabel = new JLabel("Kode Matkul:");
        JLabel utsLabel = new JLabel("UTS:");
        JLabel uasLabel = new JLabel("UAS:");
        JLabel tugasLabel = new JLabel("Tugas:");

        JTextField nimField = new JTextField();
        JTextField matkulField = new JTextField();
        JTextField utsField = new JTextField();
        JTextField uasField = new JTextField();
        JTextField tugasField = new JTextField();

        JButton simpanButton = new JButton("Simpan");

        nimLabel.setBounds(30, 30, 100, 25);
        matkulLabel.setBounds(30, 70, 100, 25);
        utsLabel.setBounds(30, 110, 100, 25);
        uasLabel.setBounds(30, 150, 100, 25);
        tugasLabel.setBounds(30, 190, 100, 25);

        nimField.setBounds(130, 30, 200, 25);
        matkulField.setBounds(130, 70, 200, 25);
        utsField.setBounds(130, 110, 200, 25);
        uasField.setBounds(130, 150, 200, 25);
        tugasField.setBounds(130, 190, 200, 25);
        simpanButton.setBounds(130, 230, 100, 30);

        add(nimLabel); add(nimField);
        add(matkulLabel); add(matkulField);
        add(utsLabel); add(utsField);
        add(uasLabel); add(uasField);
        add(tugasLabel); add(tugasField);
        add(simpanButton);

        // Tabel display
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIM");
        model.addColumn("Kode Matkul");
        model.addColumn("UTS");
        model.addColumn("UAS");
        model.addColumn("Tugas");
        model.addColumn("Status"); // kolom tambahan untuk Lulus/Tidak Lulus
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 280, 480, 150);
        add(scrollPane);

        loadData();

        simpanButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                double uts = Double.parseDouble(utsField.getText());
                double uas = Double.parseDouble(uasField.getText());
                double tugas = Double.parseDouble(tugasField.getText());
                double nilaiAkhir = (uts + uas + tugas) / 3;
                String status = (nilaiAkhir >= 60) ? "Lulus" : "Tidak Lulus";

                String sql = "INSERT INTO Nilai (nim, kodeMatkul, uts, uas, tugas) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nimField.getText());
                ps.setString(2, matkulField.getText());
                ps.setDouble(3, uts);
                ps.setDouble(4, uas);
                ps.setDouble(5, tugas);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Nilai berhasil ditambahkan! Status: " + status);
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Nilai";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                double uts = rs.getDouble("uts");
                double uas = rs.getDouble("uas");
                double tugas = rs.getDouble("tugas");
                double nilaiAkhir = (uts + uas + tugas) / 3;
                String status = (nilaiAkhir >= 60) ? "Lulus" : "Tidak Lulus";

                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nim"),
                    rs.getString("kodeMatkul"),
                    uts,
                    uas,
                    tugas,
                    status
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error load data: " + ex.getMessage());
        }
    }
}
