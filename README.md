#Hirono: Your Personal Task Manager

"The art of progress is to preserve order amid change and to preserve change amid order." – Alfred North Whitehead 🧠
Hirono is a text-based task management application designed to help you stay organized and productive.
##📌 About Hirono
Hirono is a command-line and GUI-based task management application that helps you manage your daily tasks efficiently.
###Why use Hirono?


✅ Easy to use – Simple text-based commands for quick task management.
⚡ Fast & Efficient – Lightweight and responsive.
🔧 Customizable – Supports different task types (ToDo, Deadlines, Events).
##🚀 Features
###📝 Task Management
Add, delete, mark, unmark, and edit tasks.
Support for "ToDo", "Deadline", and "Event" tasks.
###📅 Event Scheduling
Keep track of deadlines and events.
Manage time efficiently with precise scheduling.
###🔍 Search & Filter
Quickly find tasks by keywords or dates.
Filter tasks that are due today.
##💡 Getting Started
###Running the Application
To run the application, use:

shCopyjava -jar hirono.jar
###Command Guide
####Basic Commands
#####Adding Tasks
CopyTodo: todo <description>
Example: todo read book

Deadline: deadline <description> /by <date-time>
Example: deadline finish report /by 2023-12-01 2359

Event: event <description> /from <start-time> /to <end-time>
Example: event team meeting /from 2023-12-01 1400 /to 2023-12-01 1600
#####Listing Tasks
CopyList all tasks: list
View tasks by date: date YYYY-MM-DD
Example: date 2023-12-01
#####Managing Tasks
CopyMark as done: mark <task-number>
Example: mark 1

Unmark task: unmark <task-number>
Example: unmark 1

Delete task: delete <task-number>
Example: delete 1

Edit task: edit <task-number>: <task-type> <new-description>
Examples:
edit 1: todo study mathematics
edit 2: deadline complete assignment /by 2023-12-15 2359
edit 3: event project meeting /from 2023-12-10 1000 /to 2023-12-10 1200
#####Finding Tasks
CopySearch by keyword: find <keyword>
Example: find book
####Date-Time Formats
CopyDate format: YYYY-MM-DD
Time format: HHMM (24-hour)

Examples:
2023-12-01 2359
2023-12-25 0900
