#include <iostream>

#include <cassert>

#include "listFuncs.h"

using namespace std;

Node::Node(const string &theKey, int theValue) {
   key = theKey;
   value = theValue;
   next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
   key = theKey;
   value = theValue;
   next = n;
}




//*************************************************************************
// put the function definitions for your list functions below


bool listAppend(const std::string &theKey, int theValue, ListType &list) {

   Node* node = list;
   Node* preNode = NULL; // to record the previous node
   Node* newNode = new Node(theKey, theValue); // create a new node which will be appended to the list

   // the linked list in this bucket is empty
   if (node == NULL) {
      list = newNode; // the list only contains new node
      return true;
   }

   // traverse to the last node in the list
   while (node != NULL) {

      // if encounter the same key of the entry, print a message to that effect and don't do the insert
      if (node->key == theKey) {
         cout << "Insert failed. This name is already present." << endl;
         return false;
      }

      preNode = node; // record the previous node
      node = node->next; // move to next node
   }

   node = preNode; // make node point to the last node in the list
   node->next = newNode; // node points to the new node
   return true;
}

bool listRemove(const std::string &theKey, ListType &list) {

   // the linked list in this bucket is empty, so cannot find the name
   if (list == NULL) {
      cout << "Remove failed. This name isn't present." << endl;
      return false;
   }

   Node* node = list;
   Node* preNode = NULL; // to record the previous node

   // the first node is the target to remove
   if (node->key == theKey) {

      list = node->next; // list points to the second node
      delete node; // delete the first node

      return true;
   }

   // traverse in the list to find the target
   while (node != NULL) {

      // find the target
      if (node->key == theKey) {

         preNode->next = node->next; // previous node points to the next node
         delete node; // delete the target, the current node

         return true;
      }

      preNode = node; // record the previous node
      node = node->next; // move to next node
   }

   // connot find the target
   cout << "Remove failed. This name isn't present." << endl;
   return false;
}

int listSize(ListType &list) {

   int size = 0;
   Node* node = list;

   // traverse the list and count the number of entries
   while (node != NULL) {
      size++;
      node = node->next;
   }

   return size;
}

ListType listLookUp(const std::string &theKey, ListType &list) {

   Node* node = list;

   // traverse the list to find the target
   while (node != NULL) {

      // find the target and return ListType(node)
      if (node->key == theKey) {
         return node;
      }

      node = node->next;
   }

   // connot find the target
   cout << "This name isn't present." << endl;
   return NULL;
}

void listPrint(ListType &list) {

   Node* node = list;

   // traverse the list and print out the entry
   while (node != NULL) {
      cout << node->key << " " << node->value << endl;
      node = node->next;
   }
}