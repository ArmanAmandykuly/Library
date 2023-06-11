package org.arman.library.model.dao;

import org.arman.library.model.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO extends DAO<Person>{

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Person.class);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (full_name, year_of_birth) VALUES(?, ?)", person.getFullName(), person.getYearOfBirth());
    }

    public Person show(Long id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(long id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?",
                updatedPerson.getFullName(), updatedPerson.getYearOfBirth(), id);
    }

    public Optional<Person> show(String fullName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[] {fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
