package com.daduboard.api.core;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Created by swzhou on 15/6/3.
 */
@Entity
@Table(name = "gambling")
public class Gambling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Length(min = 2, max = 255)
    @Column(name = "title", nullable = false)
    private final String title;

    @Length(min = 2, max = 1024)
    @Column(name = "description", nullable = false)
    private final String description;

    public Gambling() {
        this.id = 0;
        this.title = null;
        this.description = null;
    }

    public Gambling(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gambling gambling = (Gambling) o;

        if (id != gambling.id) return false;
        if (!description.equals(gambling.description)) return false;
        if (!title.equals(gambling.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
