# ESTJ User Guide

![Main UI](docs/Ui.png)

ESTJ is a messenger-like task planner.
You can add / delete / mark / check tasks with simple commands.
ry your first step to be a MBTI-J person!

## Adding todos

Adds a simple task without due dates.

Example: `todo read book`

Expected output:
```
Nice plan!
added: [T][ ] read book
```

## Adding deadlines

Adds a task with a due date (inclusive commands and listing).

Example: `deadline cs2103t ip /by 2025-09-19`

Expected output:
```
Nice plan!
added: [D][ ] cs2103t ip (by: 2025-09-19)
```

## Adding events

Adds a event that has a specific start and end date.

Example: `event internship /from 2026-01-12 /to 2026-06-26`

Expected output:
```
Nice plan!
added: [E][ ] internship (from: 2026-01-12 to: 2026-06-26)
```

## Listing

Shows all the tasks in the list.

Example: `list`

Expected output:
```
1. [T][ ] read book
2. [D][ ] cs2103t ip (by: 2025-09-19)
3. [E][ ] internship (from: 2026-01-12 to: 2026-06-26)
```

## Listing in time period

Shows a list of tasks assigned in a specific time period.

Example: `list between 2025-09-01 2025-10-20`

Expected output:
```
1. [D][ ] pay rent (by: 2025-09-27)
2. [D][ ] cs2103t ip (by: 2025-09-19)
```

## Marking / Unmarking

Marks or unmarks a task by listing numbers.

Example: `mark 1`

Expected output:
```
Well done! Wish your planning habits helped you!
I've marked this task as done:
[D][X] cs2103t ip (by: 2025-09-19)
```

## Deleting

Deletes a task from the list.

Example: `mark 1`

Expected output:
```
Noted. I've removed this task:
[D][X] cs2103t ip (by: 2025-09-19)
Now you have 2 tasks in the list.
```

## Sources

Avatar: adapted from 16Personalities (https://www.16personalities.com/). 
Educational, non-commercial use. No affiliation; 
rights belong to respective owners.
