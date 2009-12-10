package com.rngtng.launchpad;

public class LColor {

	public final static byte OFF           =  0;
	public final static byte LOW           =  1;
	public final static byte MEDIUM        =  2;
	public final static byte HIGH          =  3;

	public final static byte RED_OFF       =  0;
	public final static byte RED_LOW       =  1;
	public final static byte RED_MEDIUM    =  2;
	public final static byte RED_HIGH      =  3;

	public final static byte GREEN_OFF     =  0;
	public final static byte GREEN_LOW     =  1;
	public final static byte GREEN_MEDIUM  =  2;
	public final static byte GREEN_HIGH    =  3;

	public final static byte BUFFERED      =  0; // updates the LED for all circumstances (the new value will be written to both buffers)
	public final static byte NORMAL        =  8; // flashing   updates the LED for flashing (the new value will be written to buffer 0 while the LED will be off in buffer 1, see buffering_mode)
	public final static byte FLASHING      = 12; // updates the LED for the current update_buffer only

	byte red;
	byte green;
	byte mode;

	LColor() {
		this(RED_OFF, GREEN_OFF, NORMAL);
	}

	LColor(byte _red, byte _green) {
		this(_red, _green, NORMAL);
	}

	LColor(byte _red, byte _green, byte _mode) {
		this.red = _red;
		this.green = _green;
		this.mode = _mode;
	}

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * [<tt>:red</tt>]   brightness of red LED
	 * [<tt>:green</tt>] brightness of green LED
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public static byte velocity(byte red, byte green) {
		return velocity(red, green, NORMAL);
	}
	
	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * [<tt>:red</tt>]   brightness of red LED
	 * [<tt>:green</tt>] brightness of green LED
	 * [<tt>:mode</tt>]  button mode
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public static byte velocity(byte red, byte green, byte mode) {
		return (byte) (16 * green + red + mode);
	}

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * [<tt>:red</tt>]   brightness of red LED
	 * [<tt>:green</tt>] brightness of green LED
	 * [<tt>:mode</tt>]  button mode
	 *
	 * @return integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */	
	public byte velocity() {
		return LColor.velocity(this.red, this.green, this.mode);
	}
}