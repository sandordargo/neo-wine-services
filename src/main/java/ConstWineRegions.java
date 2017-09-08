import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class ConstWineRegions implements WineRegions {

  private Driver db;

  public ConstWineRegions(Driver db) {
    this.db = db;
  }

  @Override public WineRegion getByName(String name) {
    String query = "MATCH (wr:WineRegion {name:{regionName}})-[relationship:CONTAINS]->(wsr:WineSubRegion) RETURN wr, wsr";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("regionName", name);
    Set<String> results = new HashSet<>();
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      while (result.hasNext()) {
        InternalNode region = (InternalNode) result.next().get(0).asObject();
        results.add(region.asMap().get("name").toString());
      }
      return new Neo4jWineRegion(db, "Eger");
    }
  }
}
