package ee.ooloros.hibernate.demo1321.example;

import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import ee.ooloros.hibernate.demo1321.BaseControllerIntegrationTest;
import ee.ooloros.hibernate.demo1321.shared.AdminContextService;
import jakarta.servlet.http.Cookie;

import static ee.ooloros.hibernate.demo1321.BaseControllerTest.FULL_ADMIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExampleControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Autowired
    private AdminContextService adminContextService;

    @Nested
    class GetExample {

        @Test
        void shouldReturn2xx() throws Exception {
            var sessionId = UUID.randomUUID().toString();
            adminContextService.authenticate(sessionId, FULL_ADMIN.toBuilder()
                    .sessionToken(sessionId)
                    .build());

            var request = get("/examples/{name}", "EXAMPLE_1")
                    .cookie(new Cookie("BOSESSIONID", sessionId));
            mockMvc.perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("[1,2,3]"));
        }
    }

    @Nested
    class PostExample {

        @Test
        void shouldReturn2xx() throws Exception {
            var sessionId = UUID.randomUUID().toString();
            adminContextService.authenticate(sessionId, FULL_ADMIN.toBuilder()
                    .sessionToken(sessionId)
                    .build());

            var request = put("/examples/{name}", "EXAMPLE_2")
                    .cookie(new Cookie("BOSESSIONID", sessionId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[1,2,3]");
            mockMvc.perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("[1,2,3]"));
        }
    }

    @Nested
    class GetAnonymous {

        @Test
        void shouldReturn2xx_whenAnonymous() throws Exception {
            var request = get("/examples/anonymous");
            mockMvc
                    .perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("-1"));
        }
    }

}
