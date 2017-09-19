package neowine;

import java.util.Set;

import neowine.resources.GrapePojo;

public interface Grape {
  long getId();

  String getName();

  Set<WineSubregion> getProducingSubregions();

  GrapePojo asPojo();
}
