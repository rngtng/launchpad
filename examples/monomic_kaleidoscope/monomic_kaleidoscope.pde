import themidibus.*;

import com.rngtng.launchpad.*;

import jklabs.monomic.*;

Monome m;

void setup() {
  m = new MonomeLaunchpad(this);
}

void monomePressed(int x, int y) {
  m.setValue(x, y, !m.isLit(x,y));
  m.setValue(7-x, y, !m.isLit(7-x,y));
  m.setValue(x, 7-y, !m.isLit(x,7-y));
  m.setValue(7-x, 7-y, !m.isLit(7-x,7-y));
}

void draw() {
  
}

