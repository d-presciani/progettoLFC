package myPackage;

public class Variable {
	public String name;
	public String type;
	public Integer value;
	
	public Variable (String n) {
		name = n;
		type = "int";
		value=null;
	}
	public Variable (String t, String n) {
		name = n;
		type = t;
		value=null;
	}
	
	public int getValue() {
		return value.intValue();
	}
}
