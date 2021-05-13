package com.thecolorred.abcparser.parser;

public class Node {
	public static enum NodeType {
		NOTE, FIELD, INSTDEF, BEAT
	}
	public NodeType type;
	public String fieldName;
	public String text;
	public byte note;
	public float length;
	public byte instrument;
	public byte drumType;
	public int sortIndex;
}