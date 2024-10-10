package ee.ooloros.hibernate.demo1321.example;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import ee.ooloros.hibernate.demo1321.BaseControllerTest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExampleController.class)
class ExampleControllerTest extends BaseControllerTest {

    @MockBean
    private ExampleService exampleService;

    @Nested
    class GetExample {

        private static final String EXAMPLE_NAME = "GET_EXAMPLE";

        @BeforeEach
        void setup() {
            doReturn(List.of(1, 2, 3)).when(exampleService).getExamples(EXAMPLE_NAME);
        }

        @Test
        void shouldReturn2xx() throws Exception {
            doReturn(FULL_ADMIN).when(adminContextService).resolveRole(EXAMPLE_SESSION_ID, false);

            var request = get("/examples/{name}", EXAMPLE_NAME)
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID));
            mockMvc.perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("[1,2,3]"));
        }

        @Test
        void shouldReturnNotFound() throws Exception {
            doReturn(FULL_ADMIN).when(adminContextService).resolveRole(EXAMPLE_SESSION_ID, false);
            doThrow(new EntityNotFoundException()).when(exampleService).getExamples("DOES_NOT_EXIST");

            var request = get("/examples/{name}", "DOES_NOT_EXIST")
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID));
            mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("{code:  \"common.not_found\", params:  {}}", true));
        }

        @Test
        void shouldReturnForbidden_whenAnonymous() throws Exception {
            var request = get("/examples/{name}", EXAMPLE_NAME);
            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldReturnForbidden_whenSessionNotFound() throws Exception {
            var request = get("/examples/{name}", EXAMPLE_NAME)
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID));
            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

    }

    @Nested
    class PutExample {

        private static final String EXAMPLE_NAME = "PUT_EXAMPLE";

        @BeforeEach
        void setup() {
            doReturn(List.of(1, 2, 3)).when(exampleService).putExamples(EXAMPLE_NAME, List.of(1, 2, 3));
        }

        @Test
        void shouldReturn2xx() throws Exception {
            doReturn(FULL_ADMIN).when(adminContextService).resolveRole(EXAMPLE_SESSION_ID, false);

            var request = put("/examples/{name}", EXAMPLE_NAME)
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[1,2,3]");
            mockMvc.perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("[1,2,3]"));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "",
                "[2147483648]"//exceeds MAX_INT
        })
        void shouldReturnBadRequest(String content) throws Exception {
            doReturn(FULL_ADMIN).when(adminContextService).resolveRole(EXAMPLE_SESSION_ID, false);

            var request = put("/examples/{name}", EXAMPLE_NAME)
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content);
            mockMvc.perform(request)
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturnForbidden_whenNoValidRole() throws Exception {
            doReturn(FULL_ADMIN.toBuilder()
                             .roles(Set.of("Other::view"))
                             .build())
                    .when(adminContextService).resolveRole(EXAMPLE_SESSION_ID, false);

            var request = put("/examples/{name}", EXAMPLE_NAME)
                    .cookie(new Cookie("BOSESSIONID", EXAMPLE_SESSION_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[1,2,3]");
            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }

        @Test
        void shouldReturnForbidden_whenAnonymous() throws Exception {
            var request = put("/examples/{name}", EXAMPLE_NAME)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("[1,2,3]");
            mockMvc.perform(request)
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class GetAnonymous {

        @BeforeEach
        void setup() {
            doReturn(123).when(exampleService).getAnonymous();
        }

        @Test
        void shouldReturn2xx_whenAnonymous() throws Exception {
            var request = get("/examples/anonymous");
            mockMvc
                    .perform(request)
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().json("123"));
        }
    }

}
