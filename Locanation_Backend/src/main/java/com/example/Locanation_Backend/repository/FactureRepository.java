package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Facture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class FactureRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FactureRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Facture> rowMapper = new RowMapper<Facture>() {
        @Override
        public Facture mapRow(ResultSet rs, int rowNum) throws SQLException {
            Facture facture = new Facture();
            facture.setId(rs.getInt("id"));
            facture.setContratId(rs.getInt("contrat_id"));
            facture.setNumeroFacture(rs.getString("numero_facture"));
            facture.setDateFacture(rs.getObject("date_facture", LocalDate.class));
            facture.setMontant(rs.getDouble("montant"));
            facture.setTva(rs.getDouble("tva"));
            facture.setStatut(rs.getString("statut"));
            facture.setModePaiement(rs.getString("mode_paiement"));
            facture.setDatePaiement(rs.getObject("date_paiement", LocalDate.class));
            facture.setNotes(rs.getString("notes"));
            return facture;
        }
    };

    public List<Facture> findAll() {
        String sql = "SELECT * FROM factures";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Facture> findById(int id) {
        String sql = "SELECT * FROM factures WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Facture save(Facture facture) {
        if (facture.getId() == 0) {
            // Insert new invoice
            String sql = "INSERT INTO factures (contrat_id, numero_facture, date_facture, montant, tva, statut, mode_paiement, date_paiement, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    facture.getContratId(),
                    facture.getNumeroFacture(),
                    facture.getDateFacture(),
                    facture.getMontant(),
                    facture.getTva(),
                    facture.getStatut(),
                    facture.getModePaiement(),
                    facture.getDatePaiement(),
                    facture.getNotes());
            // Retrieve the generated ID
            Integer newId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM factures", Integer.class);
            facture.setId(newId != null ? newId : 0);
        } else {
            // Update existing invoice
            String sql = "UPDATE factures SET contrat_id = ?, numero_facture = ?, date_facture = ?, montant = ?, tva = ?, statut = ?, mode_paiement = ?, date_paiement = ?, notes = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    facture.getContratId(),
                    facture.getNumeroFacture(),
                    facture.getDateFacture(),
                    facture.getMontant(),
                    facture.getTva(),
                    facture.getStatut(),
                    facture.getModePaiement(),
                    facture.getDatePaiement(),
                    facture.getNotes(),
                    facture.getId());
        }
        return facture;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM factures WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Facture findByContratId(int contratId) {
        String sql = "SELECT * FROM factures WHERE contrat_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, contratId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Facture> findByClientId(int clientId) {
        String sql = "SELECT f.* FROM factures f JOIN contrats c ON f.contrat_id = c.id WHERE c.client_id = ?";
        return jdbcTemplate.query(sql, rowMapper, clientId);
    }

    public List<Facture> findUnpaidFactures() {
        String sql = "SELECT * FROM factures WHERE statut IN ('UNPAID', 'PARTIAL')";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public int updatePaymentStatus(int id, String status, String paymentMethod, LocalDate paymentDate) {
        String sql = "UPDATE factures SET statut = ?, mode_paiement = ?, date_paiement = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, paymentMethod, paymentDate, id);
    }

    public List<Facture> searchFactures(LocalDate dateFrom, LocalDate dateTo, String statut) {
        StringBuilder sql = new StringBuilder("SELECT * FROM factures WHERE 1=1");
        if (dateFrom != null) {
            sql.append(" AND date_facture >= '").append(dateFrom).append("'");
        }
        if (dateTo != null) {
            sql.append(" AND date_facture <= '").append(dateTo).append("'");
        }
        if (statut != null && !statut.isEmpty()) {
            sql.append(" AND statut = '").append(statut).append("'");
        }
        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    public String generateInvoiceNumber() {
        String sql = "SELECT MAX(id) FROM factures";
        Integer maxId = jdbcTemplate.queryForObject(sql, Integer.class);
        return "FACT-" + (maxId != null ? maxId + 1 : 1000);
    }
}