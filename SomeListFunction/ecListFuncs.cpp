#include <iostream>
#include <vector>

#include "ecListFuncs.h"

using namespace std;


ListType vectorToList(const vector<int> & nums) {

   Node *list;
   list = new Node(nums[0]);

   // create a Node* which points to the first node of the list
   Node *pointToFront = list;

   // traverse the vector in forward order
   for (unsigned int i = 1; i < nums.size(); i++) {
      Node *temp = new Node(nums[i]);
      list->next = temp;
      list = temp;
   }

   // the last node in list points to null
   list->next = NULL;

   return pointToFront;
}

int countRuns(ListType list) {

   Node *node = list;
   int count = 0;

   while (node != NULL) {
      Node *preNode = node;
      node = node->next;

      // ajacecent sequence two of the same value can count as a run
      if (node != NULL && preNode->data == node->data) {
         count++;
      }

      // ajacecent sequence two more of the same value would still count as a run
      while (node != NULL && preNode->data == node->data) {
         node = node->next;
      }
   }
   return count;
}

ListType reverse(ListType list) {

   Node *node = list;
   Node *result = NULL;

   while (node != NULL) {
      // use insertFront function to create a new Node which points to current node
      insertFront(result, node->data);
      node = node->next;

   }
   return result;
}

void removeMiddle(ListType &list) {

   Node *node = list;

   if (node == NULL) {
      return;
   }

   int size = 0;

   // compute the size of the list
   while (node != NULL) {
      size++;
      node = node->next;
   }

   delete node;

   if (size == 0) {
      return;
   }

   // if size equals one or two, we should remove the first node
   if (size == 1 || size == 2) {
      Node *tempList = list;
      list = list->next;
      delete tempList;
      return;
   }

   // if size is more than two, find the middle of the list
   int mid = (size - 1) / 2;
   Node *temp = list;
   Node *preNode = NULL;

   // traverse to the middle node
   for (int i = 0; i < mid; i++) {
      preNode = temp;
      temp = temp->next;
   }

   // make previous node points to next node of the millde node
   preNode->next = temp->next;
   delete temp;
}

void split(ListType &list, int splitVal, ListType &a, ListType &b) {

   if (list == NULL) {
      return;
   }

   a = list;

   // if the first node's data equals splitVal
   if (list->data == splitVal) {
      b = list->next;
      a = NULL;
      delete list;

      list = NULL;
      return;
   }

   Node *preNode = NULL;

   // traverse the list to find the splitVal
   while (list != NULL){
      // find the splitVal
      if (list->data == splitVal) {
         preNode->next = NULL;
         b = list->next;
         delete list;
         break;
      }

      preNode = list;
      list = list->next;
   }
   list = NULL;
}
