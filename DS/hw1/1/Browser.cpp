#include "Browser.h"

Browser::Browser() {
    firstTab = currentTab = new Tab();
}

Browser::~Browser() {
    while(firstTab->next) {
        firstTab = firstTab->next;

        delete firstTab->prev;
    }

    delete firstTab;
}

void Browser::go(const std::string& goTo) {
    currentTab->URL = goTo;
    currentTab->timestamp = time(0);
}

void Browser::insert(const std::string& newTabURL) {
    Tab* newTab = new Tab(newTabURL);

    newTab->next = currentTab->next;
    currentTab->next = newTab;
    
    currentTab->next->prev = newTab;
    newTab->prev = currentTab;

    currentTab = newTab;
}

void Browser::back() {
    if (currentTab == firstTab) {
        return;
    }

    currentTab = currentTab->prev;
}

void Browser::forward() {
    if (currentTab->next == nullptr) {
        return;
    }

    currentTab = currentTab->next;
}

void Browser::remove() {
    Tab* temp = currentTab;

    if (currentTab->next == nullptr) {
        if (currentTab->prev == nullptr) {
            currentTab->URL = "about:blank";
            currentTab->timestamp = time(0);
        }

        currentTab = currentTab->prev;
        currentTab->next = nullptr;

        delete temp;

        return;
    }

    currentTab->prev->next = currentTab->next;
    currentTab->next->prev = currentTab->prev;

    currentTab = currentTab->next;

    delete temp;
}

void Browser::print() const {
    Tab* iterator = firstTab;

    while (iterator) {
        if (iterator == currentTab) {
            std::cout << ">" << iterator->URL << " " << iterator->timestamp << std::endl;
        } else {
            std::cout << iterator->URL << " " << iterator->timestamp << std::endl;
        }

        iterator = iterator->next;
    }

    std::cout << std::endl;

    delete iterator;
}

Browser::Tab* Browser::merge(Tab* right, Tab* left, const std::string& sortBy) {
    Tab temp = Tab();
    Tab* currtail = &temp;

    bool compareFlag = false;

    while (right && left) {
        if (sortBy == "URL") {

            if (left->URL == right->URL) {
                compareFlag = left->timestamp < right->timestamp;
            } else {
                compareFlag = left->URL < right->URL;
            }

        } else {

            if (left->timestamp == right->timestamp) {
                compareFlag = left->URL < right->URL;
            } else {
                compareFlag = left->timestamp < right->timestamp;
            }

        }

        if (compareFlag) {
            currtail->next = left;
            left->prev = currtail;
            left = left->next;
        } else {
            currtail->next = right;
            right->prev = currtail;
            right = right->next;
        }

        currtail = currtail->next;
    }

    if (left) {
        currtail->next = left;
    } else {
        currtail->next = right;
    }

    if (currtail->next) {
        currtail->next->prev = currtail;
    }

    currentTab = temp.next;

    return temp.next;
}

Browser::Tab* Browser::split(Tab* head) {
    Tab* fast = head;
    Tab* slow = head;

    while (fast->next && fast->next->next) {
        fast = fast->next->next;
        slow = slow->next;
    } 

    return slow;
}

Browser::Tab* Browser::mergeSort(Tab* head, const std::string& sortBy) {
    if (!head || !head->next) {
        return head;
    }

    Tab* right = split(head);
    right = right->next;

    right->prev->next = nullptr;
    right->prev = nullptr;

    Tab* leftSorted = mergeSort(head, sortBy);
    Tab* rightSorted = mergeSort(right, sortBy);

    return merge(leftSorted, rightSorted, sortBy);
}

void Browser::sort(const std::string& sortBy) {
    firstTab = mergeSort(firstTab, sortBy);
}