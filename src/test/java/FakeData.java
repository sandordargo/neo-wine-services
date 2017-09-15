public class FakeData {
  public static String FAKE_DATA_CREATOR =
      "CREATE (eger:WineRegion {name:'Eger'})-[:CONTAINS]->(eger_subregion:WineSubRegion {name:'Eger'}),"
        + "(eger)-[:CONTAINS]->(matra:WineSubRegion {name:'Mátra'}),"
        + "(eger)-[:CONTAINS]->(bukk:WineSubRegion {name:'Bükk'}),"
        + "(eger_subregion)<-[:GROWS_AT]-(merlot:Grape {name:'Merlot'}),"
        + "(eger_subregion)<-[:GROWS_AT]-(pinot_noir:Grape {name:'Pinot Noir'}),"
        + "(bukk)<-[:GROWS_AT]-(cabernet_franc:Grape {name:'Cabernet Franc'}),"
        + "(eger_subregion)<-[:GROWS_AT]-(cabernet_franc)";
}
