package com.jfixby.r3.fokker.input;

import com.jfixby.cmns.api.input.Key;
import com.jfixby.cmns.api.input.Keyboard;
import com.jfixby.cmns.api.input.KeysList;

public final class FokkerKeyboard implements Keyboard {

	public final KeysList KEYS_LIST = new FokkerKeysList();;

	final Key ANY_KEY = new FokkerKeyCode("ANY_KEY", this); // -1;
	final Key NUM_0 = new FokkerKeyCode("0", this); // 7;
	final Key NUM_1 = new FokkerKeyCode("1", this); // 8;
	final Key NUM_2 = new FokkerKeyCode("2", this); // 9;
	final Key NUM_3 = new FokkerKeyCode("3", this); // 10;
	final Key NUM_4 = new FokkerKeyCode("4", this); // 11;
	final Key NUM_5 = new FokkerKeyCode("5", this); // 12;
	final Key NUM_6 = new FokkerKeyCode("6", this); // 13;
	final Key NUM_7 = new FokkerKeyCode("7", this); // 14;
	final Key NUM_8 = new FokkerKeyCode("8", this); // 15;
	final Key NUM_9 = new FokkerKeyCode("9", this); // 16;
	final Key A = new FokkerKeyCode("A", this); // 29;
	final Key ALT_LEFT = new FokkerKeyCode("L-Alt", this); // 57;
	final Key ALT_RIGHT = new FokkerKeyCode("R-Alt", this); // 58;
	final Key APOSTROPHE = new FokkerKeyCode("'", this); // 75;
	final Key AT = new FokkerKeyCode("@", this); // 77;
	final Key B = new FokkerKeyCode("B", this); // 30;
	final Key BACK = new FokkerKeyCode("Back", this); // 4;
	final Key BACKSLASH = new FokkerKeyCode("\\", this); // 73;
	final Key C = new FokkerKeyCode("C", this); // 31;
	final Key CALL = new FokkerKeyCode("Call", this); // 5;
	final Key CAMERA = new FokkerKeyCode("Camera", this); // 27;
	final Key CLEAR = new FokkerKeyCode("Clear", this); // 28;
	final Key COMMA = new FokkerKeyCode(",", this); // 55;
	final Key D = new FokkerKeyCode("D", this); // 32;

	/*
	 * These two keys work very different (weird) on different devices; Android
	 * slightly fucked up things here as usually. See hey have the same code. Be
	 * careful.
	 */
	final Key BACKSPACE = new FokkerKeyCode("BACKSPACE", this); // 67
	// final IKeyCode BACKWARD_DEL = new
	// KeyCode("Backward Delete", this); // 67

