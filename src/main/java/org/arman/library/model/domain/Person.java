package org.arman.library.model.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class Person {

    private long id;

    @NotEmpty(message = "Name mustn't be empty")
    @Size(min= 2, max = 100, message = "Name's size must be between 2 and 100")
    private String fullName;

    @Min(value=1900, message = "The year of birth must be larger than 1900")
    private String yearOfBirth;
}
