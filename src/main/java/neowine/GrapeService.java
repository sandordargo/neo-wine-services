package neowine;

import org.neo4j.driver.v1.Driver;

public class GrapeService {
  private final Driver db;

  public GrapeService(Driver db) {
    this.db = db;
  }

  public Grape getGrapeByName(String grapeName) {
    Grape grape = new Neo4jGrape(db, grapeName);
    return grape;
  }
}
