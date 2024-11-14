#include "BoolVector.hpp"

static size_t BUCKET_SIZE = 8;

BoolVector::BoolVector() {
    size = 0;
    capacity = BUCKET_SIZE;
    data = new uint8_t[1];
    data[0] = 0;
}

BoolVector::~BoolVector() {
    delete[] data;
}

void BoolVector::resize(size_t newCapacity) {
    size_t new_byte_size = (newCapacity + 7) / BUCKET_SIZE;
    uint8_t* newData = new uint8_t[new_byte_size];

    std::memset(newData, 0, new_byte_size);

    size_t byte_size = (capacity + 7) / BUCKET_SIZE;

    std::memcpy(newData, data, byte_size);

    delete[] data;

    data = newData;
    capacity = newCapacity;
}

void BoolVector::push_back(bool value) {
    if (size == capacity) {
        resize(capacity * 2);
    }

    set_bit(size, value);
    
    size++;
}