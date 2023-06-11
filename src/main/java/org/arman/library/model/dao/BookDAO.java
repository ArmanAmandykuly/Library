package org.arman.library.model.dao;

import org.arman.library.model.domain.Book;
import org.arman.library.model.domain.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.List;

@Component
public class BookDAO extends DAO<Book> {
    public BookDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Book.class);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year_of_publication, reader_id) VALUES(?, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYearOfPublication(), new SqlParameterValue(Types.BIGINT, book.getReaderId()));
    }

    public void update(long id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, year_of_publication=?, reader_id=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYearOfPublication(), new SqlParameterValue(Types.BIGINT, updatedBook.getReaderId()), id);
    }

    public List<Book> findForReaderWithId(Long readerId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE reader_id=?", new Object[]{new SqlParameterValue(Types.BIGINT, readerId)}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void release(long id) {
        jdbcTemplate.update("UPDATE Book SET reader_id=null WHERE id=?", id);
    }

    public void assign(long id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET reader_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
