import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

LColor current_color;

int mode;

int FLASHING = 0;
int BUFFER0 = 1;
int BUFFER1 = 2;

void setup() {
  launchpad = new Launchpad(this);
  launchpad.reset();
  launchpad.flashingAuto();
  mode = FLASHING;
  current_color  = new LColor(LColor.YELLOW_HIGH);

  update_scene_buttons(launchpad, current_color);
}

void draw() {
  background(0);
  delay(1000);
}

void update_scene_buttons(Launchpad d, LColor kolor) {
  d.changeSceneButton( LButton.SCENE1, (kolor.isRed(LColor.RED_HIGH   )) ? LColor.YELLOW_HIGH : LColor.RED_HIGH );
  d.changeSceneButton( LButton.SCENE2, (kolor.isRed(LColor.RED_MEDIUM )) ? LColor.YELLOW_HIGH : LColor.RED_MEDIUM );
  d.changeSceneButton( LButton.SCENE3, (kolor.isRed(LColor.RED_LOW    )) ? LColor.YELLOW_HIGH : LColor.RED_LOW );
  d.changeSceneButton( LButton.SCENE4, (kolor.isRed(LColor.RED_OFF    )) ? LColor.YELLOW_HIGH : LColor.RED_OFF );
  d.changeSceneButton( LButton.SCENE5, (kolor.isGreen(LColor.GREEN_HIGH   )) ? LColor.YELLOW_HIGH : LColor.GREEN_HIGH ) ;
  d.changeSceneButton( LButton.SCENE6, (kolor.isGreen(LColor.GREEN_MEDIUM )) ? LColor.YELLOW_HIGH : LColor.GREEN_MEDIUM );
  d.changeSceneButton( LButton.SCENE7, (kolor.isGreen(LColor.GREEN_LOW    )) ? LColor.YELLOW_HIGH : LColor.GREEN_LOW );
  d.changeSceneButton( LButton.SCENE8, (kolor.isGreen(LColor.GREEN_OFF    )) ? LColor.YELLOW_HIGH : LColor.GREEN_OFF );
  d.changeButton( LButton.SESSION, (mode == FLASHING) ? LColor.YELLOW_HIGH : LColor.YELLOW_LOW );
  d.changeButton( LButton.USER1,   (mode == BUFFER0) ? LColor.YELLOW_HIGH : LColor.YELLOW_LOW);
  d.changeButton( LButton.USER2,   (mode == BUFFER1) ? LColor.YELLOW_HIGH : LColor.YELLOW_LOW );
}

public void launchpadSceneButtonPressed(int buttonCode) {
  int number = LButton.sceneButtonNumber(buttonCode);
  println(number);
  if( number < 5 ) { 
    current_color.setRed( 4 - number);
  } 
  else {
    current_color.setGreen(8 - number);
  }
}  

public void launchpadSceneButtonReleased(int buttonCode) {
  update_scene_buttons(launchpad, current_color);
}  

public void launchpadButtonPressed(int buttonCode) {
  switch(buttonCode) {
  case LButton.SESSION:
    mode = FLASHING;
    launchpad.flashingAuto();
    break;    
  case LButton.USER1:
    mode = BUFFER0;
    launchpad.bufferingMode(Launchpad.BUFFER0, Launchpad.BUFFER0);
    break;
  case LButton.USER2:
    mode = BUFFER1;
    launchpad.bufferingMode(Launchpad.BUFFER1, Launchpad.BUFFER1);
    break;
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
  update_scene_buttons(launchpad, current_color);
}  

public void launchpadGridPressed(int x, int y) {
  switch(y) {
  case 0:
    launchpad.changeGrid(x, y, current_color);
    break;
  case 1:
    launchpad.bufferingMode(Launchpad.BUFFER1, Launchpad.BUFFER0);
    launchpad.changeGrid(x, y, current_color.getRed() + current_color.getGreen() + LColor.FLASHING );
    launchpad.flashingAuto();
    break;
  default:
    launchpad.changeGrid(x, y, current_color.getRed() + current_color.getGreen() + LColor.BUFFERED );
    break;  
  }
}




