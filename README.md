# Jarvis
This is a Bukkit/PaperMC/Spigot, made in version 1.18.2 plugin designed for Minecraft servers. It provides an 'AI' chatbot called "Jarvis" which players with appropriate permissions can interact with  (tonystark.jarvis). This plugin listens to chat messages from players, and if a player with the required permission sends a message that matches a pre-configured trigger message, Jarvis will respond with a pre-defined message and execute the associated commands.

When a player joins the server, the plugin sends a welcome message to the player in the form of an ActionBar message, which is a message that appears above the player's inventory. The welcome message can be customized in the plugin's configuration file.

The plugin's configuration file can be reloaded using the "/jarvisplugin.reload" command. This command can only be executed by a player who has the appropriate permission, and it reloads the plugin's configuration file without requiring the server to be restarted.

To configure Jarvis's responses and commands, the plugin's configuration file can be edited. Triggers can be added with pre-defined messages that will activate Jarvis's response. Each trigger can have a response message that Jarvis will send to the player, and a list of commands that Jarvis will execute when triggered.

The plugin's source code is written in Java and includes an implementation of the Bukkit API. The Bukkit API allows the plugin to interact with the Minecraft server and perform actions like sending chat messages and executing commands. The plugin also uses the Kyori Adventure library for text formatting and styling.
