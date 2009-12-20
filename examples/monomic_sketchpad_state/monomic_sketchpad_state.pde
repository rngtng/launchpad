import processing.serial.*;
import jklabs.monomic.*;

Monome m;

void setup() {
  m = new MonomeSerial(this);
}

void monomePressed(int x, int y) {
  m.setValue(x, y, !m.isLit(x,y));
    
  byte[] buttons = m.getButtonValues();
  for (int i=0; i<buttons.length; i++) println(m.bitString(buttons[i]));
  
  println();

  byte[] leds = m.getLedValues();
  for (int i=0; i<leds.length; i++) println(m.bitString(leds[i]));

}

void draw() {
  
}

