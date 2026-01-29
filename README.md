# Event Planner Mini

This project demonstrates practical use of data structures:
linked lists, stacks, queues, maps, trees, sorting, and searching.

---

## Data Structures Used 
### GuestList Manager
**1. LinkedList<Guest>**
LinkedList for the guest list, which stores the guests in
insertion order and allows easy iteration and insertion.

**2. HashMap<String, Guest>**
Hashmap for the mapping guest names to Guest objects, 
which allows O(1) lookups when searching for a guest by name, avoiding iteration through the entire list.

### Seating Planner
**3. HashMap<String, Queue<Guest>>** 
* Hashmap groupQueues to organize guests by their group tag for seating arrangements. HashMap provides O(1) access to each group's queue

**4. HashMap<Integer, List<Guest>>**
* Hashmap seatingChart to map table numbers to lists of guests. Provides O(1) access to any tables guest list using the table number as the key

**5. ArrayList<Guest>**
* Arraylist currentTable to temporary store guest for the current table being filled

**6. Queue<Guest> (implemented as LinkedList)**
* FIFO to ensure guests are seated in the order they were added to their group

### TaskManager
**7. Queue<Task> (implemented as LinkedList)**
* Queue upcoming to ensure tasks are executed in FIFO order, which is the order they were planned in, which makes sense for a task management system

**8. Stack<Task>**
* Stack task to store completed tasks with a LIFO order to allow for tasks to be undone in reverse order, which is logical for an undo feature 

### VenueSelector
**9. List<Venue>**
* List for venues to store all available venues and allow iteration through all venues

**10. ArrayList<Venue>**
* ArrayList for validVenues for filtering neues. Provides O(1) random access and allows for dynamic resizing

### Sorting and Searching algorithms

In Venue Selector I used the built-in Java sorting algorithm to sort the venues by cost and then capacity.
Then for the searching algorithm I used linear search to iterate through the entire venue list one by one. 
In GuestListManager I used the HashMap to directly retrieve where a guest is stored.



## Big O complexity: 
### Finding a guest 
- **Complexity:** O(1) - Constant time
- Uses a HashMap for constant time access using the guest's name as a key 
### Selecting a Venue
- **Complexity:** O(n log n) where n = number of venues
- uses the Java sorting algorithm. This is the most efficient for comparion based sorting. bc comparing venues to each other so we have to look at each multilple times
### Generating Seating
- **Complexity:** O(n) where n = number of guests
- linear time. each guest gets processed twice, once to group queue and once to assign to tables but never more. IF you double the amount of guests the time doubles  

 