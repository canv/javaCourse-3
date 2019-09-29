package lesson15.app.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class User {
    private UUID id;
    private String username;
    private String md5Password;

    public User(String username, String md5Password) {
        this.username = username;
        this.md5Password = md5Password;
        this.id = UUID.randomUUID();
    }
}
