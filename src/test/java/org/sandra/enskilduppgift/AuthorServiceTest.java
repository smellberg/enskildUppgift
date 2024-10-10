package org.sandra.enskilduppgift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sandra.enskilduppgift.Models.Author;
import org.sandra.enskilduppgift.Models.Movies;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.sandra.enskilduppgift.Service.AuthorService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author1;
    private Author author2;
    private Author patchAuthor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        author1 = new Author();
        author1.setId(1L);
        author1.setAge(20);
        author1.setName("Author 1");

        author2 = new Author();
        author2.setId(2L);
        author2.setAge(30);
        author2.setName("Author 2");

        patchAuthor = new Author();
        patchAuthor.setId(1L);
        patchAuthor.setAge(50);
        patchAuthor.setName("Updated Author");
    }

    @Test
    void testGetAllAuthors(){
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));
        List<Author> author = authorService.getAllAuthors();

        assertEquals(2, author.size());
        assertEquals("Author 1", author.get(0).getName());
        assertEquals(20, author.get(0).getAge());

        assertEquals("Author 2", author.get(1).getName());
        assertEquals(30, author.get(1).getAge());

        verify(authorRepository, times(1)).findAll();

    }

    @Test
    void testGetOneAuthor(){
        long existingAuthorId = 1L;
        long nonExistingAuthorId = 2L;

        when(authorRepository.findById(existingAuthorId)).thenReturn(Optional.of(author1));
        when(authorRepository.findById(nonExistingAuthorId)).thenReturn(Optional.empty());

        Optional<Author> resultExisting = authorService.getOneAuthor(existingAuthorId);
        assertEquals(true, resultExisting.isPresent());
        assertEquals(existingAuthorId, resultExisting.get().getId());
        assertEquals("Author 1", resultExisting.get().getName());
        assertEquals(20, resultExisting.get().getAge());

        Optional<Author> resultNonExisting = authorService.getOneAuthor(nonExistingAuthorId);
        assertEquals(false, resultNonExisting.isPresent());

        verify(authorRepository, times(1)).findById(existingAuthorId);
        verify(authorRepository, times(1)).findById(nonExistingAuthorId);

    }

    @Test
    void testSaveAuthor(){
        when(authorRepository.save(author1)).thenReturn(author1);

        Author savedAuthor = authorService.saveAuthor(author1);

        verify(authorRepository, times(1)).save(author1);
        assertEquals(author1, savedAuthor);
        assertEquals("Author 1", savedAuthor.getName());
        assertEquals(20, savedAuthor.getAge());
    }

    @Test
    void testPatchAuthor(){
        when(authorRepository.findById(author1.getId())).thenReturn(Optional.of(author1));
        when(authorRepository.save(author1)).thenReturn(author1);

        Author updatedAuthor = authorService.patchAuthor(patchAuthor, author1.getId());
        assertEquals("Updated Author", updatedAuthor.getName());
        assertEquals(50, updatedAuthor.getAge());

        verify(authorRepository, times(1)).findById(author1.getId());
        verify(authorRepository, times(1)).save(author1);

    }

    @Test
    void testRemoveAuthor(){
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.existsById(1L)).thenReturn(true);

        boolean result = authorService.removeAuthor(1L);

        assertTrue(result);
        verify(authorRepository, times(1)).existsById(1L);
    }
}
