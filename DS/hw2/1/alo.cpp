#include "DfsTraversalsCollection.cpp"

int main () {
    std::vector<std::vector<int>> graph = {
        {1, 2},  // 0 -> 1, 0 -> 2
        {2, 3},  // 1 -> 2, 1 -> 3
        {1},     // 2 -> 1
        {0}      // 3 -> 0
    };

    DfsTraversalsCollection collection = DfsTraversalsCollection(graph, 0);

    try {
        DfsTraversalsCollection dfsCollection(graph, 0);
        std::cout << dfsCollection << std::endl;
    } catch (const std::exception& ex) {
        std::cerr << "Error: " << ex.what() << std::endl;
    }

    return 0;
}