	final Key FORWARD_DEL = new FokkerKeyCode("Forward Delete", this); // 112;
	final Key DPAD_CENTER = new FokkerKeyCode("DPAD_CENTER", this); // 23;
	final Key DPAD_DOWN = new FokkerKeyCode("DPAD_DOWN", this); // 20;
	final Key DPAD_LEFT = new FokkerKeyCode("DPAD_LEFT", this); // 21;
	final Key DPAD_RIGHT = new FokkerKeyCode("DPAD_RIGHT", this); // 22;
	final Key DPAD_UP = new FokkerKeyCode("DPAD_UP", this); // 19;
	final Key CENTER = new FokkerKeyCode("Center", this); // 23;
	final Key DOWN = new FokkerKeyCode("Down", this); // 20;
	final Key LEFT = new FokkerKeyCode("Left", this); // 21;
	final Key RIGHT = new FokkerKeyCode("Right", this); // 22;
	final Key UP = new FokkerKeyCode("Up", this); // 19;
	final Key E = new FokkerKeyCode("E", this); // 33;
	final Key ENDCALL = new FokkerKeyCode("End Call", this); // 6;
	final Key ENTER = new FokkerKeyCode("Enter", this); // 66;
	final Key ENVELOPE = new FokkerKeyCode("Envelope", this); // 65;
	final Key EQUALS = new FokkerKeyCode("=", this); // 70;
	final Key EXPLORER = new FokkerKeyCode("Explorer", this); // 64;
	final Key F = new FokkerKeyCode("F", this); // 34;
	final Key FOCUS = new FokkerKeyCode("Focus", this); // 80;
	final Key G = new FokkerKeyCode("G", this); // 35;
	final Key GRAVE = new FokkerKeyCode("`", this); // 68;
	final Key H = new FokkerKeyCode("H", this); // 36;
	final Key HEADSETHOOK = new FokkerKeyCode("Headset Hook", this); // 79;
	final Key HOME = new FokkerKeyCode("Home", this); // 3;
	final Key I = new FokkerKeyCode("I", this); // 37;
	final Key J = new FokkerKeyCode("J", this); // 38;
	final Key K = new FokkerKeyCode("K", this); // 39;
	final Key L = new FokkerKeyCode("L", this); // 40;
	final Key LEFT_BRACKET = new FokkerKeyCode("[", this); // 71;
	final Key M = new FokkerKeyCode("M", this); // 41;
	final Key MEDIA_FAST_FORWARD = new FokkerKeyCode("Fast Forward", this); // 90;
	final Key MEDIA_NEXT = new FokkerKeyCode("Next Media", this); // 87;
	final Key MEDIA_PLAY_PAUSE = new FokkerKeyCode("Play/Pause", this); // 85;
	final Key MEDIA_PREVIOUS = new FokkerKeyCode("Prev Media", this); // 88;
	final Key MEDIA_REWIND = new FokkerKeyCode("Rewind", this); // 89;
	final Key MEDIA_STOP = new FokkerKeyCode("Stop Media", this); // 86;
	final Key MENU = new FokkerKeyCode("Menu", this); // 82;
	final Key MINUS = new FokkerKeyCode("-", this); // 69;
	final Key MUTE = new FokkerKeyCode("Mute", this); // 91;
	final Key N = new FokkerKeyCode("N", this); // 42;
	final Key NOTIFICATION = new FokkerKeyCode("Notification", this); // 83;
	final Key NUM = new FokkerKeyCode("Num", this); // 78;
	final Key O = new FokkerKeyCode("O", this); // 43;
	final Key P = new FokkerKeyCode("P", this); // 44;
	final Key PERIOD = new FokkerKeyCode(".", this); // 56;
	final Key PLUS = new FokkerKeyCode("Plus", this); // 81;
	final Key POUND = new FokkerKeyCode("#", this); // 18;
	final Key POWER = new FokkerKeyCode("Power", this); // 26;
	final Key Q = new FokkerKeyCode("Q", this); // 45;
	final Key R = new FokkerKeyCode("R", this); // 46;
	final Key RIGHT_BRACKET = new FokkerKeyCode("]", this); // 72;
	final Key S = new FokkerKeyCode("S", this); // 47;
	final Key SEARCH = new FokkerKeyCode("Search", this); // 84;
	final Key SEMICOLON = new FokkerKeyCode(";", this); // 74;
	final Key SHIFT_LEFT = new FokkerKeyCode("L-Shift", this); // 59;
	final Key SHIFT_RIGHT = new FokkerKeyCode("R-Shift", this); // 60;
	final Key SLASH = new FokkerKeyCode("/", this); // 76;
	final Key SOFT_LEFT = new FokkerKeyCode("Soft Left", this); // 1;
	final Key SOFT_RIGHT = new FokkerKeyCode("Soft Right", this); // 2;
	final Key SPACE = new FokkerKeyCode("Space", this); // 62;
	final Key STAR = new FokkerKeyCode("*", this); // 17;
	final Key SYM = new FokkerKeyCode("SYM", this); // 63;
	final Key T = new FokkerKeyCode("T", this); // 48;
	final Key TAB = new FokkerKeyCode("Tab", this); // 61;
	final Key U = new FokkerKeyCode("U", this); // 49;
	final Key UNKNOWN = new FokkerKeyCode("Unknown", this); // 0;
	final Key V = new FokkerKeyCode("V", this); // 50;
	final Key VOLUME_DOWN = new FokkerKeyCode("Volume Down", this); // 25;
	final Key VOLUME_UP = new FokkerKeyCode("Volume Up", this); // 24;
	final Key W = new FokkerKeyCode("W", this); // 51;
	final Key X = new FokkerKeyCode("X", this); // 52;
	final Key Y = new FokkerKeyCode("Y", this); // 53;
	final Key Z = new FokkerKeyCode("Z", this); // 54;
	final Key META_ALT_LEFT_ON = new FokkerKeyCode("META_ALT_LEFT_ON", this); // 16;
	final Key META_ALT_ON = new FokkerKeyCode("META_ALT_ON", this); // 2;
	final Key META_ALT_RIGHT_ON = new FokkerKeyCode("META_ALT_RIGHT_ON", this); // 32;
	final Key META_SHIFT_LEFT_ON = new FokkerKeyCode("META_SHIFT_LEFT_ON", this); // 64;
	final Key META_SHIFT_ON = new FokkerKeyCode("META_SHIFT_ON", this); // 1;
	final Key META_SHIFT_RIGHT_ON = new FokkerKeyCode("META_SHIFT_RIGHT_ON", this); // 128;
	final Key META_SYM_ON = new FokkerKeyCode("META_SYM_ON", this); // 4;
	final Key CONTROL_LEFT = new FokkerKeyCode("L-Ctrl", this); // 129;
	final Key CONTROL_RIGHT = new FokkerKeyCode("R-Ctrl", this); // 130;
	final Key ESCAPE = new FokkerKeyCode("Escape", this); // 131;
	final Key END = new FokkerKeyCode("End", this); // 132;
	final Key INSERT = new FokkerKeyCode("Insert", this); // 133;
	final Key PAGE_UP = new FokkerKeyCode("Page Up", this); // 92;
	final Key PAGE_DOWN = new FokkerKeyCode("Page Down", this); // 93;
	final Key PICTSYMBOLS = new FokkerKeyCode("PICTSYMBOLS", this); // 94;
	final Key SWITCH_CHARSET = new FokkerKeyCode("SWITCH_CHARSET", this); // 95;
	final Key BUTTON_CIRCLE = new FokkerKeyCode("BUTTON_CIRCLE", this); // 255;
	final Key BUTTON_A = new FokkerKeyCode("A Button", this); // 96;
	final Key BUTTON_B = new FokkerKeyCode("B Button", this); // 97;
	final Key BUTTON_C = new FokkerKeyCode("C Button", this); // 98;
	final Key BUTTON_X = new FokkerKeyCode("X Button", this); // 99;
	final Key BUTTON_Y = new FokkerKeyCode("Y Button", this); // 100;
	final Key BUTTON_Z = new FokkerKeyCode("Z Button", this); // 101;
	final Key BUTTON_L1 = new FokkerKeyCode("L1 Button", this); // 102;
	final Key BUTTON_R1 = new FokkerKeyCode("R1 Button", this); // 103;
	final Key BUTTON_L2 = new FokkerKeyCode("L2 Button", this); // 104;
	final Key BUTTON_R2 = new FokkerKeyCode("R2 Button", this); // 105;
	final Key BUTTON_THUMBL = new FokkerKeyCode("Left Thumb", this); // 106;
	final Key BUTTON_THUMBR = new FokkerKeyCode("Right Thumb", this); // 107;
	final Key BUTTON_START = new FokkerKeyCode("Start", this); // 108;
	final Key BUTTON_SELECT = new FokkerKeyCode("Select", this); // 109;
	final Key BUTTON_MODE = new FokkerKeyCode("Button Mode", this); // 110;

