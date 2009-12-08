package com.rngtng.launchpad;

public class Color {

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

	public final static byte BUFFERED      =  0;
	public final static byte NORMAL        =  8;
	public final static byte FLASHING      = 12;

	byte red;
	byte green;
	byte mode;

	Color() {
		this(RED_OFF, GREEN_OFF, NORMAL);
	}

	Color(byte _red, byte _green) {
		this(_red, _green, NORMAL);
	}

	Color(byte _red, byte _green, byte _mode) {
		this.red = _red;
		this.green = _green;
		this.mode = _mode;
	}

	/**
	 * Calculates the MIDI data 2 value (velocity) for given brightness and mode values.
	 *
	 * Options hash:
	 *
	 * [<tt>:red</tt>]   brightness of red LED
	 * [<tt>:green</tt>] brightness of green LED
	 * [<tt>:mode</tt>]  button mode, defaults to <tt>:normal</tt>, one of:
	 *                   [<tt>:normal/tt>]     updates the LED for all circumstances (the new value will be written to both buffers)
	 *                   [<tt>:flashing/tt>]   updates the LED for flashing (the new value will be written to buffer 0 while in buffer 1, the value will be :off, see )
	 *                   [<tt>:buffering/tt>]  updates the LED for the current update_buffer only
	 *
	 * Returns:
	 *
	 * integer to be used for MIDI data 2
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 */
	public static byte velocity(byte red, byte green, byte mode) {
		return (byte) (16 * green + red + mode);
	}

	public byte to_byte() {
		return Color.velocity(this.red, this.green, this.mode);
	}
}