import processing.serial.*;

import themidibus.*;

import com.rngtng.launchpad.*;

import jklabs.monomic.*;


// Bounce
// by REAS <http://reas.com>

// altered by jesse kriss <http://jklabs.net> for monome output

// When the shape hits the edge of the window, it reverses its direction

// Updated 1 September 2002

int size = 1;       // Width of the shape
float xpos, ypos;    // Starting position of shape    

float xspeed = 0.3;  // Speed of the shape
float yspeed = 0.2;  // Speed of the shape

int xdirection = 1;  // Left or Right
int ydirection = 1;  // Top to Bottom

Monome m;
int mWidth = 8;
int mHeight = mWidth;

void setup() 
{
  size(200, 200);
  noStroke();
  smooth();
  frameRate(60);
  // Set the starting position of the shape
  xpos = mWidth/2;
  ypos = mHeight/2;
  m = new MonomeLaunchpad(this);
  //m.setDebug(true);
}

void draw() 
{
  // Update the position of the shape
  xpos = xpos + ( xspeed * xdirection );
  ypos = ypos + ( yspeed * ydirection );
  
  // Test to see if the shape exceeds the boundaries of the screen
  // If it does, reverse its direction by multiplying by -1
  if (xpos > mWidth-size || xpos < 0) {
    xdirection *= -1;
  }
  if (ypos > mHeight-size || ypos < 0) {
    ydirection *= -1;
  }

  // Draw the shape
  //ellipse(xpos+size/2, ypos+size/2, size, size);
  m.lightsOff();
  m.lightOn((int)xpos, (int)ypos);
}

void monomePressed(int x, int y) {
  xpos = x;
  ypos = y; 
}
