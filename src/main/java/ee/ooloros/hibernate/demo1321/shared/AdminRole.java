package ee.ooloros.hibernate.demo1321.shared;

import java.security.Principal;
import java.util.Set;

import lombok.Builder;

@Builder(toBuilder = true)
public record AdminRole(
        String username, String sessionToken, Set<String> roles
) implements Principal {

    @Override
    public String getName() {
        return username;
    }
}
