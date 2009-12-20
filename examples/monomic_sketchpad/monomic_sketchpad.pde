import themidibus.*;

import com.rngtng.launchpad.*;

import jklabs.monomic.*;

Monome m;

void setup() {
  m = new MonomeLaunchpad(this);
}

void monomePressed(int x, int y) {
  m.setValue(x, y, !m.isLit(x,y));
}

void draw() {
  
}

