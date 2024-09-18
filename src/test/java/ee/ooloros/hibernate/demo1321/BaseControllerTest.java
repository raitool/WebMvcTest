package ee.ooloros.hibernate.demo1321;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import ee.ooloros.hibernate.demo1321.shared.AdminContextService;
import ee.ooloros.hibernate.demo1321.shared.AdminRole;
import ee.ooloros.hibernate.demo1321.shared.SecurityConfiguration;

@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
public abstract class BaseControllerTest {

    protected static final String EXAMPLE_SESSION_ID = "1062k46gucv8sgwugp3a9vlvw349";
    public static final AdminRole FULL_ADMIN = AdminRole.builder()
            .username("admin")
            .sessionToken(EXAMPLE_SESSION_ID)
            .roles(Set.of("Custom::edit", "Other::view"))
            .build();

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AdminContextService adminContextService;

}
