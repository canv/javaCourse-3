package lesson15.ioc.di.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class User {
    private UUID id;
    private String username;
    private String md5Password;

    public User(UUID id, String username, String md5Password) {
        this.id = id;
        this.username = username;
        this.md5Password = md5Password;
    }

    public User(String username, String md5Password) {
        this.username = username;
        this.md5Password = md5Password;
        this.id = UUID.randomUUID();
    }
}
