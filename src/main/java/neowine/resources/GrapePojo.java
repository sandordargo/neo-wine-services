package neowine.resources;

import java.util.HashSet;
import java.util.Set;

public class GrapePojo {

  private long id;
  private String name;
  private Set<String> producingSubregions;

  public GrapePojo() {
    this.id = -1;
    this.name = new String();
    this.producingSubregions = new HashSet<>();
  }


  public GrapePojo(long id, String name, Set<String> producingSubregions) {
    this.id = id;
    this.name = name;
    this.producingSubregions = producingSubregions;
  }

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

  public Set<String> getProducingSubregions() {
    return producingSubregions;
  }

  public void setProducingSubregions(Set<String> producingSubregions) {
    this.producingSubregions = producingSubregions;
  }

}
