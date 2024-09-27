package org.sandra.enskilduppgift.Service;

import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Movies;
import org.sandra.enskilduppgift.Repository.MoviesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesService {

    private final MoviesRepository moviesRepository;

    public List<Movies> getAllMovies(){
        return moviesRepository.findAll();
    }

    public Optional<Movies> getOneMovie(Long id){
        return Optional.of(moviesRepository.findById(id).orElse(new Movies()));
    }

    public Movies saveMovie(Movies movie){
        return moviesRepository.save(movie);
    }

    public Movies patchMovie(Movies movie, Long id){
        Optional <Movies> currentMovie = moviesRepository.findById(movie.getId());
        if (!movie.getTitle().equals(currentMovie.get().getTitle())) currentMovie.get().setTitle(movie.getTitle());
        return moviesRepository.save(currentMovie.get());
    }

    public void removeMovie(Long id){
        moviesRepository.deleteById(id);
    }

    public void removeMovie(Movies movie){
        moviesRepository.deleteById(movie.getId());
    }
}