	final Key NUMPAD_0 = new FokkerKeyCode("Numpad 0", this); // 144;
	final Key NUMPAD_1 = new FokkerKeyCode("Numpad 1", this); // 145;
	final Key NUMPAD_2 = new FokkerKeyCode("Numpad 2", this); // 146;
	final Key NUMPAD_3 = new FokkerKeyCode("Numpad 3", this); // 147;
	final Key NUMPAD_4 = new FokkerKeyCode("Numpad 4", this); // 148;
	final Key NUMPAD_5 = new FokkerKeyCode("Numpad 5", this); // 149;
	final Key NUMPAD_6 = new FokkerKeyCode("Numpad 6", this); // 150;
	final Key NUMPAD_7 = new FokkerKeyCode("Numpad 7", this); // 151;
	final Key NUMPAD_8 = new FokkerKeyCode("Numpad 8", this); // 152;
	final Key NUMPAD_9 = new FokkerKeyCode("Numpad 9", this); // 153;

	final Key COLON = new FokkerKeyCode(":", this); // 243;
	final Key F1 = new FokkerKeyCode("F1", this); // 244;
	final Key F2 = new FokkerKeyCode("F2", this); // 245;
	final Key F3 = new FokkerKeyCode("F3", this); // 246;
	final Key F4 = new FokkerKeyCode("F4", this); // 247;
	final Key F5 = new FokkerKeyCode("F5", this); // 248;
	final Key F6 = new FokkerKeyCode("F6", this); // 249;
	final Key F7 = new FokkerKeyCode("F7", this); // 250;
	final Key F8 = new FokkerKeyCode("F8", this); // 251;
	final Key F9 = new FokkerKeyCode("F9", this); // 252;
	final Key F10 = new FokkerKeyCode("F10", this); // 253;
	final Key F11 = new FokkerKeyCode("F11", this); // 254;
	final Key F12 = new FokkerKeyCode("F12", this); // 255;

