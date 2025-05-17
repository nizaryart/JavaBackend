package com.example.Locanation_Backend.repository;

import com.example.Locanation_Backend.model.Role;
import com.example.Locanation_Backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepo {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setMot_de_passe(rs.getString("mot_de_passe"));
        user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
        user.setNom(rs.getString("nom"));
        user.setEmail(rs.getString("email"));
        user.setTelephone(rs.getString("telephone"));
        user.setDate_creation(rs.getString("date_creation"));
        return user;
    };

    @Autowired
    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM utilisateurs";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User save(User user) {
        if (user.getId() == 0) {
            // Insert new user
            String sql = "INSERT INTO utilisateurs (login, mot_de_passe, role, nom, email, telephone, date_creation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    user.getLogin(),
                    user.getMot_de_passe(),
                    user.getRole().toString(),
                    user.getNom(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getDate_creation());
        } else {
            // Update existing user
            String sql = "UPDATE utilisateurs SET login = ?, mot_de_passe = ?, role = ?, " +
                    "nom = ?, email = ?, telephone = ?, date_creation = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    user.getLogin(),
                    user.getMot_de_passe(),
                    user.getRole().toString(),
                    user.getNom(),
                    user.getEmail(),
                    user.getTelephone(),
                    user.getDate_creation(),
                    user.getId());
        }
        return user;
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<User> searchUsers(String login, String role) {
        String sql = "SELECT * FROM utilisateurs WHERE 1=1";

        if (login != null && !login.isEmpty()) {
            sql += " AND login LIKE '%" + login + "%'";
        }
        if (role != null && !role.isEmpty()) {
            sql += " AND role = '" + role.toUpperCase() + "'";
        }

        return jdbcTemplate.query(sql, rowMapper);
    }
}