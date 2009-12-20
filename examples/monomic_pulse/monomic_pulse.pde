import themidibus.*;

import com.rngtng.launchpad.*;

import jklabs.monomic.*;

Monome m;
float b = 0;
float delta = 0.2;

void setup() {
  m = new MonomeLaunchpad(this);
  m.setLedIntensity(b);
  m.lightsOn();
}

void draw() {
  m.setLedIntensity(b);
  if (b < 0 || b > 9) delta = -delta;
  b+=delta;
}

void monomePressed(int x, int y) {
  delta = (y+1)/30f;
}

void dispose() {
  stop();
}

protected void finalize() {
  stop();
}

void stop() {
 println("stopping");
 m.setLedIntensity(1);
 m.lightsOff(); 
}
