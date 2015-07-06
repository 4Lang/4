#include <codecvt>
#include <fstream>

template <class CharT>
FileBuffer<CharT>::FileBuffer(const char* file) {
	read(file);
}

template <class CharT>
bool FileBuffer<CharT>::good() const {
	return !buffer.empty();
}

template <class CharT>
typename FileBuffer<CharT>::iterator FileBuffer<CharT>::begin() const {
	return buffer.cbegin();
}

template <class CharT>
typename FileBuffer<CharT>::iterator FileBuffer<CharT>::end() const {
	return buffer.cend();
}

template <class CharT>
typename FileBuffer<CharT>::size_type FileBuffer<CharT>::size() const {
	return buffer.size();
}

template <class CharT>
void FileBuffer<CharT>::read(const char* file) {
	std::basic_ifstream<CharT> stream(file, std::ios_base::binary);
	if (!stream.is_open()) return;
	read(stream);
}

template <class CharT>
void FileBuffer<CharT>::read(std::basic_istream<CharT> &stream) {
	stream.imbue(std::locale(stream.getloc(),
		new std::codecvt_utf8<CharT, 0x10ffff, std::consume_header>
	));
	buffer.assign(
		std::istreambuf_iterator<CharT>(stream),
		std::istreambuf_iterator<CharT>()
	);
}

template <>
void FileBuffer<char>::read(std::istream &stream) {
	buffer.assign(
		std::istreambuf_iterator<char>(stream),
		std::istreambuf_iterator<char>()
	);
}
