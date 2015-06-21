#include <iostream>
#include <fstream>
#include <string>

#include <unordered_set>

// http://pastebin.com/ugAQjABX

using namespace std;

int main () {
  ofstream output;
  output.open ("emoji.hpp");

	
  string line;
  ifstream myfile ("emoji-data.txt");
  if (myfile.is_open())
  {
	output << "#ifndef _EMOJI_HPP_"; output << "\n";
	output << "#define _EMOJI_HPP_"; output << "\n";
	output << "\n";
	output << "template <class CharT> bool isEmoji(CharT c) {"; output << "\n";
	output << "\n";
	output << "\t"; output << "static const std::unordered_set<CharT> emojis = {"; output << "\n";	
	  
    while ( getline (myfile,line) )
    {
	  if (line.at(0)=='#')
		  continue;
	  
	  line = line.substr(0, line.find(";", 1)) + ",";
	  
	  int spaces = 0;
	  for(char& c : line) {
	    if (c == ' ')
			spaces++;
      }
	  
	  if (spaces >= 2)
		  continue;
	  
	  line.insert(0, "0x");
	  
	  output << "\t";
	  output << "\t";
	  output << line;
	  output << "\n";
	  
      cout << line;
    }
    myfile.close();
	
	output << " \n \t"; output << " };";
	output << " \n \n";
	output << "\t"; output << "return emojis.count(c);";
	output << " \n } ";
	output << " \n #endif";
	
	output.close();
	
  }

  else cout << "Unable to open file"; 

  return 0;
}