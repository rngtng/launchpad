import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;
byte[] data = new byte[64];

void setup() {
  launchpad = new Launchpad(this);
  launchpad.reset();
}

void draw() {
  background(0);
}

public void launchpadGridPressed(int x, int y) {
  println("GridButton pressed at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.RED_HIGH, LColor.GREEN_OFF);
}

public void launchpadGridReleased(int x, int y) {
  println("GridButton released at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.RED_HIGH, LColor.GREEN_HIGH);
}

public void launchpadButtonPressed(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.RED_OFF, LColor.GREEN_HIGH);
}  

public void launchpadButtonReleased(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.RED_HIGH, LColor.GREEN_OFF, LColor.FLASHING);
}  


