---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

Here is a fuller picture of how the `Logic` component works and how it interacts with the `model` component using the `execute("findbyaddress address")` API call.

![Interaction Inside the Logic Component for the `execute("findbyaddress address")` Command](images/FindByAddressSequenceDiagram.png)

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user**: Peer tutoring NGOs such as Heartware Network

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Students from various institutions are deployed to tuition centres and schools across Singapore. Administrators struggle to track student and tutee information, making it hard to organise volunteers, monitor deployments, and identify understaffed schools. A proper system helps manage student details and ensure adequate support at beneficiary schools.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                   | I want to …​                                                                      | So that I can…​                                                                       |
|----------|-------------------------------------------|-----------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `* * *`  | Student volunteer                         | search up my own contact                                                          | know which tuition centre and tutee I have been assigned to                           |
| `* * *`  | Beneficiary                               | search for the students that are assigned to my location                          | be prepared and accommodate them to the best of my ability                            |
| `* * *`  | Administrator                             | sort the contacts by categories                                                   | know what role my contacts play                                                       |
| `* * *`  | Administrator                             | tag mentees to mentors                                                            | keep track of who the mentors are mentoring                                           |
| `* * *`  | Experienced user                          | remove contacts easily                                                            | have a smaller list of contacts to utilize                                            |
| `* * *`  | Beneficiary                               | add student contacts                                                              | easily add to the list of students that require tutoring                              |
| `* *`    | Administrator                             | edit my personnel’s contacts                                                      | their entries if their contact details change                                         |
| `* *`    | Staff member of a partner school          | obtain a list of tutees and their corresponding matched tutors                    | inform my students who their tutor is                                                 |
| `* *`    | Manpower admin                            | sort the tutors and tutees by the centres they are deployed in                    | ensure each location receives enough students                                         |
| `* *`    | Parent of the tutee                       | identify which student is attached to my child                                    | communicate with them the needs of the child and track the progress of their learning |
| `* *`    | Administrator                             | keep track of which subjects my tutors are able to teach and tutees need help for | match them up appropriately                                                           |
| `* *`    | Student volunteer                         | track my students’ grades                                                         | Have an estimate of whether my work has helped them improve                           |
| `*`      | Director of outreach                      | identify patterns within the tutor sign-up rate                                   | better understand the demographic that is receptive to the publicity                  |
| `*`      | Administrator                             | sort by my contacts’ location                                                     | best assign the mentors to the mentees                                                |
| `*`      | Administrator                             | track the status of my donors’ payments and payments to vendors                   | know who I need to continue completing financial transactions with                    |
| `*`      | Student volunteer                         | monitor the number of students and tutors at each centre                          | match these against my location preferences                                           |
| `*`      | First time user                           | transfer my contacts easily to the application                                    | not have to add all of them one by one                                                |
| `*`      | Teacher from a volunteer student’s school | keep track of the number of hours that they volunteered for                       | easily tally their VIA hours                                                          |
| `*`      | Fair administrator                        | flag if a mentor has too many mentees                                             | ensure that they are not overbooked                                                   |



