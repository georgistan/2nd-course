#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
#include <unordered_set>
#include <functional>
#include <stdexcept>
#include "Edge.cpp"

class DfsTraversalsCollection {
private:
    const std::unordered_map<int, std::unordered_set<int>>& graph;
    int startVertex;

    std::vector<int> dfsOrder;
    std::vector<Edge> treeEdges;
    std::vector<Edge> backEdges;
    std::vector<Edge> forwardEdges;
    std::vector<Edge> crossEdges;

    void performAllTraversals() {
        std::unordered_map<int, bool> visited;
        std::unordered_map<int, int> discoveryTime;
        std::unordered_map<int, int> finishTime;
        int time = 0;

        std::function<void(int)> dfs = [&](int v) {
            visited[v] = true;
            discoveryTime[v] = time++;
            dfsOrder.push_back(v);

            if (graph.find(v) != graph.end()) {
                for (int neighbor : graph.at(v)) {
                    if (!visited[neighbor]) {
                        treeEdges.emplace_back(v, neighbor);
                        dfs(neighbor);
                    } else {
                        classifyEdge(v, neighbor, discoveryTime, finishTime);
                    }
                }
            }

            finishTime[v] = time++;
        };

        dfs(startVertex);
    }

    void classifyEdge(int u, int v,
                      const std::unordered_map<int, int>& discoveryTime,
                      const std::unordered_map<int, int>& finishTime) {
        if (discoveryTime.at(v) < discoveryTime.at(u) && finishTime.find(v) == finishTime.end()) {
            backEdges.emplace_back(u, v);
        } else if (discoveryTime.at(u) < discoveryTime.at(v)) {
            forwardEdges.emplace_back(u, v);
        } else {
            crossEdges.emplace_back(u, v);
        }
    }

public:
    DfsTraversalsCollection(const std::unordered_map<int, std::unordered_set<int>>& graph, int startVertex)
        : graph(graph), startVertex(startVertex) {
        if (startVertex < 0 || graph.find(startVertex) == graph.end()) {
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

};

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
