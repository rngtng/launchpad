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


class MonomicLaunchpad extends Monome implements LaunchpadListener {

    private LColor color_on, color_off;
    Launchpad launchpad;
    
    MonomicLaunchpad(Object listener) {
      super(listener);
      launchpad = new Launchpad( (PApplet) listener);
      launchpad.addListener(this);      
      color_on  = new LColor(LColor.RED_HIGH, LColor.GREEN_OFF);
      color_off = new LColor(LColor.RED_OFF, LColor.GREEN_OFF);
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
		//sendSerial((byte) ((ROW_PREFIX << 4) + i), bitVals);
	}

	public void setCol(int i, byte bitVals) {
		super.setCol(i, bitVals);
		//sendSerial((byte) ((COL_PREFIX << 4) + i), bitVals);
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
}