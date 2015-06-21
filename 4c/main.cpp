#include "file/FileBuffer.hpp"
#include "lexer/lexer.hpp"
#include <iostream>

int main(int argc, char* args[]) {
	
	typedef char32_t CharT;
	
	if (argc != 2) {
		std::cout << "Filename not specified.";
		return -1;
	}
	
	FileBuffer<CharT> file(args[1]);
	
	if (!file.good()) {
		std::cout << "Failed to read " << args[1] << ".";
		return -1;
	}
	
	Lexer<CharT> lexer(file);
	Symbol symbol;
	do {
		symbol = lexer.next();
		std::cout << TokenName(symbol.token) << " [";
		std::cout << symbol.start.line << ":" << symbol.start.column;
		std::cout << " - ";
		std::cout << symbol.end.line << ":" << symbol.end.column;
		std::cout << "]" << std::endl;
	} while (symbol.token != Token::END);
	
	std::cout << "Finished.";
	
	return 0;
}
