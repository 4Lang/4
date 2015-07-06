#include "emoji.hpp"
#include <cctype>

template <class CharT> 
Lexer<CharT>::Lexer(const FileBuffer<CharT> &file)
: file(file) {}

template <class CharT> bool Lexer<CharT>::isEOF() const {
	return file.begin() + pos.position == file.end();
}

template <class CharT> CharT Lexer<CharT>::peek() const {
	return *(file.begin() + pos.position);
}

template <class CharT> CharT Lexer<CharT>::pop() {
	CharT c = *(file.begin() + pos.position++);
	pos.column++;
	if (c == static_cast<CharT>('\n')) {
		pos.line++;
		pos.column = 1;
	}
	return c;
}

template <class CharT>
Symbol Lexer<CharT>::next() {
	Symbol symbol;
	const CharT newline = static_cast<CharT>('\n');
	while (!isEOF() && peek() == newline) pop();
	symbol.start = symbol.end = pos;
	while (!isEOF() && peek() != newline && std::isspace(peek())) pop();
	
	if (symbol.start.position != pos.position) {
		symbol.token = symbol.start.column == 1 ? Token::INDENT : Token::WHITESPACE;
		symbol.end = pos;
		return symbol;
	} else if (isEOF()) {
		symbol.token = Token::END;
		return symbol;
	} else if (peek() == 0x1F4AD) {
		while (!isEOF() && peek() != newline) pop();
		symbol.token = Token::COMMENT;
		symbol.end = pos;
		return symbol;
	} else if (peek() == 0x1F532) {
		while (!isEOF() && peek() != 0x1F533) pop();
		symbol.token = Token::COMMENT;
		symbol.end = pos;
		return symbol;
	} else if (peek() == 0x1F4AD) {
		while (!isEOF() && peek() != 0x1F4AD && peek() != newline) pop();
		if (isEOF() || peek() == newline) {
			// TODO report error
		}
		symbol.token = Token::STR_LITERAL;
		symbol.end = pos;
		return symbol;
	} else if (std::isdigit(peek())) {
		while (!isEOF() && std::isdigit(peek())) pop();
		if (peek() == 0x2139 || peek() == '.') {
			pop();
			while (!isEOF() && std::isdigit(peek())) pop();
			symbol.token = Token::FLT_LITERAL;
		} else {
			symbol.token = Token::INT_LITERAL;
		}
		symbol.end = pos;
		return symbol;
	}
	
	Token token;
	switch (peek()) {
	case 0x2B05: pop(); token = Token::EQUALS; break;
	case 0x2139: pop(); token = Token::DOT;    break;
	case 0x23EA:
		pop(); token = Token::RIGHT_OP;
		if (peek() == 0x2B05) token = (pop(), Token::RIGHT_ASSIGN);
		break;
	case 0x23E9:
		pop(); token = Token::LEFT_OP;
		if (peek() == 0x2B05) token = (pop(), Token::LEFT_ASSIGN);
		break;
	case 0x274C:
		pop(); token = Token::AND;
		if      (peek() == 0x2B05) token = (pop(), Token::AND_ASSIGN);
		else if (peek() == 0x274C) token = (pop(), Token::AND_OP);
		break;
	case 0x2B55:
		pop(); token = Token::OR;
		if      (peek() == 0x2B05) token = (pop(), Token::OR_ASSIGN);
		else if (peek() == 0x2B55) token = (pop(), Token::OR_OP);
		break;
	case 0x2B06:
		pop(); token = Token::XOR;
		if (peek() == 0x2B05) token = (pop(), Token::XOR_ASSIGN);
		break;
	case 0x2796:
		pop(); token = Token::SUB;
		if (peek() == 0x2B05) token = (pop(), Token::SUB_ASSIGN);
		break;
	case 0x2795:
		pop(); token = Token::ADD;
		if (peek() == 0x2B05) token = (pop(), Token::ADD_ASSIGN);
		break;
	case 0x2716:
		pop(); token = Token::MUL;
		if (peek() == 0x2B05) token = (pop(), Token::MUL_ASSIGN);
		break;
	case 0x2797:
		pop(); token = Token::DIV;
		if (peek() == 0x2B05) token = (pop(), Token::DIV_ASSIGN);
		break;
	case 0x1F53C:
		pop(); token = Token::POW;
		if (peek() == 0x2B05) token = (pop(), Token::POW_ASSIGN);
		break;
	case '#':
		pop(); token = Token::MOD;
		if (peek() == 0x20E3) pop();
		if (peek() == 0x2B05) token = (pop(), Token::MOD_ASSIGN);
		break;
	case 0x1F6AB:
		pop(); token = Token::NOT;
		if (peek() == 0x2194) token = (pop(), Token::NE_OP);
		break;
	case 0x23EB:  pop(); token = Token::INC_OP;   break;
	case 0x23EC:  pop(); token = Token::DEC_OP;   break;
	case 0x23ED:  pop(); token = Token::LE_OP;    break;
	case 0x23EE:  pop(); token = Token::GE_OP;    break;
	case 0x2194:  pop(); token = Token::EQ_OP;    break;
	case 0x3030:  pop(); token = Token::TILDE;    break;
	case 0x1F4CC: pop(); token = Token::COMMA;    break;
	case 0x21A9:  pop(); token = Token::COLON;    break;
	case 0x1F31C: pop(); token = Token::LPARENT;  break;
	case 0x1F31B: pop(); token = Token::RPARENT;  break;
	case 0x2198:  pop(); token = Token::LBRACKET; break;
	case 0x2199:  pop(); token = Token::RBRACKET; break;
	case 0x1F340: pop(); token = Token::VAR;      break;
	case 0x2753:  pop(); token = Token::IF;       break;
	case 0x2757:  pop(); token = Token::ELSE;     break;
	case 0x2049:  pop(); token = Token::ELSEIF;   break;
	case 0x1F503: pop(); token = Token::LOOP;     break;
	case 0x1F6A6: pop(); token = Token::SWITCH;   break;
	case 0x1F4A1: pop(); token = Token::CASE;     break;
	case 0x1F526: pop(); token = Token::DEFAULT;  break;
	case 0x270B:  pop(); token = Token::BREAK;    break;
	case 0x21AA:  pop(); token = Token::CONTINUE; break;
	case 0x1F3C3: pop(); token = Token::GOTO;     break;
	case 0x2934:  pop(); token = Token::RETURN;   break;
	case 0x1F3E2: pop(); token = Token::STRUCT;   break;
	case 0x1F44D: pop(); token = Token::TRUE;     break;
	case 0x1F44E: pop(); token = Token::FALSE;    break;
	case 0x26A0:  pop(); token = Token::TRY;      break;
	case 0x26D4:  pop(); token = Token::CATCH;    break;
	case 0x1F3C1: pop(); token = Token::FINALLY;  break;
	}
	
	if (symbol.start.position != pos.position) {
		symbol.token = token;
		symbol.end = pos;
		return symbol;
	}
	
	if (isReservedEmoji(peek())) {
		// TODO report error;
		pop();
		return next();
	}
	
	if (!std::isalpha(peek()) && !isEmoji(peek())) {
		// TODO report error;
		pop();
		return next();
	}
	
	while (!isEOF() && !isReservedEmoji(peek())) {
		if (!std::isalnum(peek()) && !isEmoji(peek())) break;
		pop();
	}
	
	symbol.token = Token::IDENTIFIER;
	symbol.end = pos;
	return symbol;
}
