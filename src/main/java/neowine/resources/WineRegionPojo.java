package neowine.resources;

import java.util.HashSet;
import java.util.Set;

public class WineRegionPojo {

  private long id;

  public WineRegionPojo() {
    this.id = -1;
    this.name = new String();
    this.subregions = new HashSet<>();
  }

  public WineRegionPojo(long id, String name, Set<String> subregions) {
    this.id = id;
    this.name = name;
    this.subregions = subregions;
  }

  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getSubregions() {
    return subregions;
  }

  public void setSubregions(Set<String> subregions) {
    this.subregions = subregions;
  }

  private Set<String> subregions;

}
