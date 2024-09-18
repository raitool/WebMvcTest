package ee.ooloros.hibernate.demo1321.example;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/examples")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService service;

    @GetMapping("/{name}")
    List<Integer> getExamples(@PathVariable String name) {
        return service.getExamples(name);
    }

    @PutMapping("/{name}")
    @RolesAllowed("Custom::edit")
    List<Integer> postExamples(@PathVariable String name, @RequestBody List<Integer> content) {
        return service.putExamples(name, content);
    }

    @GetMapping("/anonymous")
    public Integer getAnonymous() {
        return service.getAnonymous();
    }
}
