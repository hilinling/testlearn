package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ling on 17/7/18.
 */
@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {

    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    List<BookmarkResource> readBookmarks(@PathVariable String userId){
        this.validateUser(userId);
        List<BookmarkResource> bookmarkResourceList = bookmarkRepository.findByAccountUsername(userId)
                .stream().map(BookmarkResource::new).collect(Collectors.toList());
        return bookmarkResourceList;

    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input){
        this.validateUser(userId);

        return this.accountRepository.findByUsername(userId).map(account -> {
            Bookmark result = bookmarkRepository.save(new Bookmark(account,input.getUri(),input.getDescription()));

            Link forOneBookmark = new BookmarkResource(result).getLink("self");

            return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
        })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{bookmarkId}")
    @PreAuthorize("hasRole('USER')")
    BookmarkResource readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId){
        this.validateUser(userId);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));

    }

    private void validateUser(String username){
        this.accountRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(username)
        );
    }
}