	@Override
	public Key ANY_KEY() {
		return this.ANY_KEY;
	}

	@Override
	public Key NUM_0() {
		return this.NUM_0;
	}

	@Override
	public Key NUM_1() {
		return NUM_1;
	}

	@Override
	public Key NUM_2() {
		return NUM_2;
	}

	@Override
	public Key NUM_3() {
		return NUM_3;
	}

	@Override
	public Key NUM_4() {
		return NUM_4;
	}

	@Override
	public Key NUM_5() {
		return NUM_5;
	}

	@Override
	public Key NUM_6() {
		return NUM_6;
	}

	@Override
	public Key NUM_7() {
		return NUM_7;
	}

	@Override
	public Key NUM_8() {
		return NUM_8;
	}

	@Override
	public Key NUM_9() {
		return NUM_9;
	}

	@Override
	public Key A() {
		return A;
	}

	@Override
	public Key ALT_LEFT() {
		return ALT_LEFT;
	}

	@Override
	public Key ALT_RIGHT() {
		return ALT_RIGHT;
	}

	@Override
	public Key APOSTROPHE() {
		return APOSTROPHE;
	}

	@Override
	public Key AT() {
		return AT;
	}

	@Override
	public Key B() {
		return B;
	}

	@Override
	public Key BACK() {
		return BACK;
	}

	@Override
	public Key BACKSLASH() {
		return BACKSLASH;
	}

	@Override
	public Key C() {
		return C;
	}

	@Override
	public Key CALL() {
		return CALL;
	}

	@Override
	public Key CAMERA() {
		return CAMERA;
	}

	@Override
	public Key CLEAR() {
		return CLEAR;
	}

	@Override
	public Key COMMA() {
		return COMMA;
	}

	@Override
	public Key D() {
		return D;
	}

	@Override
	public Key BACKSPACE() {
		return BACKSPACE;
	}

	@Override
	public Key FORWARD_DEL() {
		return FORWARD_DEL;
	}

	@Override
	public Key DPAD_CENTER() {
		return DPAD_CENTER;
	}

	@Override
	public Key DPAD_DOWN() {
		return DPAD_DOWN;
	}

	@Override
	public Key DPAD_LEFT() {
		return DPAD_LEFT;
	}

	@Override
	public Key DPAD_RIGHT() {
		return DPAD_RIGHT;
	}

	@Override
	public Key DPAD_UP() {
		return DPAD_UP;
	}

	@Override
	public Key CENTER() {
		return CENTER;
	}

	@Override
	public Key DOWN() {
		return DOWN;
	}

	@Override
	public Key LEFT() {
		return LEFT;
	}

	@Override
	public Key RIGHT() {
		return RIGHT;
	}

	@Override
	public Key UP() {
		return UP;
	}

	@Override
	public Key E() {
		return E;
	}

	@Override
	public Key ENDCALL() {
		return ENDCALL;
	}

	@Override
	public Key ENTER() {
		return ENTER;
	}

	@Override
	public Key ENVELOPE() {
		return ENVELOPE;
	}

	@Override
	public Key EQUALS() {
		return EQUALS;
	}

	@Override
	public Key EXPLORER() {
		return EXPLORER;
	}

	@Override
	public Key F() {
		return F;
	}

	@Override
	public Key FOCUS() {
		return FOCUS;
	}

	@Override
	public Key G() {
		return G;
	}

	@Override
	public Key GRAVE() {
		return GRAVE;
	}

