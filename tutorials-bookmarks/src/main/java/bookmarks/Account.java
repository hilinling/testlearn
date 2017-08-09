package bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ling on 17/7/18.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;

    public Account(){}

    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<Bookmark>();

    @JsonIgnore
    private String password;
    private String username;

    public Account(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account 【username = "+username+", password = "+password;
    }
}
