import com.rngtng.launchpad.*;

Launchpad launchpad;

void setup() {
  size(400,400);
  launchpad = new Launchpad(this);
}

void draw() {
  background(0);   
  for(int y = 0; y < launchpad.height; y++) {
    for(int x = 0; x < launchpad.width; x++) {    
      launchpad.changeGrid(x, y, LColor.RED_HIGH);
      delay(100);  
    }  
  }
}
