/*
 * grades.cpp
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 *
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

using namespace std;

// additional functions create for executing this program, function definitions are as below
void commands(Table* &grades);
void help();

int main(int argc, char * argv[]) {

   // gets the hash table size from the command line

   int hashSize = Table::HASH_SIZE;

   Table * grades;  // Table is dynamically allocated below, so we can call
   // different constructors depending on input from the user.

   if (argc > 1) {
      hashSize = atoi(argv[1]);  // atoi converts c-string to int

      if (hashSize < 1) {
         cout << "Command line argument (hashSize) must be a positive number"
              << endl;
         return 1;
      }

      grades = new Table(hashSize);

   }
   else {   // no command line args given -- use default table size
      grades = new Table();
   }


   grades->hashStats(cout);

   // add more code here
   // Reminder: use -> when calling Table methods, since grades is type Table*

   commands(grades); // users type in appropriate commands to execute this program

   return 0;
}

/**
   This program repeatedly reads and executes commands from the user,
   printing out the command prompt (cmd>) after it finishes the previous
   command, until the user enters the quit command.

   @param grades  the Table we creates
 */
void commands(Table* &grades) {

   // the command, name and score which users will type in
   string command, name;
   int score;
   bool keepTesting = true;

   while (keepTesting) {

      cout << "cmd> ";
      cin >> command;

      if (command == "insert") {

         cin >> name >> score;
         grades->insert(name, score);

      } else if (command == "change") {

         cin >> name >> score;

         // use lookup function to find the target
         if(int* target = grades->lookup(name)) {
            *target = score; // update the value of the entry
            cout << name << "'s score changes to " << score << "." << endl;
         }

      } else if (command == "lookup") {

         cin >> name;

         if (int* score = grades->lookup(name)) {
            cout << name << " got " << *score << "." << endl;
         }

      } else if (command == "remove") {

         cin >> name;
         grades->remove(name);

      } else if (command == "print") {

         grades->printAll();

      } else if (command == "size") {

         cout << "The number of entries: " << grades->numEntries() << endl;

      } else if (command == "stats") {

         grades->hashStats(cout);

      } else if (command == "help") {

         help();

      } else if (command == "quit") {

         keepTesting = false;

      } else {

         cout << "ERROR: invalid command." << endl;
         help();
      }
   }
}

/**
   The brief command summary.
 */
void help() {

   cout << "Valid commands: " << endl;
   cout << "insert name score     -- Insert this name and score in the grade table." << endl;
   cout << "change name newscore  -- Change the score for name." << endl;
   cout << "lookup name           -- Lookup the name, and print out his or her score." << endl;
   cout << "remove name           -- Remove this student." << endl;
   cout << "print                 -- Prints out all names and scores in the table." << endl;
   cout << "size                  -- Prints out the number of entries in the table." << endl;
   cout << "stats                 -- Prints out statistics about the hash table at this point." << endl;
   cout << "help                  -- Prints out a brief command summary." << endl;
   cout << "quit                  -- Exits the program." << endl;

}
