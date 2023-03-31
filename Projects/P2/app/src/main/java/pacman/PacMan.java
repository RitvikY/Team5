package pacman;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JComponent;

public class PacMan {
  String myName;
  Location myLoc;
  Map myMap;
  Location shift;

  public PacMan(String name, Location loc, Map map) {
    this.myLoc = loc;
    this.myName = name;
    this.myMap = map;
  }

  public ArrayList<Location> get_valid_moves() {
    ArrayList<Location> moves = new ArrayList<Location>();
    Location up = this.myLoc.shift(0,1);
    Location down = this.myLoc.shift(0,-1);
    Location left = this.myLoc.shift(-1,0);
    Location right = this.myLoc.shift(1,0);

    if(!myMap.getLoc(up).contains(Map.Type.WALL)){
      moves.add(up);
    }
    if(!myMap.getLoc(down).contains(Map.Type.WALL) && down.y>=0){
      moves.add(down);
    }
    if(!myMap.getLoc(left).contains(Map.Type.WALL) && left.x>=0){
      moves.add(left);
    }
    if(!myMap.getLoc(right).contains(Map.Type.WALL)){
      moves.add(right);
    }
  
    //return moves;
    return moves;
  }

  public boolean move() {
    ArrayList<Location> valid_moves = get_valid_moves();

    if (valid_moves.size() == 0) {
      return false;
    }

    Location new_location = valid_moves.get(0);
    boolean success = myMap.move(myName, new_location, Map.Type.PACMAN);

    if (!success) {
      return false;
    }

    myLoc = new_location;
    return true;
  }

  public boolean is_ghost_in_range() {
    Location pacLoc = this.myLoc;
    Location north = new Location(pacLoc.x, pacLoc.y -1);
    Location south = new Location(pacLoc.x, pacLoc.y +1);
    Location west = new Location(pacLoc.x - 1, pacLoc.y);
    Location east = new Location(pacLoc.x + 1, pacLoc.y);

    HashSet<Map.Type> nTypes = this.myMap.getLoc(north);
    HashSet<Map.Type> sTypes = this.myMap.getLoc(south);
    HashSet<Map.Type> wTypes = this.myMap.getLoc(west);
    HashSet<Map.Type> eTypes = this.myMap.getLoc(east);
    
    if (nTypes.contains(Map.Type.GHOST) || sTypes.contains(Map.Type.GHOST) || wTypes.contains(Map.Type.GHOST)|| eTypes.contains(Map.Type.GHOST)) {
      return false;
    }

    return false;
  }

  public JComponent consume() {
    if (myMap.getLoc(myLoc).contains(Map.Type.COOKIE)) {
        return myMap.eatCookie(myName);
    } else {
      return null;
    }
  }
}
