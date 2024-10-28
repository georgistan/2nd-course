#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    {
        std::vector<int> numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        auto isGreatThan5 = [](int n) { return n > 5; };

        auto boundary = std::partition(numbers.begin(), numbers.end(), isGreatThan5);

        // Output the numbers after partitioning
        std::cout << "Partitioned numbers: ";
        for (int n : numbers) {
            std::cout << n << " ";
        }

        // Output the boundary index
        std::cout << "\nBoundary: " << std::distance(numbers.begin(), boundary) << std::endl;
    }

    {
        std::vector<int> numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        auto isEven = [](int n) { return n % 2 == 0; };

        auto boundary = std::partition(numbers.begin(), numbers.end(), isEven);

        // Output the numbers after partitioning
        std::cout << "Partitioned numbers: ";
        for (int n : numbers) {
            std::cout << n << " ";
        }

        // Output the boundary index
        std::cout << "\nBoundary: " << std::distance(numbers.begin(), boundary) << std::endl;
    }

    {
        std::vector<int> numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        auto is8 = [](int n) { return n == 8; };

        auto boundary = std::partition(numbers.begin(), numbers.end(), is8);

        // Output the numbers after partitioning
        std::cout << "Partitioned numbers: ";
        for (int n : numbers) {
            std::cout << n << " ";
        }

        // Output the boundary index
        std::cout << "\nBoundary: " << std::distance(numbers.begin(), boundary) << std::endl;
    }

    {
        std::vector<int> numbers = {1, 2, 3, -8, 4, 5, 6, 7, 8, 9, 10};

        int x = 0;
        auto isEven = [x](int n) { return std::abs(n) == x; };

        auto boundary = std::partition(numbers.begin(), numbers.end(), isEven);

        // Output the numbers after partitioning
        std::cout << "Partitioned numbers: ";
        for (int n : numbers) {
            std::cout << n << " ";
        }

        // Output the boundary index
        std::cout << "\nBoundary: " << std::distance(numbers.begin(), boundary) << std::endl;
    }
}