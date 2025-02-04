package hirono.parser;

import hirono.HironoException;
import hirono.command.AddCommand;
import hirono.command.Command;
import hirono.command.DateCommand;
import hirono.command.DeleteCommand;
import hirono.command.ExitCommand;
import hirono.command.FindCommand;
import hirono.command.ListCommand;
import hirono.command.MarkCommand;
import hirono.command.UnmarkCommand;


/**
 * parses inputs based on the type of command
 */
public class Parser {

    /**
     * @param input
     * @return Command
     * @throws HironoException
     */
    public Command parse(String input) throws HironoException {
        if (input.equals("list")) {
            return new ListCommand();
        } else if (input.startsWith("delete")) {
            int taskId = parseTaskId(input, "delete");
            return new DeleteCommand(taskId);
        } else if (input.startsWith("mark")) {
            int taskId = parseTaskId(input, "mark");
            return new MarkCommand(taskId);
        } else if (input.startsWith("unmark")) {
            int taskId = parseTaskId(input, "unmark");
            return new UnmarkCommand(taskId);
        } else if (input.startsWith("todo")) {
            return new AddCommand(input, "todo");
        } else if (input.startsWith("deadline")) {
            return new AddCommand(input, "deadline");
        } else if (input.startsWith("event")) {
            return new AddCommand(input, "event");
        } else if (input.startsWith("date")) {
            return new DateCommand(input);
        } else if (input.startsWith("find")) {
            return new FindCommand(input);
        } else if (input.equals("bye")) {
            return new ExitCommand();
        } else {
            throw new HironoException("I'm sorry, but I don't know what that means.");
        }
    }

    private int parseTaskId(String input, String command) throws HironoException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new HironoException("The " + command + " command requires a task ID.");
        }
        return Integer.parseInt(parts[1]);
    }
}

