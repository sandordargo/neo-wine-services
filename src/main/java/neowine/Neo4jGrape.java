package neowine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import neowine.resources.GrapePojo;

public class Neo4jGrape implements Grape {

  private final Driver db;
  private String name;
  private Set<String> producingSubregionNames;
  private Set<WineSubregion> producingSubregions;
  private long id;

  public Neo4jGrape(Driver db, String name) {
    this.db = db;
    this.producingSubregionNames = new HashSet<>();
    this.producingSubregions = new HashSet<>();
    initializeFieldsFromNeo4j(name);
  }

  private void initializeFieldsFromNeo4j(String name) {
    String query = "MATCH (wsr:WineSubRegion)<-[:GROWS_AT]-(grape:Grape {name:{grapeName}})"
        + "RETURN wsr, grape";
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("grapeName", name);
    try (Session session = db.session()) {
      StatementResult result = session.run(query, parameters);
      Record record = result.next();
      InternalNode grape = (InternalNode) record.get(1).asObject();
      this.name = grape.asMap().get("name").toString();
      this.id = grape.id();
      collectSubregions(result, record);
    }
  }

  private void collectSubregions(StatementResult result, Record record) {
    if (record.get(0).asObject() != null) {
      producingSubregionNames.add(((InternalNode) record.get(0).asObject()).asMap().get("name").toString());
      while (result.hasNext()) {
        producingSubregionNames.add(((InternalNode) result.next().get(0).asObject()).asMap().get("name").toString());
      }
    }
  }

  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override public Set<WineSubregion> getProducingSubregions() {
    if (this.producingSubregions.isEmpty()) {
      for (String subregionName : this.producingSubregionNames) {
        this.producingSubregions.add(new Neo4jWineSubregion(this.db, subregionName));
      }
    }
    return this.producingSubregions;
  }

  @Override
  public String toString() {
    List<String> sortedSubregions = new ArrayList<>(getProducingSubregions()
                                                        .stream()
                                                        .map(WineSubregion::getName)
                                                        .collect(Collectors.toList()));
    Collections.sort(sortedSubregions);
    return "[name: " + this.getName() + ", id: " + this.getId() + ", producing subregions: "
        + sortedSubregions.toString() + "]";
  }

  @Override
  public GrapePojo asPojo() {
    return new GrapePojo(this.getId(), this.getName(),
                         this.getProducingSubregions().stream().map(WineSubregion::getName).collect(Collectors.toSet()));
  }
}
