package org.arman.library.model.dao;

import org.arman.library.model.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

public class DAO<T> {
    private Class<T> tClass;

    JdbcTemplate jdbcTemplate;

    public DAO(JdbcTemplate jdbcTemplate, Class<T> tClass) {
        this.jdbcTemplate = jdbcTemplate;
        this.tClass = tClass;
    }

    public List<T> index() {
        return jdbcTemplate.query("SELECT * FROM " + tClass.getSimpleName(), new BeanPropertyRowMapper<>(tClass));
    }

    public T show(long id) {
        return jdbcTemplate.query("SELECT * FROM " + tClass.getSimpleName() + " WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(tClass))
                .stream().findAny().orElse(null);
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM " + tClass.getSimpleName() + " WHERE id=?");
    }
}
