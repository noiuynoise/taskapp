package edu.wpi.cs.heineman.demo.model;

public class Constant {
	public final String name;
	public final double value;
	public final boolean system;      // when TRUE this is actually stored in S3 bucket
	
	public Constant (String name, double value) {
		this.name = name;
		this.value = value;
		this.system = false;
	}
	
	public Constant (String name, double value, boolean system) {
		this.name = name;
		this.value = value;
		this.system = system;
	}
	
	public boolean getSystem() { return system; }
	
	/**
	 * Equality of Constants determined by name alone.
	 */
	public boolean equals (Object o) {
		if (o == null) { return false; }
		
		if (o instanceof Constant) {
			Constant other = (Constant) o;
			return name.equals(other.name);
		}
		
		return false;  // not a Constant
	}

	public String toString() {
		String sysString = "";
		if (system) { sysString = " (system)"; }
		return "[" + name+ " = " + value + sysString + "]";
	}
}