*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Mentorface` unless specified otherwise)


### Use case: U01 - Adding person to Mentorface

#### Actor: Administrator
#### MSS:
1. Administrator enters the information of the person they wish to add
2. Mentorface notifies the administrator that the person has been added

Use case ends

#### Extension:

#### 1a. Mentorface detects that the Administrator has given the information of the person in the incorrect format

1a1. Mentorface informs the user of the format error and shows the administrator an example of the correct format

1a2. Administrator enters the information of the person they wish to add again

Steps 1a1 and 1a2 repeats until Mentorface no longer detects an error with the input

1a3. Mentorface notifies the administrator that the person has been added

Use case ends

### Use case: U02 - Delete person from Mentorface

#### Actor: Administrator

#### MSS:
1. Administrator provides the input to delete a specified person from Mentorface
2. Mentorface deletes the person from the list and notifies the Administrator that the person has been deleted

Use case ends

#### Extension:

#### 1a. Mentorface detects that the administrator a person that does not exist in the list

1a1. Mentorface informs the administrator that the person they have specified does not exist, as well as the format the administrator should use when deleting people

1a2. Administrator enters the information of the person they wish to delete again

Steps 1a1 and 1a2 repeats until Mentorface can find the person specified by the administrator.

1a3. Mentorface deletes the person from the list and notifies the Administrator that the person has been deleted

Use case ends

### Use case: U03 - Find a person in Mentorface

#### Actor: User

#### Person: Administrator, Tutor, Tutee

#### MSS:
1. User provides the input to find a specified person from Mentorface
2. Mentorface finds the person from the list and shows the user the information associated with the person

Use case ends

#### Extension:
#### 1a. Mentorface detects that the administrator has provided the information for a person that does not exist in the list

1a1. Mentorface informs the administrator that the person they have specified does not exist, as well as the format the administrator should use when finding people

1a2. Administrator enters the information of the person they wish to find again

Steps 1a1 and 1a2 repeats until Mentorface can find the person specified by the administrator.

1a3. Mentorface finds the person from the list and shows the user the information associated with the person

Use case ends

### Use case: U04 - Match a person from Mentorface to another person from Mentorface

#### Actor: Administrator

#### MSS:
1. Administrator inputs the 2 people that they want to match into Mentorface
2. Mentorface adds a tag to indicate that these 2 people have been matched and notifies the administrator that the match was successful

Use case ends

#### Extension:
#### 1a. Mentorface detects that the administrator has provided the information for people that do not exist in the list
1a1. Mentorface informs the administrator that the people they have specified do not exist, as well as the format the administrator should use when matching people

1a2. Administrator enters the information of the people they wish to match again

Steps 1a1 and 1a2 repeats until Mentorface can find the people specified by the administrator.

1a3. Mentorface adds a tag to indicate that these 2 people have been matched and notifies the administrator that the match was successful

Use case ends

### Use case: U05 - Editing the information of a person in Mentorface

#### Actor: Administrator

#### MSS:
1. Administrator provides the person they wish to change and the information they want changed
2. Mentorface makes the changes to the person and notifies the administrator that the information has been updated.

Use case ends

#### Extension:
#### 1a. Mentorface is unable to find the person that the administrator specified
1a1. Mentorface informs the user that the person they have specified does not exist

1a2. Administrator enters information and person they want edited again

Steps 1a1 and 1a2 repeats until Mentorface can find the person and there is no error with the format of the input

1a3. Mentorface notifies the administrator that the person’s information has been edited

Use case ends

#### 1b. Mentorface detects that the administrator has provided information in the incorrect format
1b1. Mentorface informs the user that the format they have provided for the person is incorrect and shows them an example of the correct format

1b2. Administrator enters information and person they want edited again

Steps 1b1 and 1b2 repeats until Mentorface can find the person and there is no error with the format of the input

1b3. Mentorface notifies the administrator that the person’s information has been edited

Use case ends

*{More to be added}*

### Non-Functional Requirements

1. The system should be based on the current implementation of AB3 without deviating too much.
2. Only the administrators have the ability to make edits to the system
3. The system should only store tutor and tutee information
4. The system should launch in under 2 seconds
5. The system should add the contact to the address book in a reasonable amount of time, no more than 200ms
6. The system should filter and return the list is no more than 5 seconds
7. The system needs to work with older operating systems as NGOs may not have the latest versions. Minimally Windows 7 onwards, macOS 13 onwards, Linux Ubuntu 20 onwards
8. The system should have identical behaviour across Windows, macOS and Linux
9. The system needs to be able to store a large amount of contacts (at least 1000 inputs)
10. The system needs to be able to handle name conflicts (where 2 people share the same name) during searches
11. The system should account for the possibility that 2 inputs can be exactly the same
12. The system should be easy to understand for a novice user that prefers a CLI
13. The system should not support the upload of images for privacy reasons
14. The system should store the data locally on the user's device
15. The system only needs to handle input for 1 user at a given time
16. The system should work on a computer that has a version of java 17
17. The GUI of the system needs to work for screens with resolution of at least 1280x720 and higher
18. The system should be PDF friendly

*{More to be added}*

### Glossary

* **Administrator**: The person in charge of the operations and logistics related to the functions of the NGO
* **Beneficiary**: An organisation (not necessarily a school) that is receiving help from the NGO in the form of tutoring services provided by the NGO
* **Tutor**: A student volunteer from a participating institution
* **Tutee**: A student from the beneficiary that is receiving the help from the tutor
* **Donor**: An organisation or individual that helps fund the NGO
* **Access Level**: The degree of permission a user has to update the system
* **NGO**: Abbreviation for non-government organisations

*{More to be added}*

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
