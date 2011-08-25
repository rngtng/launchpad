import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

void setup() {
  launchpad = new Launchpad(this);
}

void draw() {
  background(0);
}

public void launchpadGridPressed(int x, int y) {
  println("GridButton pressed at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.YELLOW_HIGH);
}

public void launchpadGridReleased(int x, int y) {
  println("GridButton released at: " + x + ", " + y);
  launchpad.changeGrid(x, y, LColor.OFF);
}

public void launchpadButtonPressed(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.RED_HIGH);
}  

public void launchpadButtonReleased(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeButton(buttonCode, LColor.OFF);
  if(buttonCode == LButton.MIXER) exit();
}  

public void launchpadSceneButtonPressed(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeSceneButton(buttonCode, LColor.GREEN_HIGH);
}  

public void launchpadSceneButtonReleased(int buttonCode) {
  println("Button pressed: " + buttonCode);
  launchpad.changeSceneButton(buttonCode, LColor.OFF);
}  
