package com.jsltd.cruddemo.dao;

import com.jsltd.cruddemo.entity.Circle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CircleRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CircleRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Circle> getCircles(int id) {
        String sql = "SELECT " +
                "circles.id AS id, " +
                "circles.name AS name, " +
                "circles.parent_id AS parentId, " +
                "circles.type AS type, " +
                "(SELECT " +
                "  JSON_AGG( " +
                "    JSON_BUILD_OBJECT( " +
                "      'id', circle_users.id, " +
                "      'role', circle_users.circle_role, " +
                "      'capacity', circle_users.capacity, " +
                "      'employee_id', circle_users.employee_id " +
                "    ) " +
                "  ) " +
                "FROM " +
                "  circle_users " +
                "WHERE " +
                "  circle_users.circle_id = circles.id " +
                ") AS users, " +
                "(SELECT " +
                "  JSON_AGG( " +
                "    JSON_BUILD_OBJECT( " +
                "      'id', circle_child.id, " +
                "      'name', circle_child.name, " +
                "      'parentId', circle_child.parent_id, " +
                "      'type', circle_child.type, " +
                "      'users', ( " +
                "        SELECT " +
                "          JSON_AGG( " +
                "            JSON_BUILD_OBJECT( " +
                "              'id', circle_child_users.id, " +
                "              'role', circle_child_users.circle_role, " +
                "              'capacity', circle_child_users.capacity, " +
                "              'circle_id', circle_child_users.employee_id " +
                "            ) " +
                "        ) " +
                "        FROM " +
                "          circle_users AS circle_child_users " +
                "        WHERE " +
                "          circle_child_users.circle_id = circle_child.id " +
                "      ) " +
                "      ) " +
                "    ) " +
                "FROM " +
                "  circles AS circle_child " +
                "WHERE " +
                "  circle_child.parent_id = circles.id " +
                ") AS childCircle " +
                "FROM circles " +
                "WHERE circles.id >= :id";

        SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        return jdbcTemplate.query(sql, parameters, new CircleMapper());
    }

    private static class CircleMapper implements RowMapper<Circle> {
        @Override
        public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
            Circle circle = new Circle();
            circle.setId(rs.getInt("id"));
            circle.setName(rs.getString("name"));
            circle.setParent_id(rs.getInt("parentId"));
            circle.setType(rs.getString("type"));
            String usersJson = rs.getString("users");
            String childCircleJson = rs.getString("childCircle");

            return circle;
        }
    }
}