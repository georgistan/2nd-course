#include "DfsTraversalsCollection.hpp"

DfsTraversalsCollection::DfsTraversalsCollection(const std::vector<std::vector<int>>& graph, int startingVertex) : graph(graph), startingVertex(startingVertex) {
    generateTraversals();
}

void DfsTraversalsCollection::DFS(Traversal& traversal, int current, std::vector<int>& discoveryTime, std::vector<int>& finishTime, std::vector<bool>& visited, int& time) {
    visited[current] = true;
    discoveryTime[current] = ++time;

    for (int next : graph[current]) {
        if (!visited[next]) {

            traversal.addTreeEdge(current, next);
            DFS(traversal, next, discoveryTime, finishTime, visited, time);

        } else if (discoveryTime[next] != -1 && finishTime[next] == -1) {

            traversal.addBackEdge(current, next);

        } else if (finishTime[next] != -1) {

            if (discoveryTime[current] < discoveryTime[next]) {
                traversal.addForwardEdge(current, next);
            } else if (discoveryTime[next] < discoveryTime[current]) {
                traversal.addCrossEdge(current, next);
            }
            
        }
    }

    finishTime[current] = ++time;
}

void DfsTraversalsCollection::generateEdges(const std::vector<int>& path, Traversal& traversal) {
    const int N = graph.size();
    std::vector<int> discoveryTime(N, -1);
    std::vector<int> finishTime(N, -1);
    std::vector<bool> visited(N, false);
    int time = 0;

    for (int current : path) {
        if (!visited[current]) {
            DFS(traversal, current, discoveryTime, finishTime, visited, time);
        }
    }
}

void DfsTraversalsCollection::generateTraversalsHelper(std::vector<int>& path, std::vector<bool>& visited, int current) {
    visited[current] = true;
    path.push_back(current);

    if (path.size() == graph.size()) {
        Traversal traversal(path);

        generateEdges(path, traversal);

        traversals.insert(traversal);
    } else {
        std::vector<int> candidates;

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                candidates.push_back(i);
            }
        }

        for (int next : candidates) {
            generateTraversalsHelper(path, visited, next);
        }
    }

    visited[current] = false;
    path.pop_back();
}

void DfsTraversalsCollection::generateTraversals() {
    std::vector<int> path;
    std::vector<bool> visited(graph.size(), false);

    generateTraversalsHelper(path, visited, startingVertex);
}