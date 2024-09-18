package ee.ooloros.hibernate.demo1321.example;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<Example, UUID> {

    List<Example> findAllByName(String name);

    void deleteAllByName(String name);
}
