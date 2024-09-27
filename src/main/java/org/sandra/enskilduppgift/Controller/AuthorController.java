package org.sandra.enskilduppgift.Controller;

import jakarta.persistence.PostUpdate;
import jakarta.websocket.ClientEndpoint;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Author;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.sandra.enskilduppgift.Repository.MoviesRepository;
import org.sandra.enskilduppgift.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor

public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("")
    public ResponseEntity<List<Author>> getAllAuthors(){
        List <Author> author = authorService.getAllAuthors();

        return ResponseEntity.ok(author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Author>> getOneAuthor(@PathVariable long id){
        Optional<Author> author = authorService.getOneAuthor(id);

        return ResponseEntity.ok(author);
    }

    @PostMapping("")
    public ResponseEntity<Author> createNewAuthor(@RequestBody Author newAuthor){
        Author author = authorService.saveAuthor(newAuthor);
        return ResponseEntity.ok(author);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Author> updateOneAuthor(@PathVariable Long id,
                                                 @RequestBody Author newAuthor){
        Author patchedAuthor = authorService.patchAuthor(newAuthor, id);
        return ResponseEntity.ok(patchedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneAuthor(@PathVariable long id){
        authorService.removeAuthor(id);

        return ResponseEntity.ok("Removed Successfully");
    }
}
