/*
A nice wrapper class to control the novation launchpad 

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
import themidibus.*;

/**
 * This is the Main class to control your Launchpad. Please the Docs and Examples for usage
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

	public final String VERSION = "0.2.1";

	boolean connected;

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
		this.app = _app;
		this.connected = false;
		app.registerDispose(this);
		midiBus = new MidiBus(_app);
		if(midiBus.addInput(inputName) && midiBus.addOutput(outputName)) {		
			midiBus.addMidiListener(this);

			listeners = new Vector<LaunchpadListener>();
			addListener(new LaunchadPAppletListener(_app));
			reset();
			this.connected = true;
		}
	}

	public void dispose() {
		reset();
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
	 * @return wheter launchpad is connected
	 */
	public boolean connected() {
		return this.connected;
	}
	
	/* -- Listener Handling -- */

	/**
	 * 	Adds a listener who will be notified each time a new MIDI message is received from a MIDI input device. If the listener has already been added, it will not be added again.
	 *
	 * @param listener the listener to add.
	 * @return true if and only the listener was successfully added.
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
	 * @return true if and only the listener was successfully removed.
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
	 * @param button value of the button (number or code)
	 * @param color    color value
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(int button, int color) {
		changeButton(button, new LColor(color));
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param button value of the button (number or code)
	 * @param c     LColor object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeButton(int button, LColor c) {
		output(STATUS_CC, LButton.buttonCode(button), c.velocity());		
	}	


	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param button value of the button (number or code)
	 * @param color    color value
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeSceneButton(int button, int color) {
		changeSceneButton(button, new LColor(color));
	}

	/**
	 * Changes a single Control or Scene Button. Specify the Button by its name
	 *
	 * @param button value of the button (number or code)
	 * @param c     LColor object
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeSceneButton(int button, LColor c) {
		output(STATUS_ON, LButton.sceneButtonCode(button) - LButton.SCENE_OFFSET, c.velocity());		
	}

	/**
	 * Changes a single Button on the Grid. Specify the Button by its x & y coordinates
	 *
	 * @param x     x coordinate
	 * @param y     y coordinate
	 * @param color    color value
	 *
	 * Errors raised:
	 * [Launchpad::NoValidGridCoordinatesError] when coordinates aren't within the valid range
	 * [Launchpad::NoValidBrightnessError] when brightness values aren't within the valid range
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void changeGrid(int x, int y, int color) {
		changeGrid(x, y, new LColor(color));
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
		output(STATUS_ON, (y * 16 + x), c.velocity());
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
	public void changeAll(LColor[] colors) {
		int param1, param2;

		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);

		int default_color = new LColor().velocity();
		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = colors[i].velocity();
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = default_color;
			} catch (NullPointerException e) {
				param1 = default_color;
			}

			try {
				param2 = colors[i+1].velocity();
			}   catch (ArrayIndexOutOfBoundsException e) {
				param2 = default_color;
			}  catch (NullPointerException e) {
				param2 = default_color;
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
	public void changeAll(int[] colors) {
		int param1, param2;

		// send normal MIDI message to reset rapid LED change pointer
		//  in this case, set mapping mode to x-y layout (the default)
		output(STATUS_CC, STATUS_NIL, GRIDLAYOUT_XY);

		int default_color = new LColor().velocity();
		//  send colors in slices of 2
		for(int i = 0; i < 80; i = i + 2) {
			try {
				param1 = colors[i];
			} catch (ArrayIndexOutOfBoundsException e) {
				param1 = default_color;
			} catch (NullPointerException e) {
				param1 = default_color;
			}

			try {
				param2 = colors[i+1];
			}
			catch (ArrayIndexOutOfBoundsException e) {
				param2 = default_color;
			}  catch (NullPointerException e) {
				param2 = default_color;
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
	public void changeAll(PImage image) {
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
	public void flashingOn() {
		bufferingMode(BUFFER0, BUFFER0);
	}

	/**
	 * Switches LEDs marked as flashing off when using custom timer for flashing.
	 *
	 * Errors raised:
	 *
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void flashingOff() {
		bufferingMode(BUFFER1, BUFFER0);
	}

	/**
	 * Starts flashing LEDs marked as flashing automatically.
	 * Stop flashing by calling flashing_on or flashing_off.
	 *
	 * Errors raised:
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void flashingAuto()  {
		bufferingMode(BUFFER0, BUFFER0, MODE_FLASHING);
	}

	/**
	 * Controls the two buffers.
	 *
	 * @param display_buffer which buffer to use for display, defaults to +0+
	 * @param update_buffer  which buffer to use for updates when <tt>:mode</tt> is set to <tt>:buffering</tt>, defaults to +0+ (see change)
	 *
	 * Errors raised:
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void bufferingMode(int display_buffer, int update_buffer) {
		bufferingMode(BUFFER0, BUFFER0, 0);
	}

	/**
	 * Controls the two buffers.
	 *
	 * @param display_buffer which buffer to use for display, defaults to +0+
	 * @param update_buffer  which buffer to use for updates when <tt>:mode</tt> is set to <tt>:buffering</tt>, defaults to +0+ (see change)
	 * @param flags          values to control FLASHING and COPY
	 *
	 * Errors raised:
	 * [Launchpad::NoOutputAllowedError] when output is not enabled
	 */
	public void bufferingMode(int display_buffer, int update_buffer, int flags) {
		int data = display_buffer + 4 * update_buffer + 32 + flags;
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
	public void midiMessage(MidiMessage message, long timestamp) {
		int code     = message.getStatus();
		int note     = message.getMessage()[1] & 0xFF;
		int velocity = message.getMessage()[2] & 0xFF;

		//process button event	
		if(code == STATUS_CC) {
			for(LaunchpadListener listener : listeners) {				
				if(velocity == 127) { 
					listener.launchpadButtonPressed(note);
				}
				else {
					listener.launchpadButtonReleased(note);			
				}					
			}			
			//PApplet.println("Button :" + note); 
			return;
		}        

		//process grid event        
		if( code == STATUS_ON || code == STATUS_OFF) {

			if( LButton.isSceneButtonCode(note + LButton.SCENE_OFFSET) )  {
				for(LaunchpadListener listener : listeners) {				
					if(velocity == 127) { 
						listener.launchpadSceneButtonPressed(note + LButton.SCENE_OFFSET );
					}
					else {
						listener.launchpadSceneButtonReleased(note + LButton.SCENE_OFFSET);			
					}					
				}
				// PApplet.println("SceneButton :" + note);  
				return;
			}
			else {
				for(LaunchpadListener listener : listeners) {				
					if(velocity == 127) { 
						listener.launchpadGridPressed(note % 16, note / 16);
					}
					else {
						listener.launchpadGridReleased(note % 16, note / 16);			
					}					
				}
			}
			// PApplet.println("x:" + (note % 16) + " y:" + (note / 16)); 
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

}
