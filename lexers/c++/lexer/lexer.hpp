#ifndef _LEXER_HPP_
#define _LEXER_HPP_

#include "tokens.hpp"
#include "../file/FileBuffer.hpp"
#include <cstddef>

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
	
	Lexer(const FileBuffer<CharT> &file);
	Symbol next();
	bool isEOF() const;
	
private:
	
	const FileBuffer<CharT> &file;
	Position pos;
	
	CharT peek() const;
	CharT pop();
	
};

#include "lexer.inl"

#endif
