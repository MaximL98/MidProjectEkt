package logic;

/**
 * Role is an enum that designates the role of a user
 * TODO:
 * add role field to the table we need to make (table: username, password, id, role, status) [tbd]
 * @author Rotem
 *
 */

public enum Role {
	// Rotem: added the logistics employee AND SUBSCRIBER - he is the one to actually re stock the machines.
	// ?
	CUSTOMER, REGIONAL_MANAGER, LOGISTICS_MANAGER, SERVICE_REPRESENTATIVE, CEO, DIVISION_MANAGER, LOGISTICS_EMPLOYEE, SUBSCRIBER; 
	
	// get role object from string
	public static Role getRoleFromString(String roleAsString) {
		return Role.valueOf(roleAsString.toUpperCase());
	}
	
	// we want it in lowercase for the sql table
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
	
}
