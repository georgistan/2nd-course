#include "Edge.hpp"

Edge::Edge(int from, int to) {
    adj.first = from;
    adj.second = to;
}

bool Edge::operator<(const Edge& other) const {
    if (from != other.from)
        return from < other.from;
    return to < other.to;
}