#include <iostream>

class BoolVector {
    private:
        size_t size;
        size_t capacity;
        uint8_t* data;

        void resize(size_t newCapacity);
        bool getBit(size_t index) const;
        void setBit(size_t index, bool value);

        void free();
        void copyFrom(const BoolVector& other);
        void moveFrom(BoolVector&& other);

    public:
        BoolVector();
        BoolVector(const BoolVector& other);
        BoolVector(BoolVector&& other);
        ~BoolVector();

        BoolVector& operator=(const BoolVector& other);
        BoolVector& operator=(BoolVector&& other);

        size_t getSize() const;
        size_t getCapacity() const;

        void setSize(size_t newSize);
        void setCapacity(size_t newCapacity);

        void push_back(bool value);
        void pop_back();
        void pop_front();
        void insert(size_t index, bool value);
        void remove(size_t index);

        class Reference {
            private:
                BoolVector& vector;
                size_t index;

            public:
                Reference(BoolVector& vec, size_t index);

                operator bool() const;
                Reference& operator=(bool value);
                Reference& operator=(const Reference& reference);
        };

        Reference operator[](size_t index);
        bool operator[](size_t index) const;

        class Iterator {
            private:
                BoolVector* vector;
                size_t index;

            public:
                Iterator(BoolVector* vector, size_t index);

                Iterator& operator++();
                Iterator operator++(int);
                Iterator& operator--();
                Iterator operator--(int);
                Reference operator*();
                bool operator==(const Iterator& other) const;
                bool operator!=(const Iterator& other) const;
        };

        Iterator begin();
        Iterator end();

        class ConstIterator {
            private:
                const BoolVector* vector;
                size_t index;

            public:
                ConstIterator(const BoolVector* vector, size_t index);

                ConstIterator& operator++();
                ConstIterator operator++(int);
                ConstIterator& operator--();
                ConstIterator operator--(int);
                bool operator*() const;
                bool operator==(const ConstIterator& other) const;
                bool operator!=(const ConstIterator& other) const;
        };

        ConstIterator c_begin() const;
        ConstIterator c_end() const;

        class ReverseIterator {
            private:
                BoolVector* vector;
                size_t index;

            public:
                ReverseIterator(BoolVector* vector, size_t index);

                ReverseIterator& operator++();
                ReverseIterator operator++(int);
                ReverseIterator& operator--();
                ReverseIterator operator--(int);
                Reference operator*();
                bool operator==(const ReverseIterator& other) const;
                bool operator!=(const ReverseIterator& other) const;
        };

        ReverseIterator rbegin();
        ReverseIterator rend();
};