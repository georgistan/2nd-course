#include <iostream>
#include <vector>

using namespace std;

void heapify(size_t currentElementIndex, size_t size, std::vector<int>& data) {
    int leftChildIndex = 2 * currentElementIndex + 1;
    int rightChildIndex = 2 * currentElementIndex + 2;

    int shouldGoLeft = leftChildIndex < size && data[leftChildIndex] > data[currentElementIndex];
    int shouldGoRight = rightChildIndex < size && data[rightChildIndex] > data[currentElementIndex];

    if (shouldGoLeft && shouldGoRight) {
        if (data[leftChildIndex] > data[rightChildIndex]) {
            swap(data[currentElementIndex], data[leftChildIndex]);
            heapify(leftChildIndex, size, data);
        } else {
            swap(data[currentElementIndex], data[rightChildIndex]);
            heapify(rightChildIndex, size, data);
        }
    } else if (shouldGoLeft) {
        swap(data[currentElementIndex], data[leftChildIndex]);
        heapify(leftChildIndex, size, data);
    } else if (shouldGoRight) {
        swap(data[currentElementIndex], data[rightChildIndex]);
        heapify(rightChildIndex, size, data);
    }
}

void heapSort(std::vector<int>& data) {
    for (int i = data.size() / 2 - 1; i >= 0; i--) {
        heapify(i, data.size(), data);
    }

    for (int i = data.size() - 1; i >= 0; i--) {
        std::swap(data[0], data[i]);
        heapify(0, i, data);
    }
}

// A utility function to print vector of size n
void printArray(std::vector<int>& arr){
    for (int i = 0; i < arr.size(); ++i)
        cout << arr[i] << " ";
    cout << "\n";
}

// Driver's code
int main(){
    vector<int> arr = { 9, 4, 3, 8, 10, 2, 5 };

    // Function call
    heapSort(arr);

    cout << "Sorted array is \n";
    printArray(arr);
}