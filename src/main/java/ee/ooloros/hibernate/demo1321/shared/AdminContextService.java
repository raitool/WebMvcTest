package ee.ooloros.hibernate.demo1321.shared;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AdminContextService {
    private final Map<String, AdminRole> redisMock = new HashMap<>();

    public AdminRole resolveRole(String sessionToken, boolean refresh) {
        return redisMock.get(sessionToken);
    }

    /**
     * replace with actual Redis running in docker container
     * */
    public void authenticate(String sessionToken, AdminRole role) {
        redisMock.put(sessionToken, role);
    }

}
