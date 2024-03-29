package me.waterbroodje.waterlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple class for implementing sub commands.
 * <p>
 * `R`egister one of these as the executor for your plugin's root command and then register sub commands to this
 * CommandBase with {@link #registerSubCommand(String, CommandExecutor)}.
 *
 * @param <P> The implementing plugin.
 */
public abstract class CommandBase<P extends Plugin> implements CommandExecutor {
    private final Map<String, CommandExecutor> subCommands = new HashMap<>();
    private final P plugin;

    /**
     * Creates a new CommandBase for the given plugin.
     *
     * @param plugin The plugin that owns this command.
     */
    public CommandBase(P plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the plugin that owns this command.
     */
    public P getPlugin() {
        return plugin;
    }

    /**
     * Registers a sub command to this command.
     *
     * @param label The label for the sub command.
     * @param subCommand The sub command to register which can either be a plain CommandExecutor or another
     *                   CommandBase if further command nesting is desired.
     */
    public void registerSubCommand(String label, CommandExecutor subCommand) {
        subCommands.put(label.toLowerCase(), subCommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 0) {
            CommandExecutor child = subCommands.get(args[0].toLowerCase());
            if (child != null) {
                label = args[0];
                String[] newArgs = new String[args.length - 1];
                System.arraycopy(args, 1, newArgs, 0, newArgs.length);
                return child.onCommand(sender, command, label, newArgs);
            }
        }
        return runCommand(sender, command, label, args);
    }

    /**
     * Executes the given commands and returns its success.
     * <p>
     * Note that the success returned may propagate up to the root command.
     *
     * @param sender Source of the command.
     * @param rootCommand The top level command that was executed.
     * @param label Alias of the command that was used - the sub command label being used.
     * @param args Arguments for the sub command.
     * @return true if a valid command, false otherwise.
     */
    public abstract boolean runCommand(CommandSender sender, Command rootCommand, String label, String[] args);
}
