/*


(c) copyright 2009 by rngtng - Tobias Bielohlawek

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General
Public License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330,
Boston, MA  02111-1307  USA
 */

package com.rngtng.launchpad;

import processing.core.PApplet;
import processing.core.PImage;
import themidibus.*;

/**
 * this is a template class and can be used to start a new processing library.
 * make sure you rename this class as well as the name of the package template
 * this class belongs to.
 *
 * @example Launchpad
 * @author rngtng - Tobias Bielohlawek
 *
 */
public class Launchpad implements MidiCodes {

	PApplet app;
	MidiBus midiBus; // The MidiBus

	public static int width = 8;
	public static int height = width;

	public final String VERSION = "0.1";

	public Launchpad(PApplet _app) {
		this(_app, "Launchpad - Launchpad", "Launchpad - Launchpad");
	}

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 *
	 * @example Launchpad
	 * @param theParent
	 * @param inputName name of MIDI input Device
	 * @param outputName name of MIDI output Device
	 */
	public Launchpad(PApplet _app, String inputName, String outputName) {
		app = _app;
		app.registerDispose(this);
		midiBus = new MidiBus(_app, inputName, outputName);
	}

	public void dispose() {
		midiBus.close();
	}

	/**
	 * return the version of the library.
	 *
	 * @return String version number
	 */
	public String version() {
		return VERSION;
	}

