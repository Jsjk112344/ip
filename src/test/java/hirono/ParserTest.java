package hirono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hirono.command.AddCommand;
import hirono.command.Command;
import hirono.parser.Parser;
public class ParserTest {
    /**
     * @throws HironoException
     */
    @Test
    public void testParseValidCommand() throws HironoException {
        Parser parser = new Parser();
        Command command = parser.parse("todo read book");
        assertTrue(command instanceof AddCommand);
    }

    @Test
    public void testParseInvalidCommand() {
        Parser parser = new Parser();
        Exception exception = assertThrows(HironoException.class, () -> {
            parser.parse("invalid command");
        });
        assertEquals("I'm sorry, but I don't know what that means.", exception.getMessage());
    }
}
