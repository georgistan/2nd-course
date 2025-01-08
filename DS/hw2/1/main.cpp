#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <stack>
#include <algorithm>
#include <iterator>
#include <functional>
#include <stdexcept>
#include "Edge.cpp"
#include <unordered_map>
#include <unordered_set>

class DfsTraversalsCollection {
public:
    DfsTraversalsCollection(const std::vector<std::vector<int>>& graph, int startVertex)
        : graph(graph), startVertex(startVertex), n(graph.size()) {
        if (startVertex < 0 || startVertex >= n) {
            throw std::invalid_argument("Invalid start vertex index.");
        }
        performAllTraversals();
    }

    const std::vector<Edge>& getTreeEdges() const { return treeEdges; }
    const std::vector<Edge>& getBackEdges() const { return backEdges; }
    const std::vector<Edge>& getForwardEdges() const { return forwardEdges; }
    const std::vector<Edge>& getCrossEdges() const { return crossEdges; }

    friend std::ostream& operator<<(std::ostream& os, const DfsTraversalsCollection& obj) {
        os << "DFS Order: ";
        for (int vertex : obj.dfsOrder) {
            os << vertex << " ";
        }

        os << "\nTree Edges: ";
        for (const auto& edge : obj.treeEdges) {
            os << "(" << edge.getEdge().first << "->" << edge.getEdge().second << ") ";
        }
        os << "\nBack Edges: ";
        for (const auto& edge : obj.backEdges) {
            os << "(" << edge.getEdge().first << "->" << edge.getEdge().second << ") ";
        }
        os << "\nForward Edges: ";
        for (const auto& edge : obj.forwardEdges) {
            os << "(" << edge.getEdge().first << "->" << edge.getEdge().second << ") ";
        }
        os << "\nCross Edges: ";
        for (const auto& edge : obj.crossEdges) {
            os << "(" << edge.getEdge().first << "->" << edge.getEdge().second << ") ";
        }
        os << "\n\n";

        return os;
    }

    // Iterator for traversals
    std::vector<int>::iterator begin() { return dfsOrder.begin(); }
    std::vector<int>::iterator end() { return dfsOrder.end(); }

    // Comparison operator
    bool operator<(const DfsTraversalsCollection& other) const {
        return std::lexicographical_compare(
            dfsOrder.begin(), dfsOrder.end(),
            other.dfsOrder.begin(), other.dfsOrder.end()
        );
    }

private:
    const std::vector<std::vector<int>>& graph;
    int startVertex;
    int n;

    std::vector<int> dfsOrder;
    std::vector<Edge> treeEdges;
    std::vector<Edge> backEdges;
    std::vector<Edge> forwardEdges;
    std::vector<Edge> crossEdges;

    void performAllTraversals() {
        std::vector<bool> visited(n, false);
        std::vector<int> discoveryTime(n, -1);
        std::vector<int> finishTime(n, -1);
        int time = 0;

        std::function<void(int)> dfs = [&](int v) {
            visited[v] = true;
            discoveryTime[v] = time++;
            dfsOrder.push_back(v);

            for (int neighbor : graph[v]) {
                if (!visited[neighbor]) {
                    treeEdges.emplace_back(v, neighbor);
                    dfs(neighbor);
                } else {
                    classifyEdge(v, neighbor, discoveryTime, finishTime);
                }
            }

            finishTime[v] = time++;
        };

        dfs(startVertex);
    }

    void classifyEdge(int u, int v,
                      const std::vector<int>& discoveryTime,
                      const std::vector<int>& finishTime) {
        if (discoveryTime[v] < discoveryTime[u] && finishTime[v] == -1) {
            backEdges.emplace_back(u, v);
        } else if (discoveryTime[u] < discoveryTime[v]) {
            forwardEdges.emplace_back(u, v);
        } else {
            crossEdges.emplace_back(u, v);
        }
    }
};

// int main() {
//     std::vector<std::vector<int>> graph = {
//         {1, 2},  // 0 -> 1, 0 -> 2
//         {2, 3},  // 1 -> 2, 1 -> 3
//         {1},      // 2 -> (none)
//         {0}      // 3 -> 0
//     };

//     try {
//         DfsTraversalsCollection dfsCollection(graph, 0);

//         // std::cout << "DFS Order: " << dfsCollection << std::endl;
        
//         // std::cout << "Tree Edges: ";
//         // for (const auto& edge : dfsCollection.getTreeEdges()) {
//         //     std::cout << "(" << edge.first << "->" << edge.second << ") ";
//         // }
//         // std::cout << std::endl;

//         // std::cout << "Back Edges: ";
//         // for (const auto& edge : dfsCollection.getBackEdges()) {
//         //     std::cout << "(" << edge.first << "->" << edge.second << ") ";
//         // }
//         // std::cout << std::endl;

//         // std::cout << "Forward Edges: ";
//         // for (const auto& edge : dfsCollection.getForwardEdges()) {
//         //     std::cout << "(" << edge.first << "->" << edge.second << ") ";
//         // }
//         // std::cout << std::endl;

//         // std::cout << "Cross Edges: ";
//         // for (const auto& edge : dfsCollection.getCrossEdges()) {
//         //     std::cout << "(" << edge.first << "->" << edge.second << ") ";
//         // }
//         std::cout << dfsCollection;
//         std::cout << std::endl;

//     } catch (const std::exception& ex) {
//         std::cerr << "Error: " << ex.what() << std::endl;
//     }

//     return 0;
// }

int main() {
    // Using unordered_map and unordered_set for the graph representation
    std::unordered_map<int, std::unordered_set<int>> graph = {
        {0, {1, 2}},  // 0 -> 1, 0 -> 2
        {1, {2, 3}},  // 1 -> 2, 1 -> 3
        {2, {1}},     // 2 -> 1
        {3, {0}}      // 3 -> 0
    };

    try {
        DfsTraversalsCollection dfsCollection(graph, 0);
        std::cout << dfsCollection;
        std::cout << std::endl;
    } catch (const std::exception& ex) {
        std::cerr << "Error: " << ex.what() << std::endl;
    }

    return 0;
}