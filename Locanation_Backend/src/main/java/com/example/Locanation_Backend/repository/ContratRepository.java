package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Contrat;
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
public class ContratRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContratRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Contrat> rowMapper = new RowMapper<Contrat>() {
        @Override
        public Contrat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contrat contrat = new Contrat();
            contrat.setId(rs.getInt("id"));
            contrat.setClientId(rs.getInt("client_id"));
            contrat.setVehiculeId(rs.getInt("vehicule_id"));
            contrat.setUtilisateurId(rs.getInt("utilisateur_id"));
            contrat.setDateDebut(rs.getObject("date_debut", LocalDate.class));
            contrat.setDateFin(rs.getObject("date_fin", LocalDate.class));
            contrat.setDateRetour(rs.getObject("date_retour", LocalDate.class));
            contrat.setKilometrageDepart(rs.getInt("kilometrage_depart"));
            contrat.setKilometrageRetour(rs.getInt("kilometrage_retour"));
            contrat.setMontantTotal(rs.getDouble("montant_total"));
            contrat.setStatut(rs.getString("statut"));
            contrat.setDateCreation(rs.getObject("date_creation", LocalDate.class));
            return contrat;
        }
    };

    public List<Contrat> findAll() {
        String sql = "SELECT * FROM contrats";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Contrat> findById(int id) {
        String sql = "SELECT * FROM contrats WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Contrat save(Contrat contrat) {
        if (contrat.getId() == 0) {
            // Insert new contract
            String sql = "INSERT INTO contrats (client_id, vehicule_id, utilisateur_id, date_debut, date_fin, " +
                    "kilometrage_depart, montant_total, statut, date_creation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    contrat.getClientId(),
                    contrat.getVehiculeId(),
                    contrat.getUtilisateurId(),
                    contrat.getDateDebut(),
                    contrat.getDateFin(),
                    contrat.getKilometrageDepart(),
                    contrat.getMontantTotal(),
                    contrat.getStatut(),
                    contrat.getDateCreation());
            // Retrieve the generated ID
            Integer newId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM contrats", Integer.class);
            contrat.setId(newId != null ? newId : 0);
        } else {
            // Update existing contract
            String sql = "UPDATE contrats SET client_id = ?, vehicule_id = ?, utilisateur_id = ?, date_debut = ?, " +
                    "date_fin = ?, date_retour = ?, kilometrage_depart = ?, kilometrage_retour = ?, " +
                    "montant_total = ?, statut = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    contrat.getClientId(),
                    contrat.getVehiculeId(),
                    contrat.getUtilisateurId(),
                    contrat.getDateDebut(),
                    contrat.getDateFin(),
                    contrat.getDateRetour(),
                    contrat.getKilometrageDepart(),
                    contrat.getKilometrageRetour(),
                    contrat.getMontantTotal(),
                    contrat.getStatut(),
                    contrat.getId());
        }
        return contrat;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM contrats WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Contrat> findByClientId(int clientId) {
        String sql = "SELECT * FROM contrats WHERE client_id = ?";
        return jdbcTemplate.query(sql, rowMapper, clientId);
    }

    public List<Contrat> findByVehiculeId(int vehiculeId) {
        String sql = "SELECT * FROM contrats WHERE vehicule_id = ?";
        return jdbcTemplate.query(sql, rowMapper, vehiculeId);
    }

    public List<Contrat> findActiveContrats() {
        String sql = "SELECT * FROM contrats WHERE statut = 'ACTIVE'";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Contrat> searchContrats(String dateDebut, String dateFin, String statut) {
        StringBuilder sql = new StringBuilder("SELECT * FROM contrats WHERE 1=1");
        if (dateDebut != null) {
            sql.append(" AND date_debut >= '").append(dateDebut).append("'");
        }
        if (dateFin != null) {
            sql.append(" AND date_fin <= '").append(dateFin).append("'");
        }
        if (statut != null && !statut.isEmpty()) {
            sql.append(" AND statut = '").append(statut).append("'");
        }
        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    public int countActiveContratsByVehicule(int vehiculeId) {
        String sql = "SELECT COUNT(*) FROM contrats WHERE vehiculeId = ? AND statut = 'ACTIVE'";
        return jdbcTemplate.queryForObject(sql, Integer.class, vehiculeId);
    }
}