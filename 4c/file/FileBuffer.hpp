#ifndef _FILEBUFFER_HPP_
#define _FILEBUFFER_HPP_

#include <vector>
#include <istream>

template <class CharT> class FileBuffer {
public:
	
	using iterator  = typename std::vector<CharT>::const_iterator;
	using size_type = typename std::vector<CharT>::size_type;
	
	FileBuffer(const char* file);
	bool good() const;
	iterator begin() const;
	iterator end() const;
	size_type size() const;
	
private:
	
	std::vector<CharT> buffer;
	
	void read(const char* file);
	void read(std::basic_istream<CharT> &stream);
	
};

#include "FileBuffer.inl"

#endif
