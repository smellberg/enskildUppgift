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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> getOneAuthor(@PathVariable long id){
        Optional<Author> author = authorService.getOneAuthor(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Author with ID " + id + " not found");
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<Author> replaceAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor){
        try {
            updatedAuthor.setId(id); // Ställ in det gamla id:t
            Author savedAuthor = authorService.saveAuthor(updatedAuthor); // Spara den uppdaterade författaren
            return ResponseEntity.ok(savedAuthor);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Returnera 404 om författaren inte hittades
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOneAuthor(@PathVariable long id){
        boolean isRemoved = authorService.removeAuthor(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
        }
        return ResponseEntity.ok("Removed Successfully");
    }
}
