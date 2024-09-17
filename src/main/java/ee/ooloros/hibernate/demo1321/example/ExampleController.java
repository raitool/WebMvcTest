package ee.ooloros.hibernate.demo1321.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/example")
public class ExampleController {


    @GetMapping
    @RolesAllowed("CashAtCageApi::Login")
    void ok() {

    }
}
