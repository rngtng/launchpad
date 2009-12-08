import com.rngtng.launchpad.*;

Launchpad launchpad;

void setup() {
  size(400,400);
  launchpad = new Launchpad(this);
  launchpad.reset();
}

void draw() {
  background(0);   
  for(int y = 0; y < launchpad.height; y++) {
    for(int x = 0; x < launchpad.width; x++) {    
      launchpad.changeGrid(x, y, com.rngtng.launchpad.Color.RED_HIGH, com.rngtng.launchpad.Color.GREEN_LOW);
      delay(100);  
    }  
  }
}
