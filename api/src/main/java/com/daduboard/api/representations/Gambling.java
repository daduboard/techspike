package com.daduboard.api.representations;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by swzhou on 15/6/3.
 */
public class Gambling {
    private final int id;

    @NotBlank
    @Length(min = 2, max = 255)
    private final String title;

    @NotBlank
    @Length(min = 2, max = 1024)
    private final String description;

    public Gambling() {
        this.id = 0;
        this.title = null;
        this.description = null;
    }

    public Gambling(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
