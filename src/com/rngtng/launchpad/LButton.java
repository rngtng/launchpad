package com.rngtng.launchpad;

/**
 * The Button class to handle the different Button types, acces thier MIDI codes
 * and transform code <-> numer conversions
 *
 * @author rngtng - Tobias Bielohlawek
 *
 */
public class LButton {

	public final static int UP      = 0x68;
	public final static int DOWN    = 0x69;
	public final static int LEFT    = 0x6A;
	public final static int RIGHT   = 0x6B;
	public final static int SESSION = 0x6C;
	public final static int USER1   = 0x6D;
	public final static int USER2   = 0x6E;
	public final static int MIXER   = 0x6F;

	public final static int SCENE_OFFSET  = 1;
	public final static int SCENE1  = SCENE_OFFSET + 0x08;
	public final static int SCENE2  = SCENE_OFFSET + 0x18;
	public final static int SCENE3  = SCENE_OFFSET + 0x28;
	public final static int SCENE4  = SCENE_OFFSET + 0x38;
	public final static int SCENE5  = SCENE_OFFSET + 0x48;
	public final static int SCENE6  = SCENE_OFFSET + 0x58;
	public final static int SCENE7  = SCENE_OFFSET + 0x68;
	public final static int SCENE8  = SCENE_OFFSET + 0x78;	
	
	/* static ones */

	/**
	 *  checks for valid button code
	 *
	 *  @param buttonCode code of the button
	 *  @return boolean if code is valid
	 */
	public static boolean isButtonCode(int buttonCode) {
		if( buttonCode == UP) return true;
		if( buttonCode == DOWN) return true;
		if( buttonCode == LEFT) return true;
		if( buttonCode == RIGHT) return true;
		if( buttonCode == SESSION) return true;
		if( buttonCode == USER1) return true;
		if( buttonCode == USER2) return true;
		if( buttonCode == MIXER) return true;
		return false;
	}

	/**
	 *  checks for valid scene button code
	 *
	 *  @param buttonCode code of the button
	 *  @return boolean whether code is as scene button
	 */
	public static  boolean isSceneButtonCode(int buttonCode) {
		if( buttonCode == SCENE1) return true;
		if( buttonCode == SCENE2) return true;
		if( buttonCode == SCENE3) return true;
		if( buttonCode == SCENE4) return true;
		if( buttonCode == SCENE5) return true;
		if( buttonCode == SCENE6) return true;
		if( buttonCode == SCENE7) return true;
		if( buttonCode == SCENE8) return true;
		return false;
	}

	/**
	 *  return button code for button number
	 *
	 *  @param button button value
	 *  @return button number
	 */
	public static int buttonNumber(int button) {	
		return ( button <= 8 ) ? button : button - UP;
	}

	/**
	 *  return button number for button code
	 *
	 *  @param button button value
	 *  @return button code
	 */
	public static int buttonCode(int button) {	
		return ( button <= 8 ) ? button + UP : button;		
	}

	/**
	 *  return scene button Code for scene button number
	 *
	 *  @param button button value
	 *  @return scene button number
	 */	
	public static int sceneButtonNumber(int button) {
		return ( button <= 8 ) ? button : (button - SCENE_OFFSET + 8) / 16;				
	}

	/**
	 *  return scene button number for scene button code
	 *
	 *  @param button button value
	 *  @return scene button code
	 */	
	public static int sceneButtonCode(int button) {
		return ( button <= 8 ) ? (button * 16) - 8 + SCENE_OFFSET: button;
	}	

}
