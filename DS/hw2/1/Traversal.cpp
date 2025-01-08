#include "Traversal.hpp"

Traversal::Traversal(const std::vector<int>& path) : dfsOrder(path) {}

void Traversal::addTreeEdge(int from, int to) {
    treeEdges.push_back(Edge(from, to));
}

void Traversal::addBackEdge(int from, int to) {
    backEdges.insert(Edge(from, to));
}

void Traversal::addForwardEdge(int from, int to) {
    forwardEdges.insert(Edge(from, to));
}

void Traversal::addCrossEdge(int from, int to) {
    crossEdges.insert(Edge(from, to));
}

const std::vector<int>& Traversal::getDfsOrder() const {
    return dfsOrder;
}

const std::vector<Edge>& Traversal::getTreeEdges() const {
    return treeEdges;
}

const std::set<Edge>& Traversal::getBackEdges() const {
    return backEdges;
}

const std::set<Edge>& Traversal::getForwardEdges() const {
    return forwardEdges;
}

const std::set<Edge>& Traversal::getCrossEdges() const {
    return crossEdges;
}

bool Traversal::operator<(const Traversal& other) const {
    return dfsOrder < other.getDfsOrder();
}