import com.rngtng.launchpad.*;

Launchpad launchpad;
byte[] data = new byte[64];

void setup() {
  launchpad = new Launchpad(this);
  launchpad.reset();
  for(int y = 0; y < 64; y++) {
    data[y] = 16;
  }
}



void draw() {
  background(0);   
  for(int y = 0; y < launchpad.height; y++) {
    for(int x = 0; x < launchpad.width; x++) {    
      byte pos = byte(y*launchpad.width + x);
      if(pos > 0) data[pos-1] = 0;
      data[pos] = 3;
      launchpad.changeAll(data);
      delay(100);
    }  
  }
}
