import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormDosen extends JFrame {
    private DefaultTableModel model;

    public FormDosen() {
        setTitle("Tambah Dosen");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel namaLabel = new JLabel("Nama:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel nidnLabel = new JLabel("NIDN:");

        JTextField namaField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField nidnField = new JTextField();

        JButton simpanButton = new JButton("Simpan");

        namaLabel.setBounds(30, 30, 100, 25);
        emailLabel.setBounds(30, 70, 100, 25);
        nidnLabel.setBounds(30, 110, 100, 25);

        namaField.setBounds(130, 30, 200, 25);
        emailField.setBounds(130, 70, 200, 25);
        nidnField.setBounds(130, 110, 200, 25);
        simpanButton.setBounds(130, 150, 100, 30);

        add(namaLabel); add(namaField);
        add(emailLabel); add(emailField);
        add(nidnLabel); add(nidnField);
        add(simpanButton);

        // Tabel untuk display
        model = new DefaultTableModel();
        model.addColumn("NIDN");
        model.addColumn("Nama");
        model.addColumn("Email");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 200, 420, 150);
        add(scrollPane);

        // Load data awal
        loadData();

        simpanButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Insert ke Identitas
                String sqlIdentitas = "INSERT INTO Identitas (nama, email) VALUES (?, ?)";
                PreparedStatement ps1 = conn.prepareStatement(sqlIdentitas, Statement.RETURN_GENERATED_KEYS);
                ps1.setString(1, namaField.getText());
                ps1.setString(2, emailField.getText());
                ps1.executeUpdate();

                ResultSet rs = ps1.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    // Insert ke Dosen
                    String sqlDosen = "INSERT INTO Dosen (nidn, id) VALUES (?, ?)";
                    PreparedStatement ps2 = conn.prepareStatement(sqlDosen);
                    ps2.setString(1, nidnField.getText());
                    ps2.setInt(2, id);
                    ps2.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Dosen berhasil ditambahkan!");
                    loadData(); // refresh tabel setelah insert
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0); // clear tabel
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT Dosen.nidn, Identitas.nama, Identitas.email " +
                         "FROM Dosen JOIN Identitas ON Dosen.id = Identitas.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nidn"),
                    rs.getString("nama"),
                    rs.getString("email")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error load data: " + ex.getMessage());
        }
    }
}
