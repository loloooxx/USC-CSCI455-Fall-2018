//*************************************************************************
// Node class definition
// and declarations for functions on ListType

// Note: we don't need Node in Table.h
// because it's used by the Table class; not by any Table client code.

// Note2: it's good practice to not put "using" statement in header files.  Thus
// here, things from std libary appear as, for example, std::string

#ifndef LIST_FUNCS_H
#define LIST_FUNCS_H


struct Node {
   std::string key;
   int value;

   Node *next;

   Node(const std::string &theKey, int theValue);

   Node(const std::string &theKey, int theValue, Node *n);
};


typedef Node* ListType;

//*************************************************************************
//add function headers (aka, function prototypes) for your functions
//that operate on a list here (i.e., each includes a parameter of type
//ListType or ListType&).  No function definitions go in this file.

/**
   Append the entry at the end of the list.

   @param theKey  the key of the entry which will be append
   @param theValue  the value of the entry which will be append
   @param list  the list which will be appended a entry

   @return true iff the entry is appended successfully
 */
bool listAppend(const std::string &theKey, int theValue, ListType &list);

/**
   Remove entry in the list.

   @param theKey  the key of the entry which will be removed
   @param list  the list which will be removed a entry

   @return true iff the entry is removed successfully
 */
bool listRemove(const std::string &theKey, ListType &list);

/**
   Return the number of entries of the list.

   @param list  the list which will be computed the size

   @return the number of entries
 */
int listSize(ListType &list);

/**
   Search the enrty in the list

   @param theKey  the key of the entry which will be searched for
   @param list  the list which will be searched for a entry

   @return the ListType iff the entry exists. Return null iff the entry does not exist in the list
 */
ListType listLookUp(const std::string &theKey, ListType &list);

/**
   Print out all entries of the list

   @param list  the list which will be printed out
 */
void listPrint(ListType &list);


// keep the following line at the end of the file
#endif