	/**
	 * Resets the launchpad - all settings are reset and all LEDs are switched off.
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void reset() {
		output(STATUS_CC, STATUS_NIL, STATUS_NIL);
	}

	/**
	 * Lights all LEDs (for testing purposes).
	 *
	 * Parameters (see Launchpad for values):
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void test_leds() {		
		test_leds(Color.HIGH);
	}

	/**
	 * Lights all LEDs (for testing purposes).
	 *
	 * Parameters (see Launchpad for values):
	 *
	 * @param brightness brightness of both LEDs for all buttons
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void test_leds(byte brightness) {
		if(brightness == 0) {
			reset();
		}
		else {
			output(STATUS_CC, STATUS_NIL, VELOCITY_TEST_LEDS + brightness);
		}
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param buttonName Name of the button
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode  button mode, defaults to <tt>MODE_NORMAL</tt>, one of:
	 *                   MODE_NORMAL   updates the LED for all circumstances (the new value will be written to both buffers)
	 *                   MODE_FLASHING flashing   updates the LED for flashing (the new value will be written to buffer 0 while the LED will be off in buffer 1, see buffering_mode)
	 *                   MODE_BUFFERED updates the LED for the current update_buffer only
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(String buttonName, Color c) {
		changeButton(buttonName, c.red, c.green, c.mode);
	}

	public void changeButton(String buttonName, byte red, byte green) {
		changeButton(buttonName, red, green, Color.NORMAL);
	}

	public void changeButton(String buttonName, byte red, byte green, byte mode) {
		int status = (" up down left right session user1 user2 mixer ".indexOf(" " + buttonName + " ") >= 0) ? STATUS_CC : STATUS_ON;
		output(status, buttonCode(buttonName), Color.velocity(red, green, mode));
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode  button mode, defaults to <tt>MODE_NORMAL</tt>, one of:
	 *                   MODE_NORMAL   updates the LED for all circumstances (the new value will be written to both buffers)
	 *                   MODE_FLASHING flashing   updates the LED for flashing (the new value will be written to buffer 0 while the LED will be off in buffer 1, see buffering_mode)
	 *                   MODE_BUFFERED updates the LED for the current update_buffer only
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, Color c) {
		changeGrid(x, y, c.red, c.green, c.mode);
	}

	public void changeGrid(int x, int y, byte red, byte green) {
		changeGrid(x, y, red, green, Color.NORMAL);
	}

	public void changeGrid(int x, int y, byte red, byte green, byte mode) {
		output(STATUS_ON, (y * 16 + x), Color.velocity(red, green, mode));
	}

	/**
	 * Changes all buttons in batch mode. First 64 are the buttons on the grid, then the scene buttons (top to bottom)
	 * followed by  the control buttons (left to right). Maximum 80 values - excessive values will be ignored, missing
	 * values will be filled with 0
	 *
	 * @param colors an array of Colors
	 * @see Color
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void change_all(Color[] colors) {
		byte param1, param2;
		
		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);

		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = colors[i].to_byte();
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = 0;
			} catch (NullPointerException e) {
				param1 = 0;
			}

			try {
				param2 = colors[i+1].to_byte();
			}   catch (ArrayIndexOutOfBoundsException e) {
				param2 = 0;
			}  catch (NullPointerException e) {
				param2 = 0;
			}
			output(STATUS_MULTI, param1, param2);
		}	
	}

	/**
	 * Changes all buttons in batch mode. First 64 are the buttons on the grid, then the scene buttons (top to bottom)
	 * followed by  the control buttons (left to right). Maximum 80 values - excessive values will be ignored, missing
	 * values will be filled with 0
	 *
	 * @param colors an array of bytes
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void change_all(byte[] colors) {
		byte param1, param2;

		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);

		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = colors[i];
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = 0;
			} catch (NullPointerException e) {
				param1 = 0;
			}

			try {
				param2 = colors[i+1];
			}
			catch (ArrayIndexOutOfBoundsException e) {
				param2 = 0;
			}  catch (NullPointerException e) {
				param2 = 0;
			}
			output(STATUS_MULTI, param1, param2);			
		}
	}

	public void change_all(PImage image) {
		byte param1, param2;

		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);
		image.loadPixels();

		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = (byte) (4 * app.red(image.pixels[i]) / 255);
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = 0;
			} catch (NullPointerException e) {
				param1 = 0;
			}

			try {
				param2 = (byte) (4 * app.green(image.pixels[i+1]) / 255);
			} catch (ArrayIndexOutOfBoundsException e) {
				param2 = 0;
			} catch (NullPointerException e) {
				param2 = 0;
			}
			output(STATUS_MULTI, param1, param2);
		}
	}



	/**
	 * Switches LEDs marked as flashing on when using custom timer for flashing.
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void flashing_on() {
		buffering_mode(0, 0, false, false);
	}

	/**
	 * Switches LEDs marked as flashing off when using custom timer for flashing.
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void flashing_off() {
		buffering_mode(1, 0, false, false);
	}

	/**
	 * Starts flashing LEDs marked as flashing automatically.
	 * Stop flashing by calling flashing_on or flashing_off.
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void flashing_auto()  {
		buffering_mode(0, 0, false, true);
	}


	public void buffering_mode() {
		buffering_mode(0, 0, false, false);
	}

	/**
	 * Controls the two buffers.
	 *
	 * Optional options hash:
	 *
	 * @param display_buffer which buffer to use for display, defaults to +0+
	 * @param update_buffer  which buffer to use for updates when <tt>:mode</tt> is set to <tt>:buffering</tt>, defaults to +0+ (see change)
	 * @param copy           whether to copy the LEDs states from the new display_buffer over to the new update_buffer, <tt>true/false</tt>, defaults to <tt>false</tt>
	 * @param flashing       whether to start flashing by automatically switching between the two buffers for display, <tt>true/false</tt>, defaults to <tt>false</tt>
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void buffering_mode(int display_buffer, int update_buffer, boolean copy, boolean flashing) {
		byte data = (byte) (display_buffer + 4 * update_buffer + 32);
		if(copy) data += 16;
		if(flashing) data += 8;
		output(STATUS_CC, STATUS_NIL, data);
	}


	// Writes several messages to the MIDI device.
	//
	// Parameters:
	//
	// [+messages+]  an array of hashes (usually created with message) with:
	//                 @param message  an array of
	//                                       MIDI status code,
	//                                       MIDI data 1 (note),
	//                                       MIDI data 2 (velocity)
	//                 @param timestampinteger indicating the time when the MIDI message was created
	private void output(int status, byte data1, byte data2) {
		//raise NoOutputAllowedError if @output.nil?		
		midiBus.sendMessage(new byte[]{(byte) status, data1, data2});
	}

	private void output(int status, int data1, int data2) {
		//raise NoOutputAllowedError if @output.nil?		
		midiBus.sendMessage(new byte[]{ (byte) status, (byte) data1, (byte) data2});
	}	
	
	/**
	 *  map the button name to button code
	 *
	 *  Parameters (see Launchpad for values):
	 *
	 *  [+button+] name of the button
	 *
	 *  Returns:
	 *
	 *  integer to be used for MIDI data 1
	 *
	 *  Errors raised:
	 *
	 *  [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 */
	private byte buttonCode(String button_name) {
		if(button_name == "up"     ) return CONTROLBUTTON_UP;
		if(button_name == "down"   ) return CONTROLBUTTON_DOWN;
		if(button_name == "left"   ) return CONTROLBUTTON_LEFT;
		if(button_name == "right"  ) return CONTROLBUTTON_RIGHT;
		if(button_name == "session") return CONTROLBUTTON_SESSION;
		if(button_name == "user1"  ) return CONTROLBUTTON_USER1;
		if(button_name == "user2"  ) return CONTROLBUTTON_USER2;
		if(button_name == "mixer"  ) return CONTROLBUTTON_MIXER;
		if(button_name == "scene1" ) return SCENEBUTTON_SCENE1;
		if(button_name == "scene2" ) return SCENEBUTTON_SCENE2;
		if(button_name == "scene3" ) return SCENEBUTTON_SCENE3;
		if(button_name == "scene4" ) return SCENEBUTTON_SCENE4;
		if(button_name == "scene5" ) return SCENEBUTTON_SCENE5;
		if(button_name == "scene6" ) return SCENEBUTTON_SCENE6;
		if(button_name == "scene7" ) return SCENEBUTTON_SCENE7;
		if(button_name == "scene8" ) return SCENEBUTTON_SCENE8;
		return 0;
		//raise NoValidGridCoordinatesError.new("you need to specify valid coordinates (x/y, 0-7, from top left), you specified: x=#{x}, y=#{y}") if x < 0 || x > 7 || y < 0 || y > 7
	}
}
