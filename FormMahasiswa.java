import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormMahasiswa extends JFrame {
    private DefaultTableModel model;

    public FormMahasiswa() {
        setTitle("Tambah Mahasiswa");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel nimLabel = new JLabel("NIM:");
        JLabel kelasLabel = new JLabel("Kelas:");

        JTextField namaField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField nimField = new JTextField();
        JTextField kelasField = new JTextField();

        JButton simpanButton = new JButton("Simpan");

        namaLabel.setBounds(30, 30, 100, 25);
        emailLabel.setBounds(30, 70, 100, 25);
        nimLabel.setBounds(30, 110, 100, 25);
        kelasLabel.setBounds(30, 150, 100, 25);

        namaField.setBounds(130, 30, 200, 25);
        emailField.setBounds(130, 70, 200, 25);
        nimField.setBounds(130, 110, 200, 25);
        kelasField.setBounds(130, 150, 200, 25);
        simpanButton.setBounds(130, 190, 100, 30);

        add(namaLabel); add(namaField);
        add(emailLabel); add(emailField);
        add(nimLabel); add(nimField);
        add(kelasLabel); add(kelasField);
        add(simpanButton);

        // Tabel display
        model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("Email");
        model.addColumn("Kelas");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 230, 420, 120);
        add(scrollPane);

        loadData();

        simpanButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sqlIdentitas = "INSERT INTO Identitas (nama, email) VALUES (?, ?)";
                PreparedStatement ps1 = conn.prepareStatement(sqlIdentitas, Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, namaField.getText());
                ps1.setString(2, emailField.getText());
                ps1.executeUpdate();
                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String sqlMhs = "INSERT INTO Mahasiswa (nim, id, kelas) VALUES (?, ?, ?)";
                    PreparedStatement ps2 = conn.prepareStatement(sqlMhs);
                    ps2.setString(1, nimField.getText());
                    ps2.setInt(2, id);
                    ps2.setString(3, kelasField.getText());
                    ps2.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Mahasiswa berhasil ditambahkan!");
                    loadData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Mahasiswa.nim, Identitas.nama, Identitas.email, Mahasiswa.kelas " +
                         "FROM Mahasiswa JOIN Identitas ON Mahasiswa.id = Identitas.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("email"),
                    rs.getString("kelas")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error load data: " + ex.getMessage());
        }
    }
}
