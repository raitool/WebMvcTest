package ee.ooloros.hibernate.demo1321.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import ee.ooloros.hibernate.demo1321.shared.AuthRequestFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExampleController.class)
@Import(AuthRequestFilter.class)
class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPass() throws Exception {
        var request = get("/example")
                .header(
                        "Authorization",
                        "Bearer eyJraWQiOiJDQnk5TFlvM2JUK0M2eVpvcWp3ZzEwTndXXC9GQWxjUURteHVHYWNZdDBhRT0iLCJhbGciOiJSUzI1NiJ9.....");
        mockMvc
                .perform(request)
                .andExpect(status().is2xxSuccessful());
    }
}