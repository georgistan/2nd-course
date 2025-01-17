#include <iostream>
#include <vector>

template <class T>
struct Node {
    public:
        T data;
        std::vector<Node<T>*> children;

        Node(T data) : data(data) {}
};

template <class T>
void print(Node<T>* root) {
    std::cout << root->data << " ";

    for (int i = 0; i < root->children.size(); i++) {
        print(root->children[i]);
    }
}

template <class T>
void free(Node<T>* root)
{
	for (int i = 0; i < root->children.size(); i++) {
		free(root->children[i]);
    }

	delete root;
}

template <class T>
bool contains(Node<T>* root, T element)
{
	int size = root->children.size();

	if (root->data == element) {
		return true;
    }

	for (int i = 0; i < size; i++) {
		if (contains(root->children[i], element)) {
            return true;
        }
	}

	return false;
}

template <class T>
int sum(Node<T>* root) {
    int summ = root->data;

    for (int i = 0; i < root->children.size(); i++) {
        summ += sum(root->children[i]);
    }

    return summ;
}

template <class T>
T maxT(Node<T>* root) {
    T max = root->data;

    for (int i = 0; i < root->children.size(); i++) {
        max = std::max(max, maxT(root->children[i]));
    }

    return max;
}

template <class T>
int height(Node<T>* root) {
    int maxHeight = -1;

    for (int i = 0; i < root->children.size(); i++) {
        maxHeight = std::max(maxHeight, height(root->children[i]));
    }

    return maxHeight + 1;
}

template <class T>
int getNumOfElements(Node<T>* root) {
    int num = 0;

    for (int i = 0; i < root->children.size(); i++) {
        num += getNumOfElements(root->children[i]);
    }

    return num + 1;
}

bool markOccurances(Node<int>* root, std::vector<bool>& nums, int n) {
	if (root->data < 1 || root->data > n) {
		return false;
    }

	if (nums[root->data - 1]) {
		return false;
    }

	nums[root->data - 1] = true;

	for (int i = 0; i < root->children.size(); i++) {
		if (!markOccurances(root->children[i], nums, n)) {
            return false;
        }
	}

	return true;
}

bool isPermutation(Node<int>* root)
{
	int size = getNumOfElements(root);
	std::vector<bool> nums(size, false);

	return markOccurances(root, nums, size);
}

template<class T>
T sumOfLeaves(Node<T>* root) {
	int sum = 0;

	if (root->children.size() == 0) {
		return root->data;
    }

	for (int i = 0; i < root->children.size(); i++) {
		sum += sumOfLeaves(root->children[i]);
    }

	return sum;
}

template <class T>
int getNumOfLeaves(Node<T>* root) {
    int num = 0;

    if (root->children.size() == 0) {
        num++;
    }

    for (int i = 0; i < root->children.size(); i++) {
        num += getNumOfLeaves(root->children[i]);
    }

    return num;
}

int main()
{
    Node<int>* root = new Node<int>(4);

    // Create child nodes
    Node<int>* child1 = new Node<int>(7);
    Node<int>* child2 = new Node<int>(8);

    // Add children to root
    root->children.push_back(child1);
    root->children.push_back(child2);

    // Add children to one of the child nodes
    Node<int>* child3 = new Node<int>(5);
    Node<int>* child4 = new Node<int>(9);
    child1->children.push_back(child3);
    child1->children.push_back(child4);

    Node<int>* child5 = new Node<int>(1);
    Node<int>* child6 = new Node<int>(2);
    Node<int>* child7 = new Node<int>(3);
    child2->children.push_back(child5);
    child2->children.push_back(child6);
    child2->children.push_back(child7);

    // Print the tree
    std::cout << "Tree traversal: ";
    print(root);
    std::cout << std::endl;

    std::cout << "Contains 2: " << contains(root, 2) << std::endl;
    std::cout << "Sum: " << sum(root) << std::endl;
    std::cout << "Max: " << maxT(root) << std::endl;
    std::cout << "Height: " << height(root) << std::endl;
    std::cout << "Num of elements: " << getNumOfElements(root) << std::endl;

    std::vector<bool> nums(9, false);
    // Node<int>* child8 = new Node<int>(5);
    // child2->children.push_back(child8);      outputs false that way
    std::cout << "Valid tree: " << markOccurances(root, nums, 9) << std::endl;

    std::cout << "Is permutation: " << isPermutation(root) << std::endl;
    std::cout << "Num of leaves: " << getNumOfLeaves(root) << std::endl;

    return 0;
}