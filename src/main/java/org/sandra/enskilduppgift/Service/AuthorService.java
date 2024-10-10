package org.sandra.enskilduppgift.Service;

import lombok.RequiredArgsConstructor;
import org.sandra.enskilduppgift.Models.Author;
import org.sandra.enskilduppgift.Models.Movies;
import org.sandra.enskilduppgift.Repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author patchAuthor(Author author, Long id){
        Optional <Author> currentAuthor = authorRepository.findById(author.getId());
        if (currentAuthor.isPresent()) {
            Author existingAuthor = currentAuthor.get();

            if (!author.getName().equals(existingAuthor.getName())) {
                existingAuthor.setName(author.getName());
            }

            if (author.getAge() != existingAuthor.getAge()) {
                existingAuthor.setAge(author.getAge());
            }

            return authorRepository.save(existingAuthor);
        }

        throw new RuntimeException("Author not found");
    }

    @Transactional
    public boolean removeAuthor(Long id){
        Optional<Author> authorOpt = authorRepository.findById(id);
        if (authorOpt.isPresent()) {
            Author author = authorOpt.get();

            List<Movies> movies = author.getMovies();
            if (movies != null) {
                for (Movies movie : movies) {

                    movie.setAuthor(null);
                }
            }

            authorRepository.delete(author);
            return true;
        }
        return false;
    }
}

