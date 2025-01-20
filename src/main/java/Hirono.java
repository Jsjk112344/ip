import java.io.*;

public class Hirono {
    public static String line = "--------------------------------------------------";
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
        writer.println("What can I do for you?\n");

        writer.println(line);
        writer.println("What would you like me to echo?\n");
        writer.flush();
        String input = reader.readLine();

        while(!input.equals("bye")) {
            writer.println(line);
            writer.println(input);
            writer.println(line);
            writer.println("What would you like me to echo next?\n");
            writer.flush();
            input = reader.readLine();
        }
        writer.println(line);
        writer.println("Bye. Hope to see you again soon!\n");
        writer.println(line + "\n");

        
        writer.close();
        reader.close();
    }
}
