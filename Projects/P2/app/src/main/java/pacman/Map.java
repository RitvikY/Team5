package pacman;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JComponent;

public class Map {

  public enum Type {
    EMPTY,
    PACMAN,
    GHOST,
    WALL,
    COOKIE
  }

  private HashMap<Location, HashSet<Type>> field;
  private boolean gameOver;
  private int dim;

  private HashMap<String, Location> locations;
  private HashMap<String, JComponent> components;
  private HashSet<Type> emptySet;
  private HashSet<Type> wallSet;

  private int cookies = 0;

  public Map(int dim) {
    gameOver = false;
    locations = new HashMap<String, Location>();
    components = new HashMap<String, JComponent>();
    field = new HashMap<Location, HashSet<Type>>();

    emptySet = new HashSet<Type>();
    wallSet = new HashSet<Type>();
    emptySet.add(Type.EMPTY);
    wallSet.add(Type.WALL);
    this.dim = dim;
  }

  public void add(String name, Location loc, JComponent comp, Type type) {
    locations.put(name, loc);
    components.put(name, comp);
    if (!field.containsKey(loc)) field.put(loc, new HashSet<Type>());
    field.get(loc).add(type);
  }

  public int getCookies() {
    return cookies;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean move(String name, Location loc, Type type) {
    //assume given a valid move
    //only return false if extreme case (location or name not in data structure)
    if(!locations.containsKey(name) || !components.containsKey(name) || !field.containsKey(locations.get(name)) || !field.get(locations.get(name)).contains(type)) {
      return false;
    }

    Location og_loc = locations.get(name);
    JComponent comp = components.get(name);
    comp.setLocation(loc.x, loc.y);
    locations.put(name, loc);
    field.get(og_loc).remove(type);
    field.get(loc).add(type);
    this.add(name, loc, comp, type);

    // update locations, components, and field
    // use the setLocation method for the component to move it to the new location
    
    //return true;
    return true;
  }

  public HashSet<Type> getLoc(Location loc) {
    // wallSet and emptySet will help you write this method
    if (!field.containsKey(loc)) {
      return emptySet;
    }

    return field.get(loc);
  }

  public boolean attack(String Name) {
    // update gameOver
    Location ghost = locations.get(Name);
    if (ghost == null) {
      return false; //ghost dont exist
    } else {
      //ghost exist
      Location north = new Location(ghost.x, ghost.y -1);
      Location south = new Location(ghost.x, ghost.y +1);
      Location west = new Location(ghost.x - 1, ghost.y);
      Location east = new Location(ghost.x + 1, ghost.y);
      HashSet<Type> nTypes = this.getLoc(north);
      HashSet<Type> sTypes = this.getLoc(south);
      HashSet<Type> wTypes = this.getLoc(west);
      HashSet<Type> eTypes = this.getLoc(east);
      if (nTypes.contains(Map.Type.PACMAN) || sTypes.contains(Map.Type.PACMAN) || wTypes.contains(Map.Type.PACMAN)|| eTypes.contains(Map.Type.PACMAN)) {
        //pacman exist
        this.gameOver = true;
        return false;
      }
    }
    return false;
  }

  public JComponent eatCookie(String name) {
    // update locations, components, field, and cookies
    // the id for a cookie at (10, 1) is tok_x10_y1
    Location pacman = locations.get(name);
    if (getLoc(pacman).contains(Map.Type.COOKIE)) {
      this.cookies++;
      getLoc(pacman).remove(Map.Type.COOKIE);
      return components.get(String.format("tok_x%d_y%d", pacman.x, pacman.y));
    } else {
      return null;
    }
  }
}
