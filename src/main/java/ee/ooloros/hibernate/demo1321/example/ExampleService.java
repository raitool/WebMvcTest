package ee.ooloros.hibernate.demo1321.example;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    public List<Integer> getExamples(String name) {
        List<Integer> examples = exampleRepository.findAllByName(name).stream()
                .map(Example::getValue)
                .toList();
        if (examples.isEmpty()) {
            throw new EntityNotFoundException("NOT_FOUND");
        }
        return examples;
    }

    public List<Integer> putExamples(String name, List<Integer> content) {
        exampleRepository.deleteAllByName(name);
        exampleRepository.flush();
        var newExamples = content.stream()
                .map(value -> Example.builder()
                        .name(name)
                        .value(value)
                        .build())
                .toList();
        exampleRepository.saveAll(newExamples);
        return content;
    }

    public Integer getAnonymous() {
        return -1;
    }
}
