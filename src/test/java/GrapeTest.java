import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.exceptions.NoSuchRecordException;
import org.neo4j.harness.junit.Neo4jRule;

import neowine.Grape;
import neowine.Neo4jGrape;
import neowine.WineSubregion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GrapeTest {

  @Rule
  public Neo4jRule neo4j = new Neo4jRule().withFixture(FakeData.FAKE_DATA_CREATOR);

  @Test
  public void shouldReturnExistingGrape() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedGrapeName = "Pinot Noir";
      Grape grape = new Neo4jGrape(driver, expectedGrapeName);
      assertEquals(expectedGrapeName, grape.getName());
    }
  }

  @Test
  public void shouldFindSubRegions() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String existingGrapeName = "Cabernet Franc";
      List<String> expectedSubregions = Arrays.asList("Eger", "Bükk");
      Grape grape = new Neo4jGrape(driver, existingGrapeName);
      assertTrue(expectedSubregions.containsAll(subregionsAsSetOfStringOfTheirNames(grape.getProducingSubregions()))
          && subregionsAsSetOfStringOfTheirNames(grape.getProducingSubregions()).containsAll(expectedSubregions));
    }
  }

  @Test(expected = NoSuchRecordException.class)
  public void nonExistingGrapeThrowsException() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String notProducedGrapeName = "Syrah";
      Grape grape = new Neo4jGrape(driver, notProducedGrapeName);
      System.out.println(grape.toString());
    }
  }

  @Test
  public void grapeStringRepresentationShouldMatch() {
    try (Driver driver = GraphDatabase.driver(neo4j.boltURI(), Config.build().withoutEncryption().toConfig())) {
      String expectedGrapeDescription = "[name: Cabernet Franc, id: 6, producing subregions: [Bükk, Eger]]";
      Grape grape = new Neo4jGrape(driver, "Cabernet Franc");
      assertEquals(expectedGrapeDescription, grape.toString());
    }
  }

  private Set<String> subregionsAsSetOfStringOfTheirNames(Set<WineSubregion> wineSubregions) {
    return wineSubregions.stream().map(subregion -> subregion.getName()).collect(Collectors.toSet());
  }
}
