package ee.ooloros.hibernate.demo1321;

import org.springframework.boot.SpringApplication;

public class TestDemo1321Application {

    public static void main(String[] args) {
        SpringApplication.from(Demo1321Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
