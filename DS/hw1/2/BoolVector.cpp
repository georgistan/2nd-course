#include "BoolVector.h"

static const size_t BUCKET_SIZE = 8;
static const size_t GROWTH_FACTOR = 2;

BoolVector::BoolVector() {
    size = 0;
    capacity = BUCKET_SIZE;
    data = new uint8_t[1];
    data[0] = 0;
}

size_t BoolVector::getSize() const {
    return size;
}

size_t BoolVector::getCapacity() const {
    return capacity;
}

void BoolVector::setSize(size_t newSize) {
    size = newSize;
}

void BoolVector::setCapacity(size_t newCapacity) {
    capacity = newCapacity;
}

void BoolVector::free() {
    size = 0;

    capacity = 0;

    delete[] data;
    data = nullptr;
}

BoolVector::~BoolVector() {
    free();
}

void BoolVector::copyFrom(const BoolVector& other) {
    size = other.getSize();
    capacity = other.getCapacity();

    data = new uint8_t[size];

    for (int i = 0; i < size; i++) {
        data[i] = other.data[i];
    }
}

BoolVector::BoolVector(const BoolVector& other) {
    copyFrom(other);
}

BoolVector& BoolVector::operator=(const BoolVector& other) {
    if (this != &other) {
        free();
        copyFrom(other);
    }

    return *this;
}

void BoolVector::moveFrom(BoolVector&& other) {
    size = other.getSize();
    other.setSize(0);

    capacity = other.getCapacity();
    other.setCapacity(0);

    data = other.data;
    other.data = nullptr;
}

BoolVector::BoolVector(BoolVector&& other) {
    moveFrom(std::move(other));
}

BoolVector& BoolVector::operator=(BoolVector&& other) {
    if(this != &other) {
        moveFrom(std::move(other));
    }
}

void BoolVector::resize(size_t newCapacity) {
    size_t newBytesCount = (newCapacity + 7) / BUCKET_SIZE;
    uint8_t* newData = new uint8_t[newBytesCount]{0};

    size_t bytesCount = (capacity + 7) / BUCKET_SIZE;
    for (size_t i = 0; i < bytesCount; i++) {
        newData[i] = data[i];
    }

    delete[] data;

    data = newData;
    capacity = newCapacity;
}

bool BoolVector::getBit(size_t index) const {
    size_t byteIndex = index / BUCKET_SIZE;
    size_t bitIndex = index % BUCKET_SIZE;

    return (data[byteIndex] >> bitIndex) & 1;
}

void BoolVector::setBit(size_t index, bool value) {
    size_t byteIndex = index / BUCKET_SIZE;
    size_t bitIndex = index % BUCKET_SIZE;

    if (value) {
        data[byteIndex] = data[byteIndex] | (1 << bitIndex);
    } else {
        data[byteIndex] = data[byteIndex] & ~(1 << bitIndex);
    }
}

void BoolVector::push_back(bool value) {
    if (size == capacity) {
        resize(capacity * GROWTH_FACTOR);
    }

    setBit(size, value);
    
    size++;
}

void BoolVector::pop_back() {
    if (size == 0) {
        throw std::out_of_range("Cannot pop, vector is empty.");
    }

    size--;
}

void BoolVector::pop_front() {
    if (size == 0) {
        throw std::out_of_range("Cannot pop, vector is empty.");
    }

    for (size_t i = 1; i < size; i++) {
        bool bit = getBit(i);
        setBit(i - 1, bit);
    }

    size--;
}

void BoolVector::insert(size_t index, bool value) {
    if (index > size) {
        throw std::invalid_argument("Invalid index to insert on.");
    }
    
    if (size == capacity) {
        resize(capacity * GROWTH_FACTOR);
    }

    for (size_t i = size; i > index; i--) {
        bool val = getBit(i - 1);
        setBit(i, val);
    }

    setBit(index, value);
    
    size++;
}

void BoolVector::remove(size_t index) {
    if (index >= size) {
        throw std::out_of_range("Index out of range");
    }

    for (size_t i = index; i < size - 1; i++) {
        bool val = getBit(i + 1);
        setBit(i, val);
    }

    size--;
}

BoolVector::Reference::Reference(BoolVector& vector, size_t index) : vector(vector), index(index) {}

