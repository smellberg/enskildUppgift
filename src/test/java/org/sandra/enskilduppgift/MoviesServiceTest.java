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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MoviesServiceTest {
    @Mock
    private MoviesRepository moviesRepository;

    @InjectMocks
    private MoviesService moviesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovies() {
        Movies movie1 = new Movies();
        movie1.setId(1L);
        movie1.setTitle("Movie 1");

        Movies movie2 = new Movies();
            movie2.setId(2L);
            movie2.setTitle("Movie 2");


            when(moviesRepository.findAll()).thenReturn(Arrays.asList(movie1, movie2));


            List<Movies> movies = moviesService.getAllMovies();


            assertEquals(2,movies.size());

            verify(moviesRepository, times(1)).findAll();
        }
    }
