// #include <iostream>
// #include <vector>
// #include <sstream>
// #include <iterator>
// #include <set>
// #include <unordered_map>
// #include "Traversal.cpp"
// #include <functional>
#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
#include <unordered_set>
#include <functional>
#include <stdexcept>
#include "Traversal.cpp"

class DfsTraversalsCollection {
    private:
        std::vector<std::vector<int>> graph;
        int startingVertex;

        std::set<Traversal> traversals;

        void generateTraversals();
        void generateTraversalsHelper(std::vector<int>& path, std::vector<bool>& visited, int current);        
        void generateEdges(const std::vector<int>& path, Traversal& traversal);
        void DFS(Traversal& traversal, int current, std::vector<int>& discoveryTime, std::vector<int>& finishTime, std::vector<bool>& visited, int& time);

    public:
        DfsTraversalsCollection(const std::vector<std::vector<int>>& graph, int startingVertex);

        std::vector<int> getTreeEdges() const;
        std::vector<int> getCrossEdges() const;
        std::vector<int> getBackEdges() const;
        std::vector<int> getForwardEdges() const;

        std::set<Traversal>::const_iterator begin() const;
        std::set<Traversal>::const_iterator end() const;
        
        bool compareTraversals(const DfsTraversalsCollection& first, const DfsTraversalsCollection& second) const;

        friend std::ostream& operator<<(std::ostream& os, const DfsTraversalsCollection& collection) {  
            for (const auto& traversal : collection.traversals) {
                for (int vertex : traversal.getDfsOrder())
                    os << vertex << " ";
                os << "\n";
            }
            return os;
        }
};