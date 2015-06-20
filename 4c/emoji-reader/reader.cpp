#include <iostream>
#include <fstream>
#include <string>

#include <unordered_set>

// http://pastebin.com/ugAQjABX

using namespace std;

static std::unordered_set<string> emojis;

int main () {
  string line;
  ifstream myfile ("emoji-data.txt");
  if (myfile.is_open())
  {
    while ( getline (myfile,line) )
    {
	  if (line.at(0)=='#')
		  continue;
	  
	  line = line.substr(0, line.find(";", 1) + ",");
	  
	  int spaces = 0;
	  for(char& c : line) {
	    if (c == ' ')
			spaces++;
      }
	  
	  if (spaces >= 2)
		  continue;
	  
	  line.insert(0, "0x");
	  
	  emojis.insert(line);
	  
      cout << line << '\n';
    }
    myfile.close();
	
  }

  else cout << "Unable to open file"; 

  return 0;
}