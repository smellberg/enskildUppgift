package org.sandra.enskilduppgift.Controller;

import jakarta.persistence.PostUpdate;
import jakarta.websocket.ClientEndpoint;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Movies;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.sandra.enskilduppgift.Repository.MoviesRepository;
import org.sandra.enskilduppgift.Service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor

public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping("")
    public ResponseEntity<List<Movies>> getAllMovies(){
        List <Movies> movies = moviesService.getAllMovies();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneMovie(@PathVariable long id){
        Optional<Movies> movie = moviesService.getOneMovie(id);

        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.status(404).body("Movie with ID " + id + " not found");
        }
    }

    @PostMapping("")
    public ResponseEntity<Movies> createNewMovie(@RequestBody Movies newMovie){
       Movies movie = moviesService.saveMovie(newMovie);
       return ResponseEntity.ok(movie);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Movies> updateOneMovie(@PathVariable Long id,
                                                 @RequestBody Movies newMovie){
        Movies patchedMovie = moviesService.patchMovie(newMovie, id);
        return ResponseEntity.ok(patchedMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movies> replaceMovie(@PathVariable Long id, @RequestBody Movies updatedMovie) {
        try {
            Movies savedMovie = moviesService.replaceMovie(id, updatedMovie);
            return ResponseEntity.ok(savedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Returnera 404 om filmen inte finns
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneMovie(@PathVariable long id){
        moviesService.removeMovie(id);

        return ResponseEntity.ok("Removed Successfully");
    }
}
