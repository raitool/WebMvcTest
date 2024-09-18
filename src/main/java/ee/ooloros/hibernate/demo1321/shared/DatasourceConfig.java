package ee.ooloros.hibernate.demo1321.shared;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ConditionalOnBean(DataSource.class)
public class DatasourceConfig {

}
