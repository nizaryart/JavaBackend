package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepo {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Client> rowMapper = (rs, rowNum) -> {
        Client client = new Client();
        client.setId(rs.getInt("id"));
        client.setNom(rs.getString("nom"));
        client.setPrenom(rs.getString("prenom"));
        client.setCin(rs.getString("cin"));
        client.setPermis_conduire(rs.getString("permis_conduire"));
        client.setAdresse(rs.getString("adresse"));
        client.setVille(rs.getString("ville"));
        client.setTelephone(rs.getString("telephone"));
        client.setEmail(rs.getString("email"));
        client.setDate_naissance(rs.getString("date_naissance"));
        client.setNotes(rs.getString("notes"));
        return client;
    };

    @Autowired
    public ClientRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Client> findAll() {
        String sql = "SELECT * FROM clients";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Optional<Client> findById(int id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Client save(Client client) {
        if (client.getId() == 0) {
            // Insert new client
            String sql = "INSERT INTO clients (nom, prenom, cin, permis_conduire, adresse, ville, telephone, email, date_naissance, notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    client.getNom(),
                    client.getPrenom(),
                    client.getCin(),
                    client.getPermis_conduire(),
                    client.getAdresse(),
                    client.getVille(),
                    client.getTelephone(),
                    client.getEmail(),
                    client.getDate_naissance(),
                    client.getNotes());
        } else {
            // Update existing client
            String sql = "UPDATE clients SET nom = ?, prenom = ?, cin = ?, permis_conduire = ?, " +
                    "adresse = ?, ville = ?, telephone = ?, email = ?, date_naissance = ?, notes = ? " +
                    "WHERE id = ?";
            jdbcTemplate.update(sql,
                    client.getNom(),
                    client.getPrenom(),
                    client.getCin(),
                    client.getPermis_conduire(),
                    client.getAdresse(),
                    client.getVille(),
                    client.getTelephone(),
                    client.getEmail(),
                    client.getDate_naissance(),
                    client.getNotes(),
                    client.getId());
        }
        return client;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM clients WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Client> searchClients(String nom, String prenom, String cin) {
        String sql = "SELECT * FROM clients WHERE 1=1";

        if (nom != null && !nom.isEmpty()) {
            sql += " AND nom LIKE '%" + nom + "%'";
        }
        if (prenom != null && !prenom.isEmpty()) {
            sql += " AND prenom LIKE '%" + prenom + "%'";
        }
        if (cin != null && !cin.isEmpty()) {
            sql += " AND cin = '" + cin + "'";
        }

        return jdbcTemplate.query(sql, rowMapper);
    }

    public Client findByDrivingLicense(String permisConduire) {
        String sql = "SELECT * FROM clients WHERE permis_conduire = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, permisConduire);
        } catch (Exception e) {
            return null;
        }
    }
}