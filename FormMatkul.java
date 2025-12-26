import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormMatkul extends JFrame {
    private DefaultTableModel model;

    public FormMatkul() {
        setTitle("Tambah Mata Kuliah");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel kodeLabel = new JLabel("Kode Matkul:");
        JLabel namaLabel = new JLabel("Nama Matkul:");
        JLabel sksLabel = new JLabel("SKS:");
        JLabel dosenLabel = new JLabel("NIDN Dosen:");

        JTextField kodeField = new JTextField();
        JTextField namaField = new JTextField();
        JTextField sksField = new JTextField();
        JTextField dosenField = new JTextField();

        JButton simpanButton = new JButton("Simpan");

        kodeLabel.setBounds(30, 30, 100, 25);
        namaLabel.setBounds(30, 70, 100, 25);
        sksLabel.setBounds(30, 110, 100, 25);
        dosenLabel.setBounds(30, 150, 100, 25);

        kodeField.setBounds(130, 30, 200, 25);
        namaField.setBounds(130, 70, 200, 25);
        sksField.setBounds(130, 110, 200, 25);
        dosenField.setBounds(130, 150, 200, 25);
        simpanButton.setBounds(130, 190, 100, 30);

        add(kodeLabel); add(kodeField);
        add(namaLabel); add(namaField);
        add(sksLabel); add(sksField);
        add(dosenLabel); add(dosenField);
        add(simpanButton);

        // Tabel display
        model = new DefaultTableModel();
        model.addColumn("Kode");
        model.addColumn("Nama Matkul");
        model.addColumn("SKS");
        model.addColumn("NIDN Dosen");
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 230, 420, 120);
        add(scrollPane);

        loadData();

        simpanButton.addActionListener(e -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO MataKuliah (kodeMatkul, namaMatkul, sks, nidn) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kodeField.getText());
                ps.setString(2, namaField.getText());
                ps.setInt(3, Integer.parseInt(sksField.getText()));
                ps.setString(4, dosenField.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Mata Kuliah berhasil ditambahkan!");
                loadData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM MataKuliah";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("kodeMatkul"),
                    rs.getString("namaMatkul"),
                    rs.getInt("sks"),
                    rs.getString("nidn")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error load data: " + ex.getMessage());
        }
    }
}
