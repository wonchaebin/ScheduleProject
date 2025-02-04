package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule save(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO schedule (description, username, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, schedule.getDescription());
            ps.setString(2, schedule.getUsername());
            ps.setString(3, schedule.getPassword());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getCreatedAt()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getUpdatedAt()));
            return ps;
        }, keyHolder);

        return new Schedule(
                Objects.requireNonNull(keyHolder.getKey()).longValue(),
                schedule.getDescription(),
                schedule.getUsername(),
                null, // 비밀번호 제외
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(
                "SELECT id, description, username, created_at, updated_at FROM schedule",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getString("username"),
                        null, // 비밀번호 제외
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                )
        );
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        List<Schedule> schedules = jdbcTemplate.query(
                "SELECT id, description, username, created_at, updated_at FROM schedule WHERE id = ?",
                (rs, rowNum) -> new Schedule(
                        rs.getLong("id"),
                        rs.getString("description"),
                        rs.getString("username"),
                        null, // 비밀번호 제외
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ),
                id
        );
        return schedules.stream().findAny();
    }

    public boolean isPasswordCorrect(Long id, String password) {
        String dbPassword = jdbcTemplate.queryForObject(
                "SELECT password FROM schedule WHERE id = ?",
                String.class,
                id
        );
        return dbPassword != null && dbPassword.equals(password);
    }

    @Override
    public Schedule update(Schedule schedule) {
        if (!isPasswordCorrect(schedule.getId(), schedule.getPassword())) {
            throw new IllegalArgumentException("Invalid password for updating schedule.");
        }

        jdbcTemplate.update(
                "UPDATE schedule SET description = ?, username = ?, updated_at = ? WHERE id = ?",
                schedule.getDescription(),
                schedule.getUsername(),
                java.sql.Timestamp.valueOf(schedule.getUpdatedAt()),
                schedule.getId()
        );

        return findById(schedule.getId()).orElseThrow(() -> new RuntimeException("Schedule not found after update"));
    }

    @Override
    public void deleteById(Long id, String password) {
        if (!isPasswordCorrect(id, password)) {
            throw new IllegalArgumentException("Invalid password for deleting schedule.");
        }

        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}
