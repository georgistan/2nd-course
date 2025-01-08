#include <iostream>

class Edge {
    private:
        std::pair<int, int> adj;

    public:
        Edge(int from, int to);

        bool operator<(const Edge& other) const;
};