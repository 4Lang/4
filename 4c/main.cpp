#include <fstream>
#include <iostream>
#include <codecvt>
#include "lexer/lexer.hpp"

int main(int argc, char* args[]) {
	
	if (argc != 2) {
		std::cout << "Filename not specified.";
		return -1;
	}
	
	typedef char32_t CharT;
	std::basic_ifstream<CharT> input(args[1], std::ios_base::binary);
	input.imbue(std::locale(input.getloc(),
		new std::codecvt_utf8<CharT, 0x10ffff, std::consume_header>
	));
	
	if (!input.is_open()) {
		std::cout << "Failed to open " << args[1] << ".";
		return -1;
	}
	
	Lexer<CharT> lexer(input);
	input.close();
	
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
