# Hirono: Your Personal Task Manager
> "The art of progress is to preserve order amid change and to preserve change amid order." – Alfred North Whitehead 🧠

Hirono is a **text-based task management application** designed to help you **stay organized and productive**.

## 📌 About Hirono
Hirono is a **command-line and GUI-based task management application** that helps you manage your daily tasks efficiently.

### Why use Hirono?
- ✅ **Easy to use** – Simple text-based commands for quick task management.
- ⚡ **Fast & Efficient** – Lightweight and responsive.
- 🔧 **Customizable** – Supports different task types (*ToDo, Deadlines, Events*).

## 🚀 Features
### 📝 **Task Management**
- Add, delete, mark, unmark, and edit tasks.
- Support for **"ToDo"**, **"Deadline"**, and **"Event"** tasks.

### 📅 **Event Scheduling**
- Keep track of deadlines and events.
- Manage time efficiently with **precise scheduling**.

### 🔍 **Search & Filter**
- Quickly **find tasks** by keywords or dates.
- Filter tasks that are due today.

## 💡 Getting Started
### Running the Application
To run the application, use:
```sh
java -jar hirono.jar
Basic Commands

Adding Tasks

Todo: todo <description>
Copytodo read book

Deadline: deadline <description> /by <date-time>
Copydeadline finish report /by 2023-12-01 2359

Event: event <description> /from <start-time> /to <end-time>
Copyevent team meeting /from 2023-12-01 1400 /to 2023-12-01 1600



Listing Tasks

List all tasks: list
View tasks by date: date YYYY-MM-DD
Copydate 2023-12-01



Managing Tasks

Mark as done: mark <task-number>
Copymark 1

Unmark task: unmark <task-number>
Copyunmark 1

Delete task: delete <task-number>
Copydelete 1

Edit task: edit <task-number>: <task-type> <new-description>
Copyedit 1: todo study mathematics
edit 2: deadline complete assignment /by 2023-12-15 2359
edit 3: event project meeting /from 2023-12-10 1000 /to 2023-12-10 1200



Finding Tasks

Search by keyword: find <keyword>
Copyfind book




Date-Time Formats

Date format: YYYY-MM-DD
Time format: HHMM (24-hour format)

Example date-time combinations:
Copy2023-12-01 2359
2023-12-25 0900
Tips & Notes

Task numbers are displayed in the list view
Use list to see current task numbers before editing or deleting
Tasks maintain their type when editing (can't change a todo to a deadline)
Use the date command to see all tasks due on a specific day
