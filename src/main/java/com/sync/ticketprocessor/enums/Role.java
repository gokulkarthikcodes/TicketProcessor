package com.sync.ticketprocessor.enums;

public enum Role {
	ROLE_USER, ROLE_ADMIN, ROLE_OWNER, ROLE_CONTRACTOR;

	public static Boolean isExist(String role) {
		if (role == null || role.equals("")) {
			return false;
		}

		for (Role item : Role.values()) {
			if (role.equalsIgnoreCase(item.name())) {
				return true;
			}
		}
		return false;
	}
}
