import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

void setup() {
  launchpad = new Launchpad(this);

  int pos_x = 0;  
  int pos_y = 0;

  for( int red = 0; red < 4; red++) {
    for( int green = 0; green < 4; green++) {
      launchpad.changeGrid( pos_x, pos_y, new LColor(red, green));
      launchpad.changeGrid( 7 - pos_x, pos_y, new LColor(red, green));
      launchpad.changeGrid( pos_x, 7 - pos_y, new LColor(red, green));
      launchpad.changeGrid( 7 - pos_x, 7 - pos_y, new LColor(red, green));
      pos_y += 1;
    }
    pos_x += 1;
    pos_y = 0;
  }
}

void draw() {
  background(0);
}




