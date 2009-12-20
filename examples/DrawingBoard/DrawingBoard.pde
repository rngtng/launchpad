import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

LColor current_color;

boolean[] flags = new boolean[64];

void setup() {
  launchpad = new Launchpad(this);
}

void draw() {
  background(0);
  delay(1000);
}

public void launchpadButtonPressed(int buttonCode) {
  switch(buttonCode) {
  case LButton.MIXER:
    launchpad.changeButton(LButton.MIXER, LColor.RED_HIGH);
    break;
  }
}  

public void launchpadButtonReleased(int buttonCode) {
  switch(buttonCode) {
  case LButton.MIXER:
    exit();
  }
}  

public void launchpadGridPressed(int x, int y) {
  int coord = y*8 + x;
  flags[coord] =  !flags[coord];
  launchpad.changeGrid(x, y, (flags[coord]) ? LColor.YELLOW_HIGH : LColor.YELLOW_OFF);
}


