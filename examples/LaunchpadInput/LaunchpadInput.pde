import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;
byte[] data = new byte[64];

void setup() {
  launchpad = new Launchpad(this);
  launchpad.flashingAuto();
}

void draw() {
  background(0);
}

public void launchpadGridPressed(int x, int y) {
  println("GridButton pressed at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.RED_HIGH);
}

public void launchpadGridReleased(int x, int y) {
  println("GridButton released at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.YELLOW_HIGH);
}

public void launchpadButtonPressed(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.GREEN_HIGH);
}  

public void launchpadButtonReleased(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.RED_HIGH + LColor.FLASHING);
}  


public void launchpadSceneButtonPressed(int buttonCode) {
  println("Scene Button pressed: " + buttonCode);
  launchpad.changeSceneButton(buttonCode, LColor.RED_HIGH);
}  

public void launchpadSceneButtonReleased(int buttonCode) {
  println("Scene Button pressed: " + buttonCode);
  launchpad.changeSceneButton(buttonCode, LColor.GREEN_HIGH + LColor.FLASHING);
}  
