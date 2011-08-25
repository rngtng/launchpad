import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

LColor current_color;

void setup() {
  launchpad = new Launchpad(this);
  launchpad.flashingAuto();
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
  d.changeButton( LButton.USER1, ((kolor.isMode( LColor.FLASHING )) ? LColor.GREEN_HIGH : LColor.GREEN_LOW) + LColor.FLASHING );
  d.changeButton( LButton.USER2, (kolor.isMode( LColor.NORMAL   )) ? LColor.GREEN_HIGH : LColor.GREEN_LOW );
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
  case LButton.USER1:
    current_color.setMode( LColor.FLASHING );
    break;
  case LButton.USER2:
    current_color.setMode( LColor.NORMAL );
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
  launchpad.changeGrid(x, y, current_color);
}


