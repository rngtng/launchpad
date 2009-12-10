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
public class Launchpad implements LMidiCodes, StandardMidiListener {

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
		midiBus.addMidiListener(this);
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
		test_leds(LColor.HIGH);
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
	 * @param buttonCode Code of the button
	 * @param c     LColor object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(byte buttonCode, LColor c) {
		changeButton(buttonCode, c.red, c.green, c.mode);
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param buttonCode Code of the button
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(byte buttonCode, byte red, byte green) {
		changeButton(buttonCode, red, green, LColor.NORMAL);
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param buttonCode Code of the button
	 * @param c     LColor object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(byte buttonCode, byte red, byte green, byte mode) {
	    //if(!isButtonCode(buttonCode)) throw
		int status = (isSceneButtonCode(buttonCode) ? STATUS_ON : STATUS_CC;
		output(status, buttonCode, LColor.velocity(red, green, mode));
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode  button mode
     *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, Color c) {
		changeGrid(x, y, c.red, c.green, c.mode);
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, byte red, byte green) {
		changeGrid(x, y, red, green, LColor.NORMAL);
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode  button mode
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, byte red, byte green, byte mode) {
		output(STATUS_ON, (y * 16 + x), LColor.velocity(red, green, mode));
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
				param1 = colors[i].velocity();
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = 0;
			} catch (NullPointerException e) {
				param1 = 0;
			}

			try {
				param2 = colors[i+1].velocity();
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

	/**
	 * Changes all buttons in batch mode. Using the pixel data of an PImage
	 *
	 * @param image an PImage object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
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
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void buffering_mode(int display_buffer, int update_buffer, boolean copy, boolean flashing) {
		byte data = (byte) (display_buffer + 4 * update_buffer + 32);
		if(copy) data += 16;
		if(flashing) data += 8;
		output(STATUS_CC, STATUS_NIL, data);
	}


    public void midiMessage(MidiMessage message) { // You can also use midiMessage(MidiMessage message, String bus_name)
    	// Receive a MidiMessage
    	// MidiMessage is an abstract class, the actual passed object will be either javax.sound.midi.MetaMessage, javax.sound.midi.ShortMessage, javax.sound.midi.SysexMessage.
    	// Check it out here http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/package-summary.html
    	app.println();
    	app.println("MidiMessage Data:");
    	app.println("--------");
    	app.println("Status Byte/MIDI Command:"+message.getStatus());
    	if(message.getMessage().length > 1) app.println("Param 1: "+(int)(message.getMessage()[1] & 0xFF));
    	if(message.getMessage().length > 2) app.println("Param 2: "+(int)(message.getMessage()[2] & 0xFF));
    }

    /**
    * Reads user actions (button presses/releases) that haven't been handled yet.
    *
    * Returns:
    *
    * an array of hashes with (see Launchpad for values):
    *
    * [<tt>:timestamp</tt>] integer indicating the time when the action occured
    * [<tt>:state</tt>]     state of the button after action
    * [<tt>:type</tt>]      type of the button
    * [<tt>:x</tt>]         x coordinate
    * [<tt>:y</tt>]         y coordinate
    *
    * Errors raised:
    * [Launchpad::NoInputAllowedError] when input is not enabled
    */
    private void read_pending_actions(MidiMessage midi_message) {
        int code     = message.getStatus();
        int note     = message.getMessage()[1] & 0xFF;
        int velocity = message.getMessage()[2] & 0xFF;

        if( status == Status::ON ) {
            r = (velocity == 127) ? GridDownEvent(note % 16, note / 16) : GridUpEvent(note % 16, note / 16);
        }
        if( status == Status::CC || (status == Status::ON && isSceneButtonCode(note)) ) {
            r = (velocity == 127) ? ButtonDownEvent(note) : ButtonUpEvent(note);
        }
    }

	/** Writes a messages to the MIDI device.
	 *
     */
	private void output(int status, byte data1, byte data2) {
		//raise NoOutputAllowedError if @output.nil?
		midiBus.sendMessage(new byte[]{(byte) status, data1, data2});
	}

	/** Writes a messages to the MIDI device.
	 *
     */
	private void output(int status, int data1, int data2) {
		//raise NoOutputAllowedError if @output.nil?
		midiBus.sendMessage(new byte[]{ (byte) status, (byte) data1, (byte) data2});
	}

	/**
	 *  checks for valid button code
	 *
	 *  @param buttonCode code of the button
	 *  @return boolean wether code is valid
	 */
	private boolean isButtonCode(byte buttonCode) {
		if(buttonCode == BUTTON_UP)      return true;
		if(buttonCode == BUTTON_DOWN)    return true;
		if(buttonCode == BUTTON_LEFT)    return true;
		if(buttonCode == BUTTON_RIGHT)   return true;
		if(buttonCode == BUTTON_SESSION) return true;
		if(buttonCode == BUTTON_USER1)   return true;
		if(buttonCode == BUTTON_USER2)   return true;
		if(buttonCode == BUTTON_MIXER)   return true;
		return isSceneButtonCode(buttonCode);
	}

	/**
	 *  checks for valid scene button code
	 *
	 *  @param buttonCode code of the button
	 *  @return boolean wether code is as scene button
	 */
	private boolean isSceneButtonCode(byte buttonCode) {
		if(buttonCode == BUTTON_SCENE1) return true;
		if(buttonCode == BUTTON_SCENE2) return true;
		if(buttonCode == BUTTON_SCENE3) return true;
		if(buttonCode == BUTTON_SCENE4) return true;
		if(buttonCode == BUTTON_SCENE5) return true;
		if(buttonCode == BUTTON_SCENE6) return true;
		if(buttonCode == BUTTON_SCENE7) return true;
		if(buttonCode == BUTTON_SCENE8) return true;
		return false;
	}
}
