#include <iostream>

class BoolVector {
    private:
        size_t size;
        size_t capacity;
        uint8_t* data;

        void resize(size_t newCapacity);
        bool get_bit(size_t index) const;
        void set_bit(size_t index, bool value);

    public:
        BoolVector();
        ~BoolVector();

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

        size_t getSize() const;

        class Iterator {
            private:
                BoolVector* vector;
                size_t index;

            public:
                Iterator(BoolVector* vec, size_t index);

                Iterator& operator++();
                Iterator operator++(int);
                Iterator& operator--();
                Iterator operator--(int);
                Iterator operator*();
                Iterator operator==(const Iterator& other) const;
                Iterator operator!=(const Iterator& other) const;
        };

        Iterator begin();
        Iterator end();

        class ConstIterator {
            private:
                BoolVector* vector;
                size_t index;

            public:
                ConstIterator& operator++();
                ConstIterator operator++(int);
                ConstIterator& operator--();
                ConstIterator operator--(int);
                ConstIterator operator*();
                ConstIterator operator==(const ConstIterator& other) const;
                ConstIterator operator!=(const ConstIterator& other) const;
        };

        ConstIterator begin() const;
        ConstIterator end() const;

        class ReverseIterator {
            private:
                BoolVector* vector;
                size_t index;

            public:
                ReverseIterator& operator++();
                ReverseIterator operator++(int);
                ReverseIterator& operator--();
                ReverseIterator operator--(int);
                ReverseIterator operator*();
                ReverseIterator operator==(const ReverseIterator& other) const;
                ReverseIterator operator!=(const ReverseIterator& other) const;
        };

        ConstIterator rbegin();
        ConstIterator rend();
};