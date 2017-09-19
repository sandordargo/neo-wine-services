package neowine;

import java.util.Set;

import neowine.resources.WineSubregionPojo;

public interface WineSubregion {
  long getId();

  String getName();

  Set<Grape> getGrapesGrownAt();

  WineRegion getIncludingRegion();

  WineSubregionPojo asPojo();

}
