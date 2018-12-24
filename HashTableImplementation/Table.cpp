// Table.cpp  Table class implementation


#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>

using namespace std;


// listFuncs.h has the definition of Node and its methods.  -- when
// you complete it it will also have the function prototypes for your
// list functions.  With this #include, you can use Node type (and
// Node*, and ListType), and call those list functions from inside
// your Table methods, below.

#include "listFuncs.h"


//*************************************************************************

/**
   Create an empty table and use a constant in Table to determine the size.
 */
Table::Table() {
   hashSize = HASH_SIZE;
   hashTable = new ListType[hashSize];

   for (int i = 0; i < hashSize; i++) {
      hashTable[i] = NULL;
   }
}

/**
   Create an empty table and get the size to use in a parameter.
 */
Table::Table(unsigned int hSize) {
   hashSize = hSize;
   hashTable = new ListType[hashSize];

   for (int i = 0; i < hashSize; i++) {
      hashTable[i] = NULL;
   }
}

bool Table::insert(const string &key, int value) {

   ListType &list = hashTable[hashCode(key)];
   return listAppend(key, value, list); // use listAppend function to insert
}

int* Table::lookup(const string &key) {
   ListType list = hashTable[hashCode(key)];
   ListType target = listLookUp(key, list); // use listLookUp function to de lookup, listLookUp will return ListType

   // connot find the target
   if (target == NULL) {
      return NULL;
   }

   return &(target->value); // returns the address of the value that goes with this key
}

bool Table::remove(const string &key) {

   ListType &list = hashTable[hashCode(key)];
   return listRemove(key, list); // use listRemove function to remove
}

void Table::printAll() const {
   for (int i = 0; i < hashSize; i++) {
      listPrint(hashTable[i]); // use listPrint function to print out all entries
   }
}

int Table::numEntries() const {

   int num = 0;

   for (int i = 0; i < hashSize; i++) {
      num += listSize(hashTable[i]); // use listSize to count the number of entries
   }

   return num;
}

void Table::hashStats(ostream &out) const {

   int numEntries = 0;
   int numNonEmptyBucket = 0;
   int longestChain = 0;
   int numListEntries = 0;

   // use for-loop once to compute all needed answers is more efficient than computing answers separately
   for (int i = 0; i < hashSize; i++) {

      numListEntries = listSize(hashTable[i]);

      if (numListEntries > 0) {
         numNonEmptyBucket++;
      }

      if (numListEntries > longestChain) {
         longestChain = numListEntries;
      }

      numEntries += numListEntries;
   }

   cout << "number of buckets: " << hashSize << endl;
   cout << "number of entries: " << numEntries << endl;
   cout << "number of non-empty buckets: " << numNonEmptyBucket << endl;
   cout << "longest chain: " << longestChain << endl;
}


// add definitions for your private methods here
