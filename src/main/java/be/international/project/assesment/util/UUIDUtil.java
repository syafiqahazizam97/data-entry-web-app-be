package be.international.project.assesment.util;

import java.util.UUID;

public class UUIDUtil {

	public static String generateNo() {
		final UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

}
