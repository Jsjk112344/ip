# Hirono: Your Personal Task Manager
> "The art of progress is to preserve order amid change and to preserve change amid order." – Alfred North Whitehead 🧠
Hirono is a **text-based task management application** designed to help you **stay organized and productive**.
## 📌 About Hirono
Hirono is a **command-line and GUI-based task management application** that helps you manage your daily tasks efficiently.
### Why use Hirono?
- ✅ **Easy to use** – Simple text-based commands for quick task management.
- ⚡ **Fast & Efficient** – Lightweight and responsive.
- 🔧 **Customizable** – Supports different task types (*ToDo, Deadlines, Events*).
---
## 🚀 Features
### 📝 **Task Management**
- Add, delete, mark, and unmark tasks.
- Support for **"ToDo"**, **"Deadline"**, and **"Event"** tasks.
### 📅 **Event Scheduling**
- Keep track of deadlines and events.
- Manage time efficiently with **precise scheduling**.
### 🔍 **Search & Filter**
- Quickly **find tasks** by keywords or dates.
- Filter tasks that are due today.
---
## 💡 Getting Started
### 1️⃣ **Running the Application**
To run the application, use:
```sh
java -jar hirono.jar
```
### 2️⃣ **Adding Tasks**
To add new tasks to your list, use:
```sh
# Todo Tasks
todo <description>
Example: todo read book

# Deadline Tasks
deadline <description> /by <date-time>
Example: deadline finish report /by 2023-12-01 2359

# Event Tasks
event <description> /from <start-time> /to <end-time>
Example: event team meeting /from 2023-12-01 1400 /to 2023-12-01 1600
```
### 3️⃣ **Managing Tasks**
To manage your existing tasks, use:
```sh
# Mark and Unmark Tasks
mark <task-number>
Example: mark 1

unmark <task-number>
Example: unmark 1

# Delete Tasks
delete <task-number>
Example: delete 1

# Edit Tasks
edit <task-number>: <task-type> <new-description>
Examples:
edit 1: todo study mathematics
edit 2: deadline complete assignment /by 2023-12-15 2359
edit 3: event project meeting /from 2023-12-10 1000 /to 2023-12-10 1200
```
### 4️⃣ **Viewing Tasks**
To view and search your tasks, use:
```sh
# List all tasks
list

# View tasks by date
date YYYY-MM-DD
Example: date 2023-12-01

# Find tasks by keyword
find <keyword>
Example: find book
```
### 5️⃣ **Date Time Format**
Use these formats for deadlines and events:
```sh
# Format
Date: YYYY-MM-DD
Time: HHMM (24-hour)

# Examples
2023-12-01 2359    # December 1st, 11:59 PM
2023-12-25 0900    # December 25th, 9:00 AM
```
### 6️⃣ **Requirements**
System requirements to run Hirono:
```sh
# Minimum Requirements
Java 11 or higher
50MB free disk space
Command-line interface or GUI terminal
```