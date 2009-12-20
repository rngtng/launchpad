/*
 A wrapper class to add PApplet LaunchpadListener methods

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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import processing.core.PApplet;

/**
 * A wrapper class to to PApplet into a LaunchpadListerner for generic Listener handling 
 *
 * @author rngtng - Tobias Bielohlawek
 *
 */
public class LaunchadPAppletListener implements LaunchpadListener {

	PApplet app;

	private Method buttonPressedMethod;
	private Method buttonReleasedMethod;
	
	private Method sceneButtonPressedMethod;
	private Method sceneButtonReleasedMethod;
	
	private Method gridPressedMethod;
	private Method gridReleasedMethod;

	LaunchadPAppletListener(PApplet _app) {
		app = _app;
		getMethods(app);
	}

	protected void getMethods(Object parent) {
		Class[] argsButton = new Class[] { int.class};
		try {
			buttonPressedMethod = parent.getClass().getDeclaredMethod( "launchpadButtonPressed", argsButton);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
		try {
			buttonReleasedMethod = parent.getClass().getDeclaredMethod( "launchpadButtonReleased", argsButton);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
		
		try {
			sceneButtonPressedMethod = parent.getClass().getDeclaredMethod( "launchpadSceneButtonPressed", argsButton);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
		try {
			sceneButtonReleasedMethod = parent.getClass().getDeclaredMethod( "launchpadSceneButtonReleased", argsButton);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
		
		Class[] argsGrid = new Class[] {int.class, int.class};
		try {
			gridPressedMethod = parent.getClass().getDeclaredMethod( "launchpadGridPressed", argsGrid);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
		try {
			gridReleasedMethod = parent.getClass().getDeclaredMethod( "launchpadGridReleased", argsGrid);
		} catch (NoSuchMethodException e) {
			// not a big deal if they aren't implemented
		}
	}

	public void launchpadButtonPressed(int buttonCode) {
		if (buttonPressedMethod == null) return;			
		try {
			buttonPressedMethod.invoke(app, new Object[]{ buttonCode }); // param is: buttonCode
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}   
	}

	public void launchpadButtonReleased(int buttonCode) {
		if (buttonReleasedMethod == null) return;			
		try {
			buttonReleasedMethod.invoke(app, new Object[]{ buttonCode }); // param is: buttonCode
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}  		
	}
	
	public void launchpadSceneButtonPressed(int buttonCode) {
		if (sceneButtonPressedMethod == null) return;			
		try {
			sceneButtonPressedMethod.invoke(app, new Object[]{ buttonCode }); // param is: buttonCode
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}   
	}

	public void launchpadSceneButtonReleased(int buttonCode) {
		if (sceneButtonReleasedMethod == null) return;			
		try {
			sceneButtonReleasedMethod.invoke(app, new Object[]{ buttonCode }); // param is: buttonCode
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}  		
	}
	public void launchpadGridPressed(int x, int y) {		
		if(gridPressedMethod == null) return;
		try {
			gridPressedMethod.invoke(app, new Object[]{x, y}); // params are: x, y
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}		
	}

	public void launchpadGridReleased(int x, int y) {
		if(gridReleasedMethod == null) return;
		try {
			gridReleasedMethod.invoke(app, new Object[]{x, y}); // params are: x, y
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}			
	}
	
}
