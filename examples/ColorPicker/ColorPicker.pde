import themidibus.*;

import com.rngtng.launchpad.*;

Launchpad launchpad;

LColor current_color;

void setup() {
  launchpad = new Launchpad(this);
  launchpad.flashingAuto();
  current_color  = new LColor(LColor.YELLOW_HIGH);
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
  for( int red = 0; red < 8; red++) {
    for( int green = 0; green < 8; green++) {
      d.changeGrid( red, green, kolor);
    }
  }  
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
  case LButton.UP:
    for( int red = 0; red < 4; red++) {
      for( int green = 0; green < 4; green++) {
        launchpad.changeGrid( red, green, new LColor(red, green));
      }
    }
    break;
  case LButton.DOWN:
    for( int red = 0; red < 4; red++) {
      for( int green = 0; green < 4; green++) {
        launchpad.changeGrid( red*2, green*2, new LColor(red, green));
        launchpad.changeGrid( red*2+1, green*2+1, new LColor(red, green));
      }
    }
    break;
  case LButton.LEFT:
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
    break;
  case LButton.RIGHT:
    update_scene_buttons(launchpad, current_color);
    break;
  }
}  

public void launchpadButtonReleased(int buttonCode) {
  switch(buttonCode) {
  case LButton.MIXER:
    exit();
  }
}  







