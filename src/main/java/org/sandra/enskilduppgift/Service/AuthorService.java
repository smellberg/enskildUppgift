package org.sandra.enskilduppgift.Service;

import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Author;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Optional<Author> getOneAuthor(Long id){
        return Optional.of(authorRepository.findById(id).orElse(new Author()));
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author patchAuthor(Author author, Long id){
        Optional <Author> currentAuthor = authorRepository.findById(author.getId());
        if (!author.getName().equals(currentAuthor.get().getName())) currentAuthor.get().setName(author.getName());
        return authorRepository.save(currentAuthor.get());
    }

    public void removeAuthor(Long id){
        authorRepository.deleteById(id);
    }

}
