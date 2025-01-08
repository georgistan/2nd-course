#include <iostream>
#include <vector>
#include <set>
#include <unordered_map>
#include <unordered_set>
#include <functional>
// #include <stdexcept>
 
using namespace std;
 
struct Edge {
    int from; 
    int to;
 
    Edge(const int f, const int t) : from(f), to(t) {}
 
    bool operator<(const Edge& other) const {
        if (from != other.from)
            return from < other.from;
        return to < other.to;
    }
};
 
class Traversal {
private:
    vector<int> dfsOrder;
    vector<Edge> treeEdges;
    set<Edge> backEdges;
    set<Edge> forwardEdges;
    set<Edge> crossEdges;
 
public:
    Traversal(const vector<int>& p) : dfsOrder(p) {}
 
    void addTreeEdge(int from, int to) {
        treeEdges.push_back(Edge(from, to));
    }
    void addBackEdge(int from, int to) {
        backEdges.insert(Edge(from, to));
    }
    void addForwardEdge(int from, int to) {
        forwardEdges.insert(Edge(from, to));
    }
    void addCrossEdge(int from, int to) {
        crossEdges.insert(Edge(from, to));
    }
 
    const vector<int>& getDfsOrder() const {
        return dfsOrder;
    }
    const vector<Edge>& getTreeEdges() const {
        return treeEdges;
    }
    const set<Edge>& getBackEdges() const {
        return backEdges;
    }
    const set<Edge>& getForwardEdges() const {
        return forwardEdges;
    }
    const set<Edge>& getCrossEdges() const {
        return crossEdges;
    }
 
    bool operator<(const Traversal& other) const {
        return dfsOrder < other.dfsOrder;
    }
};
 
class DfsTraversalsCollection {
    vector<vector<int>> graph;
    int startNode;
    set<Traversal> traversals;
 
    void classifyEdges(const vector<int>& path, Traversal& traversal) {
        const int N = graph.size();
        vector<int> discoveryTime(N, -1);
        vector<int> finishTime(N, -1);
        vector<bool> visited(N, false);
        int time = 0;
 
        function<void(int)> dfs = [&](int current) { // to avoid passing all the params
            visited[current] = true;
            discoveryTime[current] = ++time;
 
            for (int next : graph[current]) {
                if (!visited[next]) {
                    traversal.addTreeEdge(current, next);
                    dfs(next);
                } else if (discoveryTime[next] != -1 && finishTime[next] == -1) {
                    traversal.addBackEdge(current, next);
                } else if (finishTime[next] != -1) {
                    if (discoveryTime[current] < discoveryTime[next])
                        traversal.addForwardEdge(current, next);
                    else if (discoveryTime[next] < discoveryTime[current]) {
                        traversal.addCrossEdge(current, next);
                    }
                }
            }
            finishTime[current] = ++time;
        };
 
        for (int current : path) {
            if (!visited[current]) {
                dfs(current);
            }
        }
    }
 
    bool isValidNextNode(const vector<bool>& visited, int current, int next) {
        for (size_t i = 0; i < graph.size(); i++) {
            if (visited[i]) {
                if (find(graph[i].begin(), graph[i].end(), next) != graph[i].end()) {
                    return true;
                }
            }
        }
        return find(graph[current].begin(), graph[current].end(), next) != graph[current].end();
    }
 
    void generateTraversalsHelper(vector<int>& path, vector<bool>& visited, int current) {
        visited[current] = true;
        path.push_back(current);
 
        if (path.size() == graph.size()) {
            Traversal traversal(path);
            classifyEdges(path, traversal);
            traversals.insert(traversal);
        } else {
            vector<int> candidates;
            for (int i = 0; i < graph.size(); i++) {
                if (!visited[i] && isValidNextNode(visited, current, i))
                    candidates.push_back(i);
            }
 
            for (int next : candidates)
                generateTraversalsHelper(path, visited, next);
        }
 
        visited[current] = false;
        path.pop_back();
    }
 
    void generateAllTraversals() {
        vector<int> path;
        vector<bool> visited(graph.size(), false);
        generateTraversalsHelper(path, visited, startNode);
    }
 
public:
    DfsTraversalsCollection(const vector<vector<int>>& g, int start)
        : graph(g), startNode(start) {
        generateAllTraversals();
    }
 
    using Iterator = set<Traversal>::const_iterator;
 
    Iterator begin() const {
        return traversals.begin();
    }
    Iterator end() const {
        return traversals.end();
    }
 
    set<Edge> getAllTreeEdges() const {
        set<Edge> result;
        for (const auto& traversal : traversals) {
            for (const auto& edge : traversal.getTreeEdges()) {
                result.insert(edge);
            }
        }
        return result;
    }
 
    set<Edge> getAllBackEdges() const {
        set<Edge> result;
        for (const auto& traversal : traversals) {
            for (const auto& edge : traversal.getBackEdges()) {
                result.insert(edge);
            }
        }
        return result;
    }
 
    set<Edge> getAllForwardEdges() const {
        set<Edge> result;
        for (const auto& traversal : traversals) {
            for (const auto& edge : traversal.getForwardEdges()) {
                result.insert(edge);
            }
        }
        return result;
    }
 
    set<Edge> getAllCrossEdges() const {
        set<Edge> result;
        for (const auto& traversal : traversals) {
            for (const auto& edge : traversal.getCrossEdges()) {
                result.insert(edge);
            }
        }
        return result;
    }
 
    friend ostream& operator<<(ostream& os, const DfsTraversalsCollection& collection) {
        for (const auto& traversal : collection.traversals) {
            for (int vertex : traversal.getPath())
                os << vertex << " ";
            os << "\n";
        }
        return os;
    }
};
 
using namespace std;
 
int main() {
    vector<vector<int>> graph = {
        {1, 2},
        {2,3},
        {1},
        {0}
    };
 
    DfsTraversalsCollection collection(graph, 0);
 
    for (auto it = collection.begin(); it != collection.end(); ++it) {
        const Traversal& traversal = *it;
        for (int vertex : traversal.getPath()) {
            cout << vertex << " ";
        }
        cout << endl;
    }
 
    auto it1 = collection.begin();
    auto it2 = ++collection.begin();
 
    cout << (*it1 < *it2) << endl;
 
    set<Edge> allTreeEdges = collection.getAllTreeEdges();
    cout << "\ntree edges: ";
    for (const Edge& edge : allTreeEdges)
        cout << "(" << edge.from << " -> " << edge.to << ")";
 
    set<Edge> allBackEdges = collection.getAllBackEdges();
    cout << "\nback edges: ";
    for (const Edge& edge : allBackEdges)
        cout << "(" << edge.from << " -> " << edge.to << ")";
 
    set<Edge> allForwardEdges = collection.getAllForwardEdges();
    cout << "\nforward eges: ";
    for (const Edge& edge : allForwardEdges) {
        cout << "(" << edge.from << " -> " << edge.to << ")";
    }
 
    set<Edge> allCrossEdges = collection.getAllCrossEdges();
    cout << "\ncross eges: ";
    for (const Edge& edge : allCrossEdges) {
        cout << "(" << edge.from << " -> " << edge.to << ")";
    }
}