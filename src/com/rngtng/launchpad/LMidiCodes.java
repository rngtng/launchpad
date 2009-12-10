package com.rngtng.launchpad;

public interface LMidiCodes {
	public final static int STATUS_NIL     = 0x00;
	public final static int STATUS_OFF     = 0x80;
	public final static int STATUS_ON      = 0x90;
	public final static int STATUS_MULTI   = 0x92;
	public final static int STATUS_CC      = 0xB0;

	public final static int BUTTON_UP      = 0x68;
	public final static int BUTTON_DOWN    = 0x69;
	public final static int BUTTON_LEFT    = 0x6A;
	public final static int BUTTON_RIGHT   = 0x6B;
	public final static int BUTTON_SESSION = 0x6C;
	public final static int BUTTON_USER1   = 0x6D;
	public final static int BUTTON_USER2   = 0x6E;
	public final static int BUTTON_MIXER   = 0x6F;

	public final static int BUTTON_SCENE1  = 0x08;
	public final static int BUTTON_SCENE2  = 0x18;
	public final static int BUTTON_SCENE3  = 0x28;
	public final static int BUTTON_SCENE4  = 0x38;
	public final static int BUTTON_SCENE5  = 0x48;
	public final static int BUTTON_SCENE6  = 0x58;
	public final static int BUTTON_SCENE7  = 0x68;
	public final static int BUTTON_SCENE8  = 0x78;

	public final static int VELOCITY_TEST_LEDS     = 0x7C;

	public final static int GRIDLAYOUT_XY          = 0x01;
	public final static int GRIDLAYOUT_DRUM_RACK   = 0x02;
}