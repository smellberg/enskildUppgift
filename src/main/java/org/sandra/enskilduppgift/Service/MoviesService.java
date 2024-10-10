package org.sandra.enskilduppgift.Service;

import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Author;
import org.sandra.enskilduppgift.Models.Movies;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.sandra.enskilduppgift.Repository.MoviesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MoviesRepository moviesRepository;
    private final AuthorRepository authorRepository;

    public List<Movies> getAllMovies(){
        return moviesRepository.findAll();
    }

    public Optional<Movies> getOneMovie(Long id){
        return moviesRepository.findById(id);
    }

    public Movies saveMovie(Movies movie){
        if (movie.getAuthor() != null && movie.getAuthor().getId() != 0) {
            Author author = authorRepository.findById(movie.getAuthor().getId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            movie.setAuthor(author);
        }
        return moviesRepository.save(movie);
    }

    public Movies patchMovie(Movies movie, Long id){
        Optional<Movies> currentMovieOpt = moviesRepository.findById(id);

        if (currentMovieOpt.isPresent()) {
            Movies currentMovie = currentMovieOpt.get();

            if (!movie.getTitle().equals(currentMovie.getTitle())) {
                currentMovie.setTitle(movie.getTitle());
            }

            if (movie.getYear() != currentMovie.getYear()) {
                currentMovie.setYear(movie.getYear());
            }

            if (movie.getAuthor() != null && movie.getAuthor().getId() != 0) {
                Author author = authorRepository.findById(movie.getAuthor().getId())
                        .orElseThrow(() -> new RuntimeException("Author not found"));
                currentMovie.setAuthor(author);
            }

            return moviesRepository.save(currentMovie);
        }

        throw new RuntimeException("Movie not found");
    }

    public Movies replaceMovie(Long id, Movies updatedMovie) {
        Optional<Movies> existingMovieOpt = moviesRepository.findById(id);
        if (existingMovieOpt.isPresent()) {
            Movies existingMovie = existingMovieOpt.get();

            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setYear(updatedMovie.getYear());

            if (updatedMovie.getAuthor() != null && updatedMovie.getAuthor().getId() != 0) {
                Author author = authorRepository.findById(updatedMovie.getAuthor().getId())
                        .orElseThrow(() -> new RuntimeException("Author not found"));
                existingMovie.setAuthor(author);
            }
            return moviesRepository.save(existingMovie);
        } else {
            throw new RuntimeException("Movie not found");
        }
    }

    public boolean removeMovie(Long id){
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
