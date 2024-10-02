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

    private String title;

    private int year;

    @ManyToOne (fetch = FetchType.EAGER)
   private Author author;
}
