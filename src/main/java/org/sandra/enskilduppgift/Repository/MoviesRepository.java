package org.sandra.enskilduppgift.Repository;

import org.sandra.enskilduppgift.Models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

}
