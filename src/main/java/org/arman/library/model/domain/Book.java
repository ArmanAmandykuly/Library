package org.arman.library.model.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class Book {
    private long id;

    @NotEmpty(message = "The title mustn't be empty")
    @Size(min = 2, max = 100, message = "The title's length must be between 2 and 100 symbols")
    private String title;

    @NotEmpty(message = "The author's name mustn't be empty")
    @Size(min = 2, max = 100, message = "The author's name must be between 2 and 100 symbols")
    private String author;

    @Min(value = 1500, message = "The year of production must be larger than 1500")
    private String yearOfPublication;

    private Long readerId;
}
