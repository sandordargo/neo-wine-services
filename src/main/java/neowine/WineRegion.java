package neowine;

import java.util.Set;

import neowine.resources.WineRegionPojo;

public interface WineRegion {

  long getId();

  String getName();

  Set<WineSubregion> getContainedSubregions();

  WineRegionPojo asPojo();
}
