package org.sandra.enskilduppgift.Models;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Movies {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private String title;

    private int year;

    @ManyToOne (fetch = FetchType.EAGER)
   private Author author;
}
