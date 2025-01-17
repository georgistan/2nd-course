#include "Edge.cpp"
#include <vector>
#include <unordered_map>

class Traversal {
    private:
        std::vector<int> dfsOrder;
        std::vector<Edge> treeEdges;
        std::set<Edge> backEdges;
        std::set<Edge> forwardEdges;
        std::set<Edge> crossEdges;
    public:
        Traversal(const std::vector<int>& path);

        void addTreeEdge(int from, int to);
        void addBackEdge(int from, int to);
        void addForwardEdge(int from, int to);
        void addCrossEdge(int from, int to);

        const std::vector<int>& getDfsOrder() const;
        const std::vector<Edge>& getTreeEdges() const;
        const std::set<Edge>& getBackEdges() const;
        const std::set<Edge>& getForwardEdges() const;
        const std::set<Edge>& getCrossEdges() const;

        bool operator<(const Traversal& other) const;
};