	@Override
	public Key H() {
		return H;
	}

	@Override
	public Key HEADSETHOOK() {
		return HEADSETHOOK;
	}

	@Override
	public Key HOME() {
		return HOME;
	}

	@Override
	public Key I() {
		return I;
	}

	@Override
	public Key J() {
		return J;
	}

	@Override
	public Key K() {
		return K;
	}

	@Override
	public Key L() {
		return L;
	}

	@Override
	public Key LEFT_BRACKET() {
		return LEFT_BRACKET;
	}

	@Override
	public Key M() {
		return M;
	}

	@Override
	public Key MEDIA_FAST_FORWARD() {
		return MEDIA_FAST_FORWARD;
	}

	@Override
	public Key MEDIA_NEXT() {
		return MEDIA_FAST_FORWARD;
	}

	@Override
	public Key MEDIA_PLAY_PAUSE() {
		return MEDIA_PLAY_PAUSE;
	}

	@Override
	public Key MEDIA_PREVIOUS() {
		return MEDIA_PREVIOUS;
	}

	@Override
	public Key MEDIA_REWIND() {
		return MEDIA_REWIND;
	}

	@Override
	public Key MEDIA_STOP() {
		return MEDIA_STOP;
	}

	@Override
	public Key MENU() {
		return MENU;
	}

	@Override
	public Key MINUS() {
		return MINUS;
	}

	@Override
	public Key MUTE() {
		return MUTE;
	}

	@Override
	public Key N() {
		return N;
	}

	@Override
	public Key NOTIFICATION() {
		return NOTIFICATION;
	}

	@Override
	public Key NUM() {
		return NUM;
	}

	@Override
	public Key O() {
		return O;
	}

	@Override
	public Key P() {
		return P;
	}

	@Override
	public Key PERIOD() {
		return PERIOD;
	}

	@Override
	public Key PLUS() {
		return PLUS;
	}

	@Override
	public Key POUND() {
		return POUND;
	}

	@Override
	public Key POWER() {
		return POWER;
	}

	@Override
	public Key Q() {
		return Q;
	}

	@Override
	public Key R() {
		return R;
	}

	@Override
	public Key RIGHT_BRACKET() {
		return RIGHT_BRACKET;
	}

	@Override
	public Key S() {
		return S;
	}

	@Override
	public Key SEARCH() {
		return SEARCH;
	}

	@Override
	public Key SEMICOLON() {
		return SEMICOLON;
	}

	@Override
	public Key SHIFT_LEFT() {
		return SHIFT_LEFT;
	}

	@Override
	public Key SHIFT_RIGHT() {
		return SHIFT_RIGHT;
	}

	@Override
	public Key SLASH() {
		return SLASH;
	}

	@Override
	public Key SOFT_LEFT() {
		return SOFT_LEFT;
	}

	@Override
	public Key SPACE() {
		return SPACE;
	}

	@Override
	public Key SOFT_RIGHT() {
		return SOFT_RIGHT;
	}

	@Override
	public Key SYM() {
		return SYM;
	}

	@Override
	public Key T() {
		return T;
	}

	@Override
	public Key TAB() {
		return TAB;
	}

	@Override
	public Key U() {
		return U;
	}

	@Override
	public Key STAR() {
		return STAR;
	}

	@Override
	public Key UNKNOWN() {
		return UNKNOWN;
	}

	@Override
	public Key V() {
		return V;
	}

	@Override
	public Key VOLUME_DOWN() {
		return VOLUME_DOWN;
	}

	@Override
	public Key VOLUME_UP() {
		return VOLUME_UP;
	}

	@Override
	public Key W() {
		return W;
	}

	@Override
	public Key X() {
		return X;
	}

	@Override
	public Key Y() {
		return Y;
	}

	@Override
	public Key Z() {
		return Z;
	}

	@Override
	public Key META_ALT_LEFT_ON() {
		return META_ALT_LEFT_ON;
	}

	@Override
	public Key META_ALT_ON() {
		return META_ALT_ON;
	}

	@Override
	public Key META_ALT_RIGHT_ON() {
		return META_ALT_RIGHT_ON;
	}

	@Override
	public Key META_SHIFT_LEFT_ON() {
		return META_SHIFT_LEFT_ON;
	}

