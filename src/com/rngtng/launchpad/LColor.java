package com.rngtng.launchpad;

public class LColor {

	public final static int OFF           =  0;
	public final static int LOW           =  1;
	public final static int MEDIUM        =  2;
	public final static int HIGH          =  3;

	public final static int RED_OFF       =  0;
	public final static int RED_LOW       =  1;
	public final static int RED_MEDIUM    =  2;
	public final static int RED_HIGH      =  3;

	public final static int GREEN_OFF     =  0;
	public final static int GREEN_LOW     =  1;
	public final static int GREEN_MEDIUM  =  2;
	public final static int GREEN_HIGH    =  3;

	public final static int BUFFERED      =  0; // updates the LED for all circumstances (the new value will be written to both buffers)
	public final static int NORMAL        =  8; // flashing   updates the LED for flashing (the new value will be written to buffer 0 while the LED will be off in buffer 1, see buffering_mode)
	public final static int FLASHING      = 12; // updates the LED for the current update_buffer only

	int red;
	int green;
	int mode;

	LColor() {
		this(RED_OFF, GREEN_OFF, NORMAL);
	}

	LColor(int _red, int _green) {
		this(_red, _green, NORMAL);
	}

	LColor(int _red, int _green, int _mode) {
		this.red = _red;
		this.green = _green;
		this.mode = _mode;
	}

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public static int velocity(int red, int green) {
		return velocity(red, green, NORMAL);
	}
	
	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode  button mode
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public static int velocity(int red, int green, int mode) {
		return (int) (16 * green + red + mode);
	}

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */	
	public int velocity() {
		return LColor.velocity(this.red, this.green, this.mode);
	}
}