package neowine;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    public WineRegionService getWineRegionService() {
      return new WineRegionService(getNeo4jDriver());
    }

    @Bean
    public WineSubregionService getWineSubregionService() {
      return new WineSubregionService(getNeo4jDriver());
    }

    @Bean
    public GrapeService getGrapeService() {
      return new GrapeService(getNeo4jDriver());
    }

    private Driver getNeo4jDriver() {
      return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4j"));
    }

    public static void main(String[] args) throws Exception {
      SpringApplication.run(Application.class, args);
    }

}
