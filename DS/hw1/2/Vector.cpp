#include <stdexcept>
#include <cstdint>
#include <cstring>

class Vector {
private:
    size_t size_;
    size_t capacity_;
    uint8_t* data_;

public:
    Vector() : size_(0), capacity_(8) {
        data_ = new uint8_t[1];
        data_[0] = 0;
    }

    ~Vector() {
        delete[] data_;
    }

    void push_back(bool value) {
        if (size_ == capacity_) {
            resize(capacity_ * 2);
        }
        set_bit(size_, value);
        ++size_;
    }

    void pop_back() {
        if (size_ == 0) {
            throw std::out_of_range("Vector is empty");
        }
        --size_;
    }

    void pop_front() {
        if (size_ == 0) {
            throw std::out_of_range("Vector is empty");
        }
        for (size_t i = 1; i < size_; ++i) {
            bool value = get_bit(i);
            set_bit(i - 1, value);
        }
        --size_;
    }

    void insert(size_t index, bool value) {
        if (index > size_) {
            throw std::out_of_range("Index out of range");
        }
        if (size_ == capacity_) {
            resize(capacity_ * 2);
        }
        for (size_t i = size_; i > index; --i) {
            bool val = get_bit(i - 1);
            set_bit(i, val);
        }
        set_bit(index, value);
        ++size_;
    }

    void remove(size_t index) {
        if (index >= size_) {
            throw std::out_of_range("Index out of range");
        }
        for (size_t i = index; i < size_ - 1; ++i) {
            bool val = get_bit(i + 1);
            set_bit(i, val);
        }
        --size_;
    }

    class reference {
    private:
        Vector& vec_;
        size_t index_;
    public:
        reference(Vector& vec, size_t index) : vec_(vec), index_(index) {}

        operator bool() const {
            return vec_.get_bit(index_);
        }

        reference& operator=(bool value) {
            vec_.set_bit(index_, value);
            return *this;
        }

        reference& operator=(const reference& ref) {
            return *this = static_cast<bool>(ref);
        }
    };

    reference operator[](size_t index) {
        if (index >= size_) {
            throw std::out_of_range("Index out of range");
        }
        return reference(*this, index);
    }

    bool operator[](size_t index) const {
        if (index >= size_) {
            throw std::out_of_range("Index out of range");
        }
        return get_bit(index);
    }

    size_t size() const {
        return size_;
    }

    class iterator {
    public:
        iterator(Vector* vec, size_t index) : vec_(vec), index_(index) {}

        iterator& operator++() {
            ++index_;
            return *this;
        }

        iterator operator++(int) {
            iterator temp = *this;
            ++*this;
            return temp;
        }

        iterator& operator--() {
            --index_;
            return *this;
        }

        iterator operator--(int) {
            iterator temp = *this;
            --*this;
            return temp;
        }

        reference operator*() {
            return (*vec_)[index_];
        }

        bool operator==(const iterator& other) const {
            return index_ == other.index_;
        }

        bool operator!=(const iterator& other) const {
            return index_ != other.index_;
        }

    private:
        Vector* vec_;
        size_t index_;
    };

    class const_iterator {
    public:
        const_iterator(const Vector* vec, size_t index) : vec_(vec), index_(index) {}

        const_iterator& operator++() {
            ++index_;
            return *this;
        }

        const_iterator operator++(int) {
            const_iterator temp = *this;
            ++*this;
            return temp;
        }

        const_iterator& operator--() {
            --index_;
            return *this;
        }

        const_iterator operator--(int) {
            const_iterator temp = *this;
            --*this;
            return temp;
        }

        bool operator*() const {
            return (*vec_)[index_];
        }

        bool operator==(const const_iterator& other) const {
            return index_ == other.index_;
        }

        bool operator!=(const const_iterator& other) const {
            return index_ != other.index_;
        }

    private:
        const Vector* vec_;
        size_t index_;
    };

    class reverse_iterator {
    public:
        reverse_iterator(Vector* vec, size_t index) : vec_(vec), index_(index) {}

        reverse_iterator& operator++() {
            --index_;
            return *this;
        }

        reverse_iterator operator++(int) {
            reverse_iterator temp = *this;
            --*this;
            return temp;
        }

        reverse_iterator& operator--() {
            ++index_;
            return *this;
        }

        reverse_iterator operator--(int) {
            reverse_iterator temp = *this;
            ++*this;
            return temp;
        }

        reference operator*() {
            return (*vec_)[index_];
        }

        bool operator==(const reverse_iterator& other) const {
            return index_ == other.index_;
        }

        bool operator!=(const reverse_iterator& other) const {
            return index_ != other.index_;
        }

    private:
        Vector* vec_;
        size_t index_;
    };

    iterator begin() {
        return iterator(this, 0);
    }

    iterator end() {
        return iterator(this, size_);
    }

    const_iterator begin() const {
        return const_iterator(this, 0);
    }

    const_iterator end() const {
        return const_iterator(this, size_);
    }

    reverse_iterator rbegin() {
        return reverse_iterator(this, size_ - 1);
    }

    reverse_iterator rend() {
        return reverse_iterator(this, static_cast<size_t>(-1));
    }

private:

    void resize(size_t new_capacity) {
        size_t new_byte_size = (new_capacity + 7) / 8;
        uint8_t* new_data = new uint8_t[new_byte_size];
        std::memset(new_data, 0, new_byte_size);
        size_t byte_size = (capacity_ + 7) / 8;
        std::memcpy(new_data, data_, byte_size);
        delete[] data_;
        data_ = new_data;
        capacity_ = new_capacity;
    }

    bool get_bit(size_t index) const {
        size_t byte_index = index / 8;
        size_t bit_index = index % 8;
        return (data_[byte_index] >> bit_index) & 1;
    }

    void set_bit(size_t index, bool value) {
        size_t byte_index = index / 8;
        size_t bit_index = index % 8;
        if (value) {
            data_[byte_index] |= (1 << bit_index);
        } else {
            data_[byte_index] &= ~(1 << bit_index);
        }
    }
};
