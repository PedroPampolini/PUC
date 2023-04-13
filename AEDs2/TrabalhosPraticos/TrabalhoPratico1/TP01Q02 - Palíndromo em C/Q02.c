#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool IsPalindrome(char* text) {
    bool flag = true;
    for (int i = 0; i < strlen(text); i++) {
        if (text[i] != text[strlen(text) - 1 - i]) {
                flag = false;
            }
    }
    return flag;
}

bool StringIsEqual(char* text1, char* text2) {
        bool flag = true;
        if (strlen(text1) != strlen(text2)) {
            return false;
        }
        for (int i = 0; i < strlen(text1); i++) {
            if (text1[i] != text2[i]) {
                flag = false;
            }
        }
        return flag;
    }



int main() {
    char text[50];
    scanf("%[^\n]s", text);
    fflush(stdin);

    while (!StringIsEqual(text, "FIM")) {
            if (IsPalindrome(text)) {
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }
            scanf("%[^\n]s", text);
            fflush(stdin);
        }

    return 0;
}
