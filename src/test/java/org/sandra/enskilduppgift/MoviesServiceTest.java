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
    private Movies patchMovie;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie1 = new Movies();
        movie1.setId(1L);
        movie1.setYear(1990);
        movie1.setTitle("Movie 1");

        movie2 = new Movies();
        movie2.setId(2L);
        movie2.setYear(2000);
        movie2.setTitle("Movie 2");

        patchMovie = new Movies();
        patchMovie.setId(1L); // Samma ID som existingMovie
        patchMovie.setYear(2020);
        patchMovie.setTitle("Updated Title");
    }

    @Test
    void testGetAllMovies() {
        when(moviesRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));
        List<Movies> movies = moviesService.getAllMovies();

        assertEquals(2,movies.size());
        assertEquals("Movie 1", movies.get(0).getTitle());
        assertEquals(1990, movies.get(0).getYear());

        assertEquals("Movie 2", movies.get(1).getTitle());
        assertEquals(2000, movies.get(1).getYear());

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
            assertEquals(1990, resultExisting.get().getYear());

            Optional<Movies> resultNonExisting = moviesService.getOneMovie(nonExistingMovieId);
            assertEquals(false, resultNonExisting.isPresent());

            verify(moviesRepository, times(1)).findById(existingMovieId);
            verify(moviesRepository, times(1)).findById(nonExistingMovieId);
        }

        @Test
    void testSaveMovie(){
        when(moviesRepository.save(movie1)).thenReturn(movie1);

        Movies savedMovie = moviesService.saveMovie(movie1);

        verify(moviesRepository, times(1)).save(movie1);
        assertEquals(movie1, savedMovie);
        assertEquals("Movie 1", savedMovie.getTitle());
        assertEquals(1990, savedMovie.getYear());
        }

        @Test
        void testPatchMovie(){
            when(moviesRepository.findById(movie1.getId())).thenReturn(Optional.of(movie1));
            when(moviesRepository.save(movie1)).thenReturn(movie1);

            Movies updatedMovie = moviesService.patchMovie(patchMovie, movie1.getId());
            assertEquals("Updated Title", updatedMovie.getTitle());
            assertEquals(2020, updatedMovie.getYear());

            verify(moviesRepository, times(1)).findById(movie1.getId());
            verify(moviesRepository, times(1)).save(movie1);
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
