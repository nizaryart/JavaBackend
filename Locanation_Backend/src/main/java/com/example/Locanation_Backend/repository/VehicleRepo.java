package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleRepo {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Vehicle> rowMapper = (rs, rowNum) -> {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(rs.getInt("id"));
        vehicle.setMarque(rs.getString("marque"));
        vehicle.setModele(rs.getString("modele"));
        vehicle.setMatricule(rs.getString("matricule"));
        vehicle.setPrix_journalier(rs.getDouble("prix_journalier"));
        vehicle.setPrix_par_km(rs.getDouble("prix_par_km"));
        vehicle.setCouleur(rs.getString("couleur"));
        vehicle.setCarburant(rs.getString("carburant"));
        vehicle.setBoite_vitesse(rs.getString("boite_vitesse"));
        vehicle.setDisponible(rs.getBoolean("disponible"));
        vehicle.setAnnee(rs.getInt("annee"));
        return vehicle;
    };

    @Autowired
    public VehicleRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Vehicle> findById(int id) {
        String sql = "SELECT * FROM vehicules WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Vehicle> findAll() {
        String sql = "SELECT * FROM vehicules";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Vehicle> findAvailableVehicles() {
        String sql = "SELECT * FROM vehicules WHERE disponible = true";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == 0) {
            // Insert new vehicle
            String sql = "INSERT INTO vehicules (marque, modele, matricule, prix_journalier, " +
                    "prix_par_km, couleur, carburant, boite_vitesse, disponible, annee) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    vehicle.getMarque(),
                    vehicle.getModele(),
                    vehicle.getMatricule(),
                    vehicle.getPrix_journalier(),
                    vehicle.getPrix_par_km(),
                    vehicle.getCouleur(),
                    vehicle.getCarburant(),
                    vehicle.getBoite_vitesse(),
                    vehicle.isDisponible(),
                    vehicle.getAnnee());
        } else {
            // Update existing vehicle
            String sql = "UPDATE vehicules SET marque = ?, modele = ?, matricule = ?, " +
                    "prix_journalier = ?, prix_par_km = ?, couleur = ?, carburant = ?, " +
                    "boite_vitesse = ?, disponible = ?, annee = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    vehicle.getMarque(),
                    vehicle.getModele(),
                    vehicle.getMatricule(),
                    vehicle.getPrix_journalier(),
                    vehicle.getPrix_par_km(),
                    vehicle.getCouleur(),
                    vehicle.getCarburant(),
                    vehicle.getBoite_vitesse(),
                    vehicle.isDisponible(),
                    vehicle.getAnnee(),
                    vehicle.getId());
        }
        return vehicle;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM vehicules WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Vehicle> searchVehicles(String marque, String modele, Double maxPrice) {
        String sql = "SELECT * FROM vehicules WHERE 1=1";

        if (marque != null && !marque.isEmpty()) {
            sql += " AND marque LIKE '%" + marque + "%'";
        }
        if (modele != null && !modele.isEmpty()) {
            sql += " AND modele LIKE '%" + modele + "%'";
        }
        if (maxPrice != null) {
            sql += " AND prix_journalier <= " + maxPrice;
        }

        return jdbcTemplate.query(sql, rowMapper);
    }
}