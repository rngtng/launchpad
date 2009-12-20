package com.rngtng.launchpad;

/**
 * The Color class, to handle the MIDI Color and Mode codes
 *
 * @author rngtng - Tobias Bielohlawek
 *
 */
public class LColor {

	public final static int OFF           =  0;
	public final static int LOW           =  1;
	public final static int MEDIUM        =  2;
	public final static int HIGH          =  3;

	public final static int RED_OFF       =  OFF;
	public final static int RED_LOW       =  LOW;
	public final static int RED_MEDIUM    =  MEDIUM;
	public final static int RED_HIGH      =  HIGH;

	public final static int GREEN_OFFSET  =  16;
	public final static int GREEN_OFF     =  OFF * GREEN_OFFSET;
	public final static int GREEN_LOW     =  LOW * GREEN_OFFSET;
	public final static int GREEN_MEDIUM  =  MEDIUM * GREEN_OFFSET;
	public final static int GREEN_HIGH    =  HIGH * GREEN_OFFSET;

	public final static int YELLOW_OFF    =  RED_OFF + GREEN_OFF;
	public final static int YELLOW_LOW    =  RED_LOW + GREEN_LOW;
	public final static int YELLOW_MEDIUM =  RED_MEDIUM + GREEN_MEDIUM;
	public final static int YELLOW_HIGH   =  RED_HIGH + GREEN_HIGH;

	//Hack: I want NORMAL to be default MODE so I switched values of BUFFERED and NORMAL
	public final static int BUFFERED      =  12; // updates the LED for the current update_buffer only
	public final static int FLASHING      =   8; // flashing   updates the LED for flashing (the new value will be written to buffer 0 while the LED will be off in buffer 1, see buffering_mode)
	public final static int NORMAL        =   0; // updates the LED for all circumstances (the new value will be written to both buffers)

	private int red;
	private int green;
	private int mode;

	/**
	 * creates default  black color
	 */
	public LColor() {
		this(OFF);
	}

	/**
	 * creates color with given red and green value, green can be 0-3 or Constants
	 * @param red red value
	 * @param green green value
	 */
	public LColor(int red, int green) {
		this(red);
		setGreen(green);
	}

	/**
	 * creates color with given red and green value, green can be 0-3 or Constants
	 * @param red Red value
	 * @param green Green value
	 * @param mode Mode value 
	 */
	public LColor(int red, int green, int mode) {
		this(red + mode);
		setGreen(green);
	}	
	
	/**
	 * creates color with given red/green and mode value
	 * @param _color color value
	 */
	public LColor(int _color) {
		int red_and_mode = _color % GREEN_OFFSET;
		if( red_and_mode < FLASHING ) {
			this.setMode(NORMAL);		
		}
		else if( red_and_mode < BUFFERED ) {
			this.setMode(FLASHING);		
		}
		else {
			this.setMode(BUFFERED);
		}
		this.setRed(red_and_mode - this.getMode());
		this.setGreen(_color - red_and_mode);						
	}	

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public int velocity() {		
		//Hack switch values, as CONST have switch values for default reasons
		int mode = this.mode;
		if(mode == NORMAL) { 
			mode = BUFFERED; 
		}		
		else if(mode == BUFFERED) {
			mode = NORMAL;
		}				
		return this.green + this.red + mode;
	}

	/**
	 * Returns the value of Red
	 * 
	 * @return red value
	 */	
	public int getRed() {
		return red;
	}

	/**
	 * Set the value of Red
	 * 
	 * @param red
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * Returns the value of green
	 * 
	 * @return green value
	 */	
	public int getGreen() {
		return green;
	}

	/**
	 * Set the value of green
	 * 
	 * @param green
	 */
	public void setGreen(int green) {
		if(green < GREEN_OFFSET) green *= GREEN_OFFSET;
		this.green = green;
	}

	/**
	 * Returns the value of mode
	 * 
	 * @return mode value
	 */	
	public int getMode() {
		return mode;
	}

	/**
	 * Set the value of mode
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Compares color with given int value
	 * 
	 * @param color
	 * @return true if color is equal
	 */
	public boolean is(int color) {
		return velocity() == new LColor(color).velocity();
	}

	/**
	 * Compares color with given LColor value
	 * 
	 * @param color
	 * @return true if color is equal
	 */
	public boolean is(LColor color) {
		return velocity() == color.velocity();
	} 	 

	/**
	 * Compares red color value with given int value
	 * 
	 * @param red
	 * @return true if color is equal
	 */
	public boolean isRed(int red) {
		return getRed() == new LColor(red).getRed();
	}

	/**
	 * Compares red color value with given int value
	 * 
	 * @param green
	 * @return true if color is equal
	 */
	public boolean isGreen(int green) {
		return getGreen() == new LColor(green).getGreen();
	}

	/**
	 * Compares red color value with given int value
	 * 
	 * @param mode
	 * @return true if color is equal
	 */
	public boolean isMode(int mode) {
		return getMode() == new LColor(mode).getMode();
	}
}