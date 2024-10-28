#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<pair<int, int>> mergeIntervals(vector<pair<int, int>>& intervals) {
    if (intervals.empty()) return {};

    sort(intervals.begin(), intervals.end());

    vector<pair<int, int>> merged;
    merged.push_back(intervals[0]);

    for (int i = 1; i < intervals.size(); ++i) {
        auto& last = merged.back();

        if (intervals[i].first <= last.second) {
            last.second = max(last.second, intervals[i].second);
        } else {
            merged.push_back(intervals[i]);
        }
    }

    return merged;
}

int main() {
    vector<pair<int, int>> intervals = {{1, 3}, {2, 4}, {5, 7}, {6, 8}};

    vector<pair<int, int>> result = mergeIntervals(intervals);

    cout << "Joined intervals: ";
    for (const auto& interval : result) {
        cout << "[" << interval.first << ", " << interval.second << "] ";
    }
    cout << endl;

    return 0;
}
