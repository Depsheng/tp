---
layout: page
title: User Guide
toc: true
toc_label: "On this page"
toc_icon: "list"
---
CLIentTracker is a **desktop CRM designed for property agents, optimized for use via a Command Line Interface** (CLI) while still retaining the benefits of a simple visual interface. It allows agents to manage clients, listings, and notes quickly through commands such as `add`, `edit`, `find`, and `list`. If you can type fast, CLIentTracker lets you update and retrieve information significantly faster than traditional GUI-based CRM systems.


Unlike many web-based CRMs, CLIentTracker works **fully offline**, allowing agents to continue working seamlessly in environments with poor or unstable connectivity, such as property viewings, new developments, or while on the move. Core operations like searching for clients or updating details can be done instantly without waiting for pages to load or systems to sync. When the application is closed, all data is automatically saved and archived, ensuring that records remain secure without requiring manual backups.


CLIentTracker is built for agents who value **speed, reliability, and control**. By removing dependency on internet access and reducing interaction to simple, efficient commands, it enables agents to focus on their clients rather than their tools—making it especially effective in fast-paced, real-world selling environments where every second counts.

## :page_facing_up: Contents
- [:rocket: Quick Start](#quick-start)
  - [:clipboard: Command Summary](#command-summary)
  - [:gear: Features](#features)
  - [:question: FAQ](#faq)
  - [:warning: Known Issues](#known-issues)

--------------------------------------------------------------------------------------------------------------------

## :rocket: Quick Start

1. Ensure you have Java `17` or above installed on your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

   1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

   1. Copy the file to the folder you want to use as the _home folder_ for your CLIentTrcaker.

   1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clienttracker.jar` command to run the application.<br>
      A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
      ![Ui](images/Ui.png)

   1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
      Some example commands you can try:

      * `list` : Lists all contacts.

      * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to CLIentTracker.

      * `delete 3` : Deletes the 3rd contact shown in the current list.

      * `clear` : Deletes all contacts.

        * `find n/John` : Finds contacts whose names contain ‘John’.

      * `exit` : Exits the app.

   1. Refer to the [Command summary](#command-summary) below for a quick list of all commands or [Features](#features) for detailed descriptions.
   .
--------------------------------------------------------------------------------------------------------------------
## :clipboard: Command Summary

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  * e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

  * Items in square brackets are optional.<br>
    * e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

  * Items with `…`​ after them can be used multiple times including zero times.<br>
    * e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

  * Parameters can be in any order.<br>
    *  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

  * Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
    * e.g. if the command specifies `help 123`, it will be interpreted as `help`.

  * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

Action | Description                                                    | Format, Examples
--------|----------------------------------------------------------------|------------------
**Add** | [Adds a new person](#adding-a-person-add)                      | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/DETAILS] [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 d/Looking to buy in north t/BUYER`
**Edit** | [Edits an existing person](#editing-a-person-edit)             | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [d/DETAILS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com d/Updated work details`
**Find** | [Finds persons by name or phone](#locating-persons-find)       | `find KEYWORD [MORE_KEYWORDS]` for name search<br> `find p/PHONE_NUMBER` for phone search<br> e.g., `find James Jake` or `find p/98765432`
**Delete** | [Deletes a person](#deleting-a-person--delete)                 | `delete PHONE`<br> e.g., `delete 91234567`
**Clear** | [Clears all entries](#clearing-all-entries--clear)             | `clear`
**Mark** | [Adds contact into favourites](#favourites-mark-and-unmark)    | `mark INDEX` <br> Example: `mark 1`
**Unmark** | [Removes contact from favourites](#favourites-mark-and-unmark) | `unmark INDEX` <br> Example: `mark 1`
**Meeting** | [Adds meeting datetime to contact](#adding-a-meeting-meeting)  | `meeting INDEX DATE_TIME` <br> Example: `meeting 1 mon 2pm`
**List** | [Lists all persons](#listing-all-persons-list)                 | `list`
**Help** | [Shows help message](#viewing-help-help)                       | `help`
**Exit** | [Exits the app](#exiting-the-program-exit)                     | `exit`


--------------------------------------------------------------------------------------------------------------------
## :gear: Features

### Adding a person: `add`

Adds a new contact

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [d/DETAILS] [t/TAG]…​`

Parameters:
* `p/` : Phone number of the new contact (*Unique identifier*)
  * `n/` : Name of the new contact
  * `e/` : Email of the new contact
  * `a/` : Address of the new contact
  * `d/` : Details of the new contact [optional] (*Must be under 512 characters, cannot be empty*)
  * `t/` : Tags of the new contact [optional] (*Valid tags: "Renter", "Landlord", "Buyer", "Seller"*)

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Behavior:
* If a contact with the same phone number already exists, the new contact will not be added.
  * Details will default to "No Details" if parameter not used.
  * Details must be under 512 characters and cannot be empty.

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
  * `add n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/1234567 t/BUYER`
  * `add n/Alex Yeoh p/87438807 e/alexyeoh@example.com a/Blk 30 Geylang Street 29, #06-40 d/Looking for apartment near city`


### Editing a person: `edit`

Edits an existing person in CLIentTracker.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DETAILS] [t/TAG]…​`

Parameters:
* `INDEX` : The index of the person to edit. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
  * `p/` : Phone number of the new contact (*Unique identifier*)
  * `n/` : Name of the new contact
  * `e/` : Email of the new contact
  * `a/` : Address of the new contact
  * `d/` : Details of the new contact [optional] (*Must be under 512 characters, cannot be empty*)
  * `t/` : Tags of the new contact [optional] (*Valid tags: "Renter", "Landlord", "Buyer", "Seller"*)

Behavior:
* The index field is mandatory and **must be a positive integer smaller than the number of contacts**
  * At least one of the optional fields must be provided.
  * Existing values will be updated to the input values.
  * When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
  * You can remove all the person’s tags by typing `t/` without
      specifying any tags after it.
  * When editing details, the existing details of the person will be removed i.e adding of details is not cumulative.
  * Details field must be under 512 characters and cannot be empty, otherwise details will not be updated.
  * If a contact with the same phone number already exists, the contact will not be updated.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
  *  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
    *  `edit 3 d/Updated details about this person` Edits the details of the 3rd person to be `Updated details about this person`.

### Locating persons: `find`
The find command searches across all details of a person — including name, phone number, email, address, and notes.
It supports both substring matches and exact matches, and returns the entire person’s details when a match is found.

Format Examples:
* General search: `find KEYWORD`
  * Name search: `find n/NAME_KEYWORD`
  * Phone search: `find p/PHONE_NUMBER`
  * Address search: `find a/ADDRESS_KEYWORD`
  * Email search: `find e/EMAIL_KEYWORD`

**For general search:**
* case-insensitive search. e.g. `alex` will match `Alex`
  * Keyword order does not matter (e.g. `Hans Bo` will match `Bo Hans`)
  * Substring matches supported. e.g. `lex` will match `Alex`
  * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `find alex` will return persons whose fields contain `alex`

Examples (General Search):
* `find 9876` → returns the full details of persons with phone numbers 98765432, 98760000
  * `find Street` → returns the full details of persons with addresses 123 Street Ave, Streetview Apartments
  * `find example.com` → returns the full details of persons with emails like johndoe@example.com
  * `find Updated` → returns the full details of persons with notes like Updated details about this person

**For field-specific search (using prefixes):**
* Searches only within the specified field.
  * The search is case-insensitive.
  * Partial matches are supported for all fields.
  * Keywords within the same field are matched using **OR** logic.
    e.g. `find n/Alex John` returns persons whose **name** contains `Alex` or `John`
  * Different fields can be combined and are matched using **AND** logic.
    e.g. `find n/Alex p/9123` returns persons whose **name** contains `Alex` and whose **phone** contains `9123`

**Supported prefixes:**
* `n/` — name
  * `p/` — phone
  * `a/` — address
  * `e/` — email
  * `d/` — details

**Prefix behavior:**
* Once a prefix is used, all following unprefixed keywords are treated as belonging to that same field until another prefix appears.
  * e.g. `find n/Alex Bob` searches for persons whose **name** contains `Alex` or `Bob`
  * e.g. `find n/Alex p/9123 Bob` searches for persons whose **name** contains `Alex`, and whose **phone** contains `9123` or `Bob`


Examples (Field-Specific Search):
* `find n/Alex David` returns persons whose name contains `Alex David`
  * `find p/91032182 8743` returns persons whose phone contains `91032182 8743`
  * `find p/9876` returns all persons with phone numbers containing `9876`
  * `find a/Serangoon Geylang` returns persons whose address contains `Serangoon Geylang`
  * `find e/example.com` returns persons whose email contains `example.com`
  * `find d/friend` returns persons whose details contain `friend`
   ![result for 'find alex david'](images/findAlexDavidResult.png)


Examples (Combined Search):
* `find n/Alex p/9123` returns persons whose name contains `Alex` and phone contains `9123`
  * `find alex p/9876` returns persons whose fields contain `alex` and whose phone contains `9876`
  * `find n/Alex Bob d/friend close` returns persons whose name contains `Alex` or `Bob`, and whose details contain `friend` or `close`


### Adding a meeting: `meeting`
Adds a meeting date and time for a client identified by the displayed index number.

Format: `meeting INDEX DATE_TIME`

* The **INDEX** refers to the index number shown in the displayed person list.
* The **DATE_TIME** can be entered in various flexible formats:

**Date formats:**
- `15 Mar 2030` (day month year)
- `15 March 2030` (full month name)
- `15/3/2030` (slash separators)
- `15-3-2030` (dash separators)
- `15.3.2030` (dot separators)

**Time formats:**
- `4pm` (12-hour format)
- `4:30pm` (12-hour with minutes)
- `4.30pm` (12-hour with dot minutes)
- `1600` (24-hour format)
- `14:30` (24-hour with colon)

**Combined examples:**
- `meeting 1 15 Mar 2030 4pm`
- `meeting 2 15 March 2030 4:30pm`
- `meeting 3 15/3/2030 1600`
- `meeting 4 15-3-2030 14:30`
- `meeting 5 15.3.2030 1630`

**Relative dates:**
- `meeting 1 Today 4pm` (today's date)
- `meeting 2 Tomorrow 9am` (tomorrow's date)
- `meeting 3 Monday 2pm` (next Monday)

**Weekday shortcuts:**
- `meeting 1 Monday 2pm` or `meeting 1 Mon 2pm`
- `meeting 2 Tuesday 2pm` or `meeting 2 Tue 2pm`
- (Similar for Wed, Thu, Fri, Sat, Sun)

**Important notes:**
- Meeting dates and times must be in the future
- If no time is specified, defaults to 12:00 AM (midnight)
- All date/time inputs are case-insensitive

Examples:
* `meeting 1 25/03/2030 14:30` Adds meeting for 1st person
* `meeting 2 15 Mar 2030 4pm` Adds meeting for 2nd person
* `meeting 3 Today 2359` Adds meeting for today at 11:59 PM
* `meeting 4 Monday 9am` Adds meeting for next Monday at 9:00 AM

### Deleting a person : `delete`

Deletes the specified person from CLIent Tracker.

Format: `delete PHONE`

* Deletes the person with `PHONE`.
  * The PHONE refers to the index number shown in the displayed person list.
  * The PHONE **must consist of 8 positive integer** 91234567, 01010101…​
  * Confirm with y/n after delete command was entered

Examples:
* `delete 91234567` deletes the person with said phone number in CLIentTracker.

### Clearing all entries : `clear`

Clears all entries from CLIentTracker.

Format: `clear`

### Favourites: `mark` and `unmark`

*Add or remove contacts from favourites*

Format: `mark INDEX` or `unmark INDEX`
* Mark `INDEX` adds the contact at the specified index to favourites
  * Unmark `INDEX` removes the contact at INDEX from favourites
  * `INDEX` refers to the index number shown on the displayed person list
    * `INDEX` must be a valid number in the list

Examples:
* `mark 1` <br> ![mark 1 image](./images/mark_1_image.png)
  * `unmark 1` <br> ![unmark_2_iumage](./images/unmark_2_image.png)

### Listing all persons: `list`

Shows a list of all persons in the CLIentTracker.

Format: `list`

### Viewing help: `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Exiting the program: `exit`

Exits the program.

Format: `exit`


### :floppy_disk: Data Storage & Saving

CLIentTracker is designed to be **fast and worry-free** — your data is automatically saved after every command.

There is **no need to press a save button**. Everything is stored locally on your device, allowing you to:
- Work completely offline
  - Access your data instantly
  - Avoid losing changes due to unsaved progress

All data is stored in:

`[JAR file location]/data/addressbook.json`

### :pencil2: Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" cl ass="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>


--------------------------------------------------------------------------------------------------------------------

## :question: FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## :warning: Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
   2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

