#include "Browser.hpp"
void handleUserInput(Browser& browser, const std::string& command, const std::string& arg) {
    if (command == "GO") {
        browser.go(arg);
    } else if (command == "INSERT") {
        browser.insert(arg);
    } else if (command == "BACK") {
        browser.back();
    }  else if (command == "FORWARD") {
        browser.forward();
    }  else if (command == "REMOVE") {
        browser.remove();
    }  else if (command == "PRINT") {
        browser.print();
    }  else if (command == "SORT") {
        browser.sort(arg);
    }
}

int main() {
    Browser browser = Browser();
    std::string input;

    while (true) {
        std::cout << "$ ";
        std::getline(std::cin, input);

        int separatorInd = input.find(' ');
        
        std::string command = input.substr(0, separatorInd);
        std::string arg = (separatorInd != std::string::npos) ? input.substr(separatorInd + 1) : "";

        handleUserInput(browser, command, arg);
    }

    return 0;
}