	@Override
	public Key META_SHIFT_ON() {
		return META_SHIFT_ON;
	}

	@Override
	public Key META_SHIFT_RIGHT_ON() {
		return META_SHIFT_RIGHT_ON;
	}

	@Override
	public Key META_SYM_ON() {
		return META_SYM_ON;
	}

	@Override
	public Key CONTROL_LEFT() {
		return CONTROL_LEFT;
	}

	@Override
	public Key CONTROL_RIGHT() {
		return CONTROL_RIGHT;
	}

	@Override
	public Key ESCAPE() {
		return ESCAPE;
	}

	@Override
	public Key END() {
		return END;
	}

	@Override
	public Key INSERT() {
		return INSERT;
	}

	@Override
	public Key PAGE_UP() {
		return PAGE_UP;
	}

	@Override
	public Key PAGE_DOWN() {
		return PAGE_DOWN;
	}

	@Override
	public Key PICTSYMBOLS() {
		return PICTSYMBOLS;
	}

	@Override
	public Key SWITCH_CHARSET() {
		return SWITCH_CHARSET;
	}

	@Override
	public Key BUTTON_CIRCLE() {
		return BUTTON_CIRCLE;
	}

	@Override
	public Key BUTTON_A() {
		return BUTTON_A;
	}

	@Override
	public Key BUTTON_B() {
		return BUTTON_B;
	}

	@Override
	public Key BUTTON_C() {
		return BUTTON_C;
	}

	@Override
	public Key BUTTON_X() {
		return BUTTON_X;
	}

	@Override
	public Key BUTTON_Y() {
		return BUTTON_Y;
	}

	@Override
	public Key BUTTON_Z() {
		return BUTTON_Z;
	}

	@Override
	public Key BUTTON_L1() {
		return BUTTON_L1;
	}

	@Override
	public Key BUTTON_R1() {
		return BUTTON_R1;
	}

	@Override
	public Key BUTTON_R2() {
		return BUTTON_R2;
	}

	@Override
	public Key BUTTON_L2() {
		return BUTTON_L2;
	}

	@Override
	public Key BUTTON_THUMBL() {
		return BUTTON_THUMBL;
	}

	@Override
	public Key BUTTON_START() {
		return BUTTON_START;
	}

	@Override
	public Key BUTTON_MODE() {
		return BUTTON_MODE;
	}

	@Override
	public Key BUTTON_SELECT() {
		return BUTTON_SELECT;
	}

	@Override
	public Key BUTTON_THUMBR() {
		return BUTTON_THUMBR;
	}

	@Override
	public Key NUMPAD_0() {
		return NUMPAD_0;
	}

	@Override
	public Key NUMPAD_1() {
		return NUMPAD_1;
	}

	@Override
	public Key NUMPAD_2() {
		return NUMPAD_2;
	}

	@Override
	public Key NUMPAD_3() {
		return NUMPAD_3;
	}

	@Override
	public Key NUMPAD_4() {
		return NUMPAD_4;
	}

	@Override
	public Key NUMPAD_5() {
		return NUMPAD_5;
	}

	@Override
	public Key NUMPAD_6() {
		return NUMPAD_6;
	}

	@Override
	public Key NUMPAD_7() {
		return NUMPAD_7;
	}

	@Override
	public Key NUMPAD_8() {
		return NUMPAD_8;
	}

	@Override
	public Key NUMPAD_9() {
		return NUMPAD_9;
	}

	@Override
	public Key COLON() {
		return COLON;
	}

	@Override
	public Key F1() {
		return F1;
	}

	@Override
	public Key F2() {
		return F2;
	}

	@Override
	public Key F3() {
		return F3;
	}

	@Override
	public Key F4() {
		return F4;
	}

	@Override
	public Key F5() {
		return F5;
	}

	@Override
	public Key F6() {
		return F6;
	}

	@Override
	public Key F7() {
		return F7;
	}

	@Override
	public Key F8() {
		return F8;
	}

	@Override
	public Key F9() {
		return F9;
	}

	@Override
	public Key F10() {
		return F10;
	}

	@Override
	public Key F11() {
		return F11;
	}

	@Override
	public Key F12() {
		return F12;
	}

	@Override
	public KeysList listAllKeys() {
		return this.KEYS_LIST;
	}
}
