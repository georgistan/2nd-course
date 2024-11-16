#include <iostream>
#include <string>
#include <chrono>
#include <ctime>

class Browser {
    private:
        struct Tab {
            Tab() : URL("about:blank"), timestamp(time(0)) {}
            Tab(const std::string& URL) : URL(URL), timestamp(time(0)) {}

            std::string URL;
            time_t timestamp;
            Tab* next = nullptr;
            Tab* prev = nullptr;
        };

        Tab* firstTab = nullptr;
        Tab* currentTab = nullptr;

        Tab* split(Tab* head);
        Tab* merge(Tab* right, Tab* left, const std::string& sortBy);
        Tab* mergeSort(Tab* head, const std::string& sortBy);

    public:
        Browser();
        ~Browser();

        void go(const std::string& goTo);
        void insert(const std::string& newTabURL);
        void back();
        void forward();
        void remove();
        void print() const;
        void sort(const std::string& sortBy);
};