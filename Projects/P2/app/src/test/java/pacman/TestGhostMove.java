package pacman;
import java.io.*;
import junit.framework.*;
import java.awt.Color;

public class TestGhostMove extends TestCase {

  public void testGhostMove() throws FileNotFoundException {
    Mainframe frame = new MainFrame();
    Ghost ghost = frame.addGhost(new Location(1, 2), "ghost1", Color.pink);

    if (ghost.get_valid_moves().size > 0) {
      Location prev = ghost.myLoc;
      if (!ghost.move()) {
        throw new FileNotFoundException();
      } else if (ghost.myLoc == prev) {
        throw new FileNotFoundException();
      }
    } else {
      if (ghost.move()) {
        throw new FileNotFoundException();
      }
    }

    return;
  }
}
