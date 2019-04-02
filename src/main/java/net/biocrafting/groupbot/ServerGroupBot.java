package net.biocrafting.groupbot;

import net.biocrafting.groupbot.bot.GroupBot;
import net.biocrafting.groupbot.bot.Configuration;

public class ServerGroupBot {
	public static void main(String[] args) {
		if (Configuration.PROPERTY_DB_NAME.isEmpty() 
				|| Configuration.PROPERTY_DB_PASSWORD.isEmpty() 
				|| Configuration.PROPERTY_DB_SERVER.isEmpty()
				|| Configuration.PROPERTY_DB_USER.isEmpty()) {			
			System.out.println("Please set all database configuration in Configuration class");
		} else {
			try {
				GroupBot bot = new GroupBot(args);
				boolean success = bot.extractAll();
				if (success) {
					bot.setGroup();
				} else {
					System.out.println("There was an error during user extraction from database");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println("Errrrrrrr");
				if (Configuration.isDebugEnable()) {
					e.printStackTrace();
				}
			}
		}
	}
	private ServerGroupBot() {}
}
