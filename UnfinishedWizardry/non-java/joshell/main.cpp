#include <cstdlib>
#include <iostream>
#include <string>
#include <conio.h>
#include <vector>

#define DEBUG_ENABLED 0

#define debug if(DEBUG_ENABLED) cout

#define false   0
#define true    1

using namespace std;

enum Direction { UP, DOWN, LEFT, RIGHT };

const unsigned int MAXINPUT = 4096;
const unsigned char DIRECTION_SYM = 37;

vector<char*> history = vector<char*>();


void assertNotNull(const void* ptr, int line) {
    if(ptr == NULL) {
        char lineStr[5];
        perror(itoa(line, lineStr, 10));
        exit(1);
    }
}

Direction processDirection() {
    Direction dir;

    char ch = getch();
    switch(ch) {
        case 'H':
            dir = UP;
            break;
        case 'P':
            dir = DOWN;
            break;
        case 'K':
            dir = LEFT;
            break;
        case 'M':
            dir = RIGHT;
            break;
        default:
            debug << "Unknown input character!\n";
            break;
    }
    
    debug << "The direction taken was " << dir << endl;
    return dir;
}

char* getInput() {
    char* input = (char*)malloc(sizeof(char) * MAXINPUT+1);
    assertNotNull(input, __LINE__);

    int historyIndex = history.size() - 1;

    const char prompt[] = "joshell> ";

    cout << prompt;
    cout.flush();

    for(int i = 0; i < MAXINPUT; i++) {
        unsigned char ch;
        Direction direction;

        doneCharacter:
        ch = getch();

        switch(ch) {
            case '\r':
                if(i == 0) {
                    cout << "\n" << prompt;
                    cout.flush();
                    goto doneCharacter;
                }
                input[i] = '\0';
                cout << endl;
                goto doneInput;
            case '\b':
                i-=2;
                if(i == -2) {
                    i = -1;
                } else {
                    cout << ch;
                    cout << ' ';
                    cout << ch;
                    cout.flush();
                }
                break;
            default:
                switch(ch) {
                    case 224:
                        direction = processDirection();
                        switch(direction) {
                            case UP:
                                if(history.size() == 0) {
                                    goto doneCharacter;
                                }

                                strcpy(input, history[historyIndex]);

                                if(historyIndex > 0) {
                                    historyIndex--;   
                                } else {
                                    historyIndex = history.size() - 1;
                                }
                                
                                for(int k = 0; k < i; k++) {
                                    cout << " \b\b";
                                    cout.flush();
                                }

                                cout << input;

                                i = strlen(input);
                                goto doneCharacter;
                            case DOWN:
                                if(history.size() == 0) {
                                    goto doneCharacter;
                                }

                                strcpy(input, history[historyIndex]);

                                if(historyIndex < history.size()-1) {
                                    historyIndex++;
                                } else {
                                    historyIndex = 0;
                                }
                                
                                for(int k = 0; k < i; k++) {
                                    cout << " \b\b";
                                    cout.flush();
                                }

                                cout << input;

                                i = strlen(input);
                                goto doneCharacter;
                            case RIGHT:
                                // TODO
                            case LEFT:
                                // TODO
                                goto doneCharacter;
                        }
                        goto doneInput;
                    default:
                        input[i] = ch;
                        cout << ch;
                        cout.flush();
                        break;
                }
        }
    }
    
    doneInput:
    return input;
}

void freeHistory() {
    debug << "History Size: " << history.size() << endl;
    for(int i = 0; i < history.size(); i++) {
        debug << "History[" << i << "]: " << history[i] << endl;
        free(history[i]);
    }
}

int main(int argc, char *argv[])
{
    const char prompt[] = "joshell> ";

    while(true) {
        char *input = getInput();
        history.push_back(input);
        debug << "Entered [" << input << "] into history... History Size = " << history.size() << endl;
        if(strcmp("exit", input) == 0) {
            debug << "Terminating..." << endl;
            freeHistory();
            if(DEBUG_ENABLED) {
                getch();
            }
            break;
        }
    }
    
    return EXIT_SUCCESS;
}
