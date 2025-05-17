package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Paiement;
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
public class PaiementRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaiementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Paiement> rowMapper = new RowMapper<Paiement>() {
        @Override
        public Paiement mapRow(ResultSet rs, int rowNum) throws SQLException {
            Paiement paiement = new Paiement();
            paiement.setId(rs.getInt("id"));
            paiement.setFactureId(rs.getInt("facture_id"));
            paiement.setMontant(rs.getDouble("montant"));
            paiement.setDatePaiement(rs.getObject("date_paiement", LocalDate.class));
            paiement.setModePaiement(rs.getString("mode_paiement"));
            paiement.setReference(rs.getString("reference"));
            paiement.setStatut(rs.getString("statut"));
            paiement.setNotes(rs.getString("notes"));
            return paiement;
        }
    };

    // Basic CRUD Operations
    public List<Paiement> findAll() {
        String sql = "SELECT * FROM paiements";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Paiement> findById(int id) {
        String sql = "SELECT * FROM paiements WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Paiement save(Paiement paiement) {
        if (paiement.getId() == 0) {
            // Insert new payment
            String sql = "INSERT INTO paiements (facture_id, montant, date_paiement, " +
                    "mode_paiement, reference, statut, notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    paiement.getFactureId(),
                    paiement.getMontant(),
                    paiement.getDatePaiement(),
                    paiement.getModePaiement(),
                    paiement.getReference(),
                    paiement.getStatut(),
                    paiement.getNotes());

            // Retrieve the generated ID
            Integer newId = jdbcTemplate.queryForObject(
                    "SELECT MAX(id) FROM paiements", Integer.class);
            paiement.setId(newId != null ? newId : 0);
        } else {
            // Update existing payment
            String sql = "UPDATE paiements SET facture_id = ?, montant = ?, date_paiement = ?, " +
                    "mode_paiement = ?, reference = ?, statut = ?, notes = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql,
                    paiement.getFactureId(),
                    paiement.getMontant(),
                    paiement.getDatePaiement(),
                    paiement.getModePaiement(),
                    paiement.getReference(),
                    paiement.getStatut(),
                    paiement.getNotes(),
                    paiement.getId());
        }
        return paiement;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM paiements WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Specialized Queries
    public List<Paiement> findByFactureId(int factureId) {
        String sql = "SELECT * FROM paiements WHERE facture_id = ? ORDER BY date_paiement DESC";
        return jdbcTemplate.query(sql, rowMapper, factureId);
    }

    public List<Paiement> findByClientId(int clientId) {
        String sql = "SELECT p.* FROM paiements p " +
                "JOIN factures f ON p.facture_id = f.id " +
                "JOIN contrats c ON f.contrat_id = c.id " +
                "WHERE c.client_id = ? " +
                "ORDER BY p.date_paiement DESC";
        return jdbcTemplate.query(sql, rowMapper, clientId);
    }

    public List<Paiement> findRecentPaiements(int count) {
        String sql = "SELECT TOP " + count + " * FROM paiements ORDER BY date_paiement DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Payment Processing
    public int validatePaiement(int id) {
        String sql = "UPDATE paiements SET statut = 'COMPLETED' WHERE id = ? AND statut = 'PENDING'";
        return jdbcTemplate.update(sql, id);
    }

    // Search and Reporting
    public List<Paiement> searchPaiements(String modePaiement, Double minAmount, Double maxAmount) {
        StringBuilder sql = new StringBuilder("SELECT * FROM paiements WHERE 1=1");

        if (modePaiement != null && !modePaiement.isEmpty()) {
            sql.append(" AND mode_paiement = '").append(modePaiement).append("'");
        }
        if (minAmount != null) {
            sql.append(" AND montant >= ").append(minAmount);
        }
        if (maxAmount != null) {
            sql.append(" AND montant <= ").append(maxAmount);
        }
        sql.append(" ORDER BY date_paiement DESC");

        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    public double calculateTotalPaiements(LocalDate startDate, LocalDate endDate) {
        StringBuilder sql = new StringBuilder(
                "SELECT SUM(montant) FROM paiements WHERE statut = 'COMPLETED'");

        if (startDate != null) {
            sql.append(" AND date_paiement >= '").append(startDate).append("'");
        }
        if (endDate != null) {
            sql.append(" AND date_paiement <= '").append(endDate).append("'");
        }

        Double total = jdbcTemplate.queryForObject(sql.toString(), Double.class);
        return total != null ? total : 0.0;
    }

    public double getFactureBalance(int factureId) {
        String sql = "SELECT f.montant - COALESCE(SUM(p.montant), 0) " +
                "FROM factures f LEFT JOIN paiements p ON f.id = p.facture_id " +
                "WHERE f.id = ? AND p.statut = 'COMPLETED' " +
                "GROUP BY f.montant";
        Double balance = jdbcTemplate.queryForObject(sql, Double.class, factureId);
        return balance != null ? balance : 0.0;
    }
}