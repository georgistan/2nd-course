#include <string>
#include <chrono>
#include <ctime>

class Browser {
    private:
        struct Tab {
            Tab(const std::string& URL) : URL(URL) {
                timestamp = std::chrono::system_clock::now();
            }
            std::string URL;
            std::chrono::system_clock timestamp;
            Tab* next = nullptr;
            Tab* prev = nullptr;
        };

        Tab* currentTab = nullptr;

    public:
        Browser(std::string URL, std::chrono::system_clock timestamp);
        void go(const std::string& goTo);
        void insert(const std::string& URL);
        void back();
        void remove();
        void forward();
        void print() const;
};