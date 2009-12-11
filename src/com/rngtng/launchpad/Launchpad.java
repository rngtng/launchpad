/*
A nice wrapper class to controll the novation launchpad 

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

import java.util.Vector;

import javax.sound.midi.MidiMessage;

import processing.core.PApplet;
import processing.core.PImage;
import themidibus.MidiBus;
import themidibus.StandardMidiListener;

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

	public final String VERSION = "0.2";

	Vector<LaunchpadListener> listeners;

	public Launchpad(PApplet _app) {
		this(_app, "Launchpad", "Launchpad");
	}

	/**
	 * a Constructor, usually called in the setup() method in your sketch to
	 * initialize and start the library.
	 *
	 * @example Launchpad
	 * @param _app parent Applet
	 * @param inputName name of MIDI input Device
	 * @param outputName name of MIDI output Device
	 */
	public Launchpad(PApplet _app, String inputName, String outputName) {
		app = _app;
		app.registerDispose(this);
		midiBus = new MidiBus(_app, inputName, outputName);
		midiBus.addMidiListener(this);

		listeners = new Vector<LaunchpadListener>();
		addListener(new LaunchadPAppletListener(_app));		
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

	/* -- Listener Handling -- */

	/**
	 * 	Adds a listener who will be notified each time a new MIDI message is received from a MIDI input device. If the listener has already been added, it will not be added again.
	 *
	 * @param listener the listener to add.
	 * @return true if and only the listener was sucessfully added.
	 * @see #removeListener(LaunchpadListener listener)
	 */
	public boolean addListener(LaunchpadListener listener) {
		for(LaunchpadListener current : listeners) if(current == listener) return false;	
		listeners.add(listener);				
		return true;
	}

	/**
	 * Removes a given listener.
	 *
	 * @param listener the listener to remove.
	 * @return true if and only the listener was sucessfully removed.
	 * @see #addListener(LaunchpadListener listener)
	 */
	public boolean removeListener(LaunchpadListener listener) {
		for(LaunchpadListener current : listeners) {
			if(current == listener) {
				listeners.remove(listener);
				return true;
			}
		}
		return false;
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
	public void testLeds() {
		testLeds(LColor.HIGH);
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
	public void testLeds(int brightness) {
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
	public void changeButton(int buttonCode, LColor c) {
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
	public void changeButton(int buttonCode, int red, int green) {
		changeButton(buttonCode, red, green, LColor.NORMAL);
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param buttonCode Code of the button
	 * @param red   brightness of red LED
	 * @param green brightness of green LED
	 * @param mode brightness of green LED 
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(int buttonCode, int red, int green, int mode) {
		//if(!isButtonCode(buttonCode)) throw
		int status = (isSceneButtonCode(buttonCode)) ? STATUS_ON : STATUS_CC;
		output(status, buttonCode, LColor.velocity(red, green, mode));
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param c     LColor object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, LColor c) {
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
	public void changeGrid(int x, int y, int red, int green) {
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
	public void changeGrid(int x, int y, int red, int green, int mode) {
		output(STATUS_ON, (y * 16 + x), LColor.velocity(red, green, mode));
	}

	/**
	 * Changes all buttons in batch mode. First 64 are the buttons on the grid, then the scene buttons (top to bottom)
	 * followed by  the control buttons (left to right). Maximum 80 values - excessive values will be ignored, missing
	 * values will be filled with 0
	 *
	 * @param colors an array of Colors
	 * @see LColor
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void change_all(LColor[] colors) {
		int param1, param2;

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
	 * @param colors an array of integers
	 *
	 * Errors raised:
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void change_all(int[] colors) {
		int param1, param2;

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
		int param1, param2;

		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);
		image.loadPixels();

		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = (int) (4 * app.red(image.pixels[i]) / 255);
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = 0;
			} catch (NullPointerException e) {
				param1 = 0;
			}

			try {
				param2 = (int) (4 * app.green(image.pixels[i+1]) / 255);
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
		int data = display_buffer + 4 * update_buffer + 32;
		if(copy) data += 16;
		if(flashing) data += 8;
		output(STATUS_CC, STATUS_NIL, data);
	}

	/**
	 * Reads user actions (button presses/releases) that haven't been handled yet and invokes a button
	 * or grid event
	 *
	 * @param message the MIDI message
	 *
	 * Errors raised:
	 * [Launchpad::NoInputAllowedError] when input is not enabled
	 */
	public void midiMessage(MidiMessage message) {
		int code     = message.getStatus();
		int note     = message.getMessage()[1] & 0xFF;
		int velocity = message.getMessage()[2] & 0xFF;

		//process button event	
		if(code == STATUS_CC || (code == STATUS_ON || code == STATUS_OFF) && isSceneButtonCode(note)) {
			for(LaunchpadListener listener : listeners) {				
				if(velocity == 127) { 
					listener.launchpadButtonPressed(note);
				}
				else {
					listener.launchpadButtonReleased(note);			
				}					
			}			
			PApplet.println("Button :" + note); 
			return;
		}        

		//process grid event        
		if( code == STATUS_ON || code == STATUS_OFF) {
			for(LaunchpadListener listener : listeners) {				
				if(velocity == 127) { 
					listener.launchpadGridPressed(note % 16, note / 16);
				}
				else {
					listener.launchpadGridReleased(note % 16, note / 16);			
				}					
			}
			PApplet.println("x:" + (note % 16) + " y:" + (note / 16)); 
			return;
		}

		PApplet.print("Huston we've an unimplemented MIDI Message: " + message.getStatus());
		if(message.getMessage().length > 1) PApplet.print(" Param 1: " + (message.getMessage()[1] & 0xFF) );
		if(message.getMessage().length > 2) PApplet.print(" Param 2: " + (message.getMessage()[2] & 0xFF) );
		PApplet.println();
	}

	/** 
	 * Writes a messages to the MIDI device.
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
	 *  @return boolean if code is valid
	 */
	private boolean isButtonCode(int buttonCode) {
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
	private boolean isSceneButtonCode(int buttonCode) {
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
