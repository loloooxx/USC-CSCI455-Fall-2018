// pa5list.cpp
// a program to test the linked list code necessary for a hash table chain

// You are not required to submit this program for pa5.

// We gave you this starter file for it so you don't have to figure
// out the #include stuff.  The code that's being tested will be in
// listFuncs.cpp, which uses the header file listFuncs.h

// The pa5 Makefile includes a rule that compiles these two modules
// into one executable.

#include <iostream>
#include <string>
#include <cassert>

using namespace std;

#include "listFuncs.h"


int main() {

   char c;
   bool keepTesting = true;
   string key;
   int value;
   Node* list = NULL;

   while (keepTesting) {

      cout << "Please enter a command [i, r, a, p, q, n, l, u]: ";
      cin >> c;

      if (cin.fail()) {

         cout << "ERROR: input stream failed." << endl;
         keepTesting = false;

      } else {

         switch(c) {

            case 'i':
               cout << "Insert key: ";
               cin >> key;

               cout << "Insert value ";
               cin >> value;

               listInsertFront(key, value, list);
               listPrint(list);
               break;

            case 'r':
               cout << "Remove key: ";
               cin >> key;

               listRemove(key, list);
               listPrint(list);
               break;

            case 'a':
               cout << "Append key: ";
               cin >> key;

               cout << "Append value: ";
               cin >> value;

               listAppend(key, value, list);
               listPrint(list);
               break;

            case 'p':
               cout << "Print list: " << endl;;
               listPrint(list);
               break;

            case 'q':
               keepTesting = false;
               break;

            case 'n':
               cout << "Number of list: " << listSize(list) << endl;
               break;

            case 'l':
               cout << "Look up key: ";
               cin >> key;

               if (listLookUp(key, list) == NULL) {
                  cout << "The key " << key << " does not exist." << endl;
                  break;
               } else {
                  cout << "The key " << key << " exists" << endl;
                  break;
               }

            case 'u':
               cout << "Update key: ";
               cin >> key;

               cout << "Update value: ";
               cin >> value;

               listUpdate(key, value, list);
               listPrint(list);
               break;
         }
      }
   }


   return 0;
}
