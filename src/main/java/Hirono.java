import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Hirono {
    public static String line = "\n--------------------------------------------------";
    public static void printHashMap(HashMap<Integer, String> map) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);
        String logo = 
                  "                            .-'''-.                   .-'''-.     \n"
                + "                           '   _    \\                '   _    \\   \n"
                + "   .        .--.         /   /` '.   \\    _..._    /   /` '.   \\  \n"
                + " .'|        |__|        .   |     \\  '  .'     '. .   |     \\  '  \n"
                + "<  |        .--..-,.--. |   '      |  '.   .-.   .|   '      |  ' \n"
                + " | |        |  ||  .-. |\\    \\     / / |  '   '  |\\    \\     / /  \n"
                + " | | .'''-. |  || |  | | `.   ` ..' /  |  |   |  | `.   ` ..' /   \n"
                + " | |/.'''. \\|  || |  | |    '-...-'`   |  |   |  |    '-...-'`    \n"
                + " |  /    | ||  || |  '-                |  |   |  |                \n"
                + " | |     | ||__|| |                    |  |   |  |                \n"
                + " | |     | |    | |                    |  |   |  |                \n"
                + " | '.    | '.   |_|                    |  |   |  |                \n"
                + " '---'   '---'                         '--'   '--'                \n";
        System.out.println("Hello from\n" + logo);
        writer.println(line);

        writer.println("Hello! I'm Hirono");
        writer.println("What can I do for you?");

        writer.println(line);
        writer.println("What would you like to add to the list?");
        writer.flush();
        String input = reader.readLine();
        Integer itemCounter = 1;
        HashMap<Integer, String> items = new HashMap<>();

        while(!input.equals("bye")) {
            writer.println(line);
            if (input.equals("list")) {
                printHashMap(items);

            } else {
                writer.println("added: " + input);
                writer.println(line);
                items.put(itemCounter, input);
                itemCounter++;
            }
            writer.flush();
            input = reader.readLine();
        }
        writer.println(line);
        writer.println("Bye. Hope to see you again soon!");
        writer.println(line + "\n");

        
        writer.close();
        reader.close();
    }
}
