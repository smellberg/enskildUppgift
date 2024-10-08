package org.sandra.enskilduppgift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sandra.enskilduppgift.Repository.MoviesRepository;
import org.sandra.enskilduppgift.Service.MoviesService;
import org.sandra.enskilduppgift.Models.Movies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MoviesServiceTest {
    @Mock
    private MoviesRepository moviesRepository;

    @InjectMocks
    private MoviesService moviesService;

    private Movies movie1;
    private Movies movie2;
    private Movies existingMovie;
    private Movies patchMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie1 = new Movies();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");

        movie2 = new Movies();
        movie2.setId(2L);
        movie2.setTitle("Movie 2");

        existingMovie = new Movies();
        existingMovie.setId(1L);
        existingMovie.setTitle("Original Title");

        patchMovie = new Movies();
        patchMovie.setId(1L); // Samma ID som existingMovie
        patchMovie.setTitle("Updated Title");
    }

    @Test
    void testGetAllMovies() {
        when(moviesRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));
        List<Movies> movies = moviesService.getAllMovies();

        assertEquals(2,movies.size());
        assertEquals("Movie 1", movies.get(0).getTitle());
        assertEquals("Movie 2", movies.get(1).getTitle());

        verify(moviesRepository, times(1)).findAll();
        }

        @Test
        void testGetOneMovie(){
            long existingMovieId = 1L;
            long nonExistingMovieId = 2L;

            when(moviesRepository.findById(existingMovieId)).thenReturn(Optional.of(movie1));
            when(moviesRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());

            Optional<Movies> resultExisting = moviesService.getOneMovie(existingMovieId);
            assertEquals(true, resultExisting.isPresent());
            assertEquals(existingMovieId, resultExisting.get().getId());
            assertEquals("Movie 1", resultExisting.get().getTitle());

            Optional<Movies> resultNonExisting = moviesService.getOneMovie(nonExistingMovieId);
            assertEquals(false, resultNonExisting.isPresent());

            verify(moviesRepository, times(1)).findById(existingMovieId);
            verify(moviesRepository, times(1)).findById(nonExistingMovieId);
        }

        @Test
    void testSaveMovie(){
        Movies movie = new Movies();
        movie.setId(3L);
        movie.setTitle("Stepbrothers");
        movie.setYear(2005);

        when(moviesRepository.save(movie)).thenReturn((movie));

        Movies savedMovie = moviesService.saveMovie(movie);

        verify(moviesRepository, times(1)).save(movie);

            assertEquals(movie, savedMovie);
            assertEquals("Stepbrothers", savedMovie.getTitle());
            assertEquals(2005, savedMovie.getYear());
        }

        @Test
        void testPatchMovie(){
            when(moviesRepository.findById(existingMovie.getId())).thenReturn(Optional.of(existingMovie));
            when(moviesRepository.save(existingMovie)).thenReturn(existingMovie);

            Movies updatedMovie = moviesService.patchMovie(patchMovie, existingMovie.getId());
            assertEquals("Updated Title", updatedMovie.getTitle());

            verify(moviesRepository, times(1)).findById(existingMovie.getId());
            verify(moviesRepository, times(1)).save(existingMovie);
        }
@Test
    void testRemoveMovie(){
    when(moviesRepository.findById(1L)).thenReturn(Optional.of(movie1));
    when(moviesRepository.existsById(1L)).thenReturn(true);

    boolean result = moviesService.removeMovie((1L));

    assertTrue(result);
    verify(moviesRepository, times(1)).deleteById(1L);
}
}
