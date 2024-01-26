package com.aphar.registration.token;

import com.aphar.registration.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("tokens")
public class Token {
    @Id
    private String id;

    private String token;

    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @DBRef
    private User user;
}
