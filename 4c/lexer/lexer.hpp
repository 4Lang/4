#ifndef _LEXER_HPP_
#define _LEXER_HPP_

#include "tokens.hpp"
#include <cstddef>
#include <istream>
#include <string>
#include <vector>

struct Position {
	std::size_t line     = 1;
	std::size_t column   = 1;
	std::size_t position = 0;
};

struct Symbol {
	Token token;
	Position start, end;
};

template <class CharT> class Lexer {
public:
	
	Lexer(std::basic_istream<CharT> &stream);
	Symbol next();
	
private:
	
	Position pos;
	std::vector<CharT> file;
	
	CharT peek() const;
	CharT pop();
	bool isEOF() const;
	
};

#include "lexer.inl"

#endif
