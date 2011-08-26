/*
 An implementation of the Monome API for launchpad

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

import jklabs.monomic.Monome;
import processing.core.PApplet;


/**
 * An implementation of the Monomic API to get your Launchpad working with Monome stuff
 *
 * @author rngtng - Tobias Bielohlawek
 *
 */
public class MonomeLaunchpad extends Monome implements LaunchpadListener {

    private LColor color_on, color_off;
    Launchpad launchpad;
    
    public MonomeLaunchpad(Object listener) {
      super(listener);
      launchpad = new Launchpad( (PApplet) listener);
      launchpad.addListener(this);      
      color_on  = new LColor(LColor.RED_HIGH); //full Red
      color_off = new LColor(); //Black
      launchpad.reset();
    }

public MonomeLaunchpad(Object listener, String inputName, String outputName) {
        super(listener);
        launchpad = new Launchpad( (PApplet) listener, inputName, outputName);
        launchpad.addListener(this);      
        color_on  = new LColor(LColor.RED_HIGH); //full Red
        color_off = new LColor(); //Black
        launchpad.reset();
      }
    
	////////////////////////////////////////////////// monome functions

	public void testPattern(boolean b) {
		super.testPattern(b);
		launchpad.testLeds();
		//sendSerial(LED_TEST, (byte) (b ? 1 : 0));
	}

	public void setValue(int x, int y, int value) {
		super.setValue(x, y, value);
		launchpad.changeGrid(x, y, (value == 1) ? color_on : color_off);
	}

	public void setRow(int i, byte bitVals) {
		super.setRow(i, bitVals);
		for(int k = 0; k < 8; k++) {
			launchpad.changeGrid(k, i, (((bitVals >> k) & 1) == 1) ? color_on : color_off);
		}
	}

	public void setCol(int i, byte bitVals) {
		super.setCol(i, bitVals);
		for(int k = 0; k < 8; k++) {
			launchpad.changeGrid(i, k, (((bitVals >> k) & 1) == 1) ? color_on : color_off);
		}
	}

////////////////////////////////////////////////// listener functions
	
	public synchronized void launchpadGridPressed(int x, int y) {
		super.handleInputEvent(x, y, 1);
	}

	public synchronized void launchpadGridReleased(int x, int y) {
		super.handleInputEvent(x, y, 0);
	}

	public void launchpadButtonPressed(int buttonCode) {	
	}

	public void launchpadButtonReleased(int buttonCode) {
	}

	public void launchpadSceneButtonPressed(int buttonCode) {		
	}

	public void launchpadSceneButtonReleased(int buttonCode) {
	}
}