BoolVector::Reference::operator bool() const {
    return vector.getBit(index);
}

BoolVector::Reference& BoolVector::Reference::operator=(bool value) {
    vector.setBit(index, value);
    return *this;
}

BoolVector::Reference& BoolVector::Reference::operator=(const Reference& ref) {
    return *this = static_cast<bool>(ref);
}

BoolVector::Reference BoolVector::operator[](size_t index) {
    if (index >= size) {
        throw std::out_of_range("Cannot get an invalid index.");
    }

    return Reference(*this, index);
}

bool BoolVector::operator[](size_t index) const {
    if (index >= size) {
        throw std::out_of_range("Cannot get an invalid index.");
    }

    return getBit(index);
}

BoolVector::Iterator::Iterator(BoolVector* vector, size_t index) : vector(vector), index(index) {}

BoolVector::Iterator& BoolVector::Iterator::operator++() {
    index++;
    
    return *this;
}

BoolVector::Iterator BoolVector::Iterator::operator++(int) {
    Iterator temp = *this;

    (*this)++;

    return temp;
}

BoolVector::Iterator& BoolVector::Iterator::operator--() {
    index--;

    return *this;
}

BoolVector::Iterator BoolVector::Iterator::operator--(int) {
    Iterator temp = *this;

    (*this)--;

    return temp;
}

BoolVector::Reference BoolVector::Iterator::operator*() {
    return (*vector)[index];
}

bool BoolVector::Iterator::operator==(const Iterator& other) const {
    return index == other.index;
}

bool BoolVector::Iterator::operator!=(const Iterator& other) const {
    return index != other.index;
}

BoolVector::Iterator BoolVector::begin() {
    return Iterator(this, 0);
}

BoolVector::Iterator BoolVector::end() {
    return Iterator(this, size);
}

BoolVector::ConstIterator::ConstIterator(const BoolVector* vector, size_t index) : vector(vector), index(index) {}

BoolVector::ConstIterator& BoolVector::ConstIterator::operator++() {
    index++;
    
    return *this;
}

BoolVector::ConstIterator BoolVector::ConstIterator::operator++(int) {
    ConstIterator temp = *this;

    (*this)++;

    return temp;
}

BoolVector::ConstIterator& BoolVector::ConstIterator::operator--() {
    index--;

    return *this;
}

BoolVector::ConstIterator BoolVector::ConstIterator::operator--(int) {
    ConstIterator temp = *this;

    (*this)--;

    return temp;
}

bool BoolVector::ConstIterator::operator*() const {
    return (*vector)[index];
}

bool BoolVector::ConstIterator::operator==(const ConstIterator& other) const {
    return index == other.index;
}

bool BoolVector::ConstIterator::operator!=(const ConstIterator& other) const {
    return index != other.index;
}

BoolVector::ConstIterator BoolVector::c_begin() const {
    return ConstIterator(this, 0);
}

BoolVector::ConstIterator BoolVector::c_end() const {
    return ConstIterator(this, size);
}

BoolVector::ReverseIterator::ReverseIterator(BoolVector* vector, size_t index) : vector(vector), index(index) {}

BoolVector::ReverseIterator& BoolVector::ReverseIterator::operator++() {
    index--;
    
    return *this;
}

BoolVector::ReverseIterator BoolVector::ReverseIterator::operator++(int) {
    ReverseIterator temp = *this;

    (*this)--;

    return temp;
}

BoolVector::ReverseIterator& BoolVector::ReverseIterator::operator--() {
    index++;

    return *this;
}

BoolVector::ReverseIterator BoolVector::ReverseIterator::operator--(int) {
    ReverseIterator temp = *this;

    (*this)++;

    return temp;
}

BoolVector::Reference BoolVector::ReverseIterator::operator*() {
    return (*vector)[index];
}

bool BoolVector::ReverseIterator::operator==(const ReverseIterator& other) const {
    return index == other.index;
}

bool BoolVector::ReverseIterator::operator!=(const ReverseIterator& other) const {
    return index != other.index;
}

BoolVector::ReverseIterator BoolVector::rbegin() {
    return ReverseIterator(this, size - 1);
}

BoolVector::ReverseIterator BoolVector::rend() {
    return ReverseIterator(this, static_cast<size_t>(-